package com.codecorecix.ecommerce.security.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  private static final String CONTENT_TYPE = "application/json";

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException {

    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.setContentType(CONTENT_TYPE);

    Map<String, Object> body = new HashMap<>();
    body.put("message", "Access denied: You do not have the necessary permissions.");
    body.put("error", accessDeniedException.getMessage());
    body.put("path", request.getRequestURI());
    response.getWriter().write(new ObjectMapper().writeValueAsString(body));
  }
}
