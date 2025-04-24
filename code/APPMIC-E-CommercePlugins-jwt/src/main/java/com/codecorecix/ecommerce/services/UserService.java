package com.codecorecix.ecommerce.services;

import com.codecorecix.ecommerce.event.models.UserResponseDto;
import com.codecorecix.ecommerce.exceptions.JwtException;
import com.codecorecix.ecommerce.utils.GenericResponse;
import com.codecorecix.ecommerce.utils.JwtErrorMessage;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UserService {

  private final Logger log = LoggerFactory.getLogger(UserService.class);

  private final WebClient.Builder loadBalancedWebClientBuilder;

  private final WebClient simpleWebClient;

  private final Environment env;

  public UserService(final WebClient.Builder loadBalancedWebClientBuilder, final WebClient simpleWebClient, final Environment env) {
    this.loadBalancedWebClientBuilder = loadBalancedWebClientBuilder;
    this.simpleWebClient = simpleWebClient;
    this.env = env;
  }

  public UserResponseDto validateUser(final String username) {
    try {
      final WebClient client = getWebClient();
      final String uri = env.getProperty("MS_MAINTENANCE_NAME", "http://127.0.0.1:9090/api/users/login");
      final GenericResponse<UserResponseDto> userResponseDto = client.get()
          .uri(uri, uriBuilder -> uriBuilder.queryParam("username", username).build())
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(new ParameterizedTypeReference<GenericResponse<UserResponseDto>>() {
          })
          .block();
      if (ObjectUtils.isEmpty(userResponseDto) || ObjectUtils.isEmpty(userResponseDto.getBody())) {
        throw new JwtException(JwtErrorMessage.USERNAME_NOT_FOUND);
      }
      return userResponseDto.getBody();
    } catch (final RuntimeException ex) {
      throw new JwtException(JwtErrorMessage.SERVICE_UNAVAILABLE);
    }
  }

  private WebClient getWebClient() {
    if (StringUtils.isBlank(env.getProperty("MS_MAINTENANCE_NAME"))) {
      log.info("Using simpleWebClient as MS_MAINTENANCE_NAME is not set.");
      return simpleWebClient;
    } else {
      log.info("Using loadBalancedWebClient.");
      return loadBalancedWebClientBuilder.build();
    }
  }
}
