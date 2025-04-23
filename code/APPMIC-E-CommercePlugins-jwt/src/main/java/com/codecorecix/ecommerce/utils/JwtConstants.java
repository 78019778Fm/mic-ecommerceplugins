package com.codecorecix.ecommerce.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtConstants {

  public static final String INVALID_USER = "Invalid or inactive user";

  public static final String GENERATED_TOKEN = "Token Generated Correctly";

  public static final String VALID_TOKEN = "The token is valid";

  public static final String INVALID_TOKEN = "The token is invalid or has expired.";
}
