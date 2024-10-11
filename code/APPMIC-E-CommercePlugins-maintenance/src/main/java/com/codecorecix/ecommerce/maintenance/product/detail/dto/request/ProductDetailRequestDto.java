package com.codecorecix.ecommerce.maintenance.product.detail.dto.request;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailRequestDto implements Serializable {

  private Integer id;

  @NotNull(message = "The field name is null, please fill.")
  @NotEmpty(message = "The field name is empty, please fill.")
  private String name;

  @NotNull(message = "The field description is null, please fill.")
  @NotEmpty(message = "The field description is empty, please fill.")
  private String description;

  @NotNull(message = "The field productId is null, please fill.")
  private Integer productId;
}
