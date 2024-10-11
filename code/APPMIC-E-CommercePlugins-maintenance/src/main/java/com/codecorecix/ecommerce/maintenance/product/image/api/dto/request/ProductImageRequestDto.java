package com.codecorecix.ecommerce.maintenance.product.image.api.dto.request;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageRequestDto implements Serializable {

  private Integer id;

  @NotNull(message = "The field imageUrl is null, please fill.")
  @NotEmpty(message = "The field imageUrl is empty, please fill.")
  private String imageUrl;

  @NotNull(message = "The field productId is null, please fill.")
  private Integer productId;
}
