package com.codecorecix.ecommerce.maintenance.user.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

  private Integer id;

  private String username;

  private String password;

  private Boolean isActive;
}
