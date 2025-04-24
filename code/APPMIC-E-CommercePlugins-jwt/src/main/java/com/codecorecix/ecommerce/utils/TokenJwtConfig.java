package com.codecorecix.ecommerce.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenJwtConfig {

  public static final String PREFIX_TOKEN = "Bearer ";

  public static final String HEADER_AUTHORIZATION = "Authorization";

  public static final String CONTENT_TYPE = "application/json";

}
