package com.codecorecix.ecommerce.maintenance.product.info.controller;

import java.util.List;
import java.util.Objects;

import com.codecorecix.ecommerce.event.models.ProductInfo;
import com.codecorecix.ecommerce.exception.GenericUnprocessableEntityException;
import com.codecorecix.ecommerce.maintenance.product.detail.service.ProductDetailService;
import com.codecorecix.ecommerce.maintenance.product.image.service.ProductImageService;
import com.codecorecix.ecommerce.maintenance.product.info.api.dto.request.ProductRequestDto;
import com.codecorecix.ecommerce.maintenance.product.info.api.dto.response.ProductResponseDto;
import com.codecorecix.ecommerce.maintenance.product.info.service.ProductService;
import com.codecorecix.ecommerce.utils.GenericResponse;
import com.codecorecix.ecommerce.utils.GenericResponseConstants;
import com.codecorecix.ecommerce.utils.MaintenanceUtils;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/product")
public class ProductController {

  private final ProductService service;

  private final ProductImageService productImageService;

  private final ProductDetailService productDetailService;

  @GetMapping("/listProduct")
  public ResponseEntity<GenericResponse<List<ProductResponseDto>>> listProduct() {
    return ResponseEntity.status(HttpStatus.OK).body(this.service.listProduct());
  }

  @GetMapping("/listActiveProduct")
  public ResponseEntity<GenericResponse<List<ProductResponseDto>>> listActiveProduct() {
    return ResponseEntity.status(HttpStatus.OK).body(this.service.listActiveProducts());
  }

  @GetMapping("/getById/{id}")
  public ResponseEntity<GenericResponse<ProductResponseDto>> getProductById(@PathVariable(value = "id") final Integer id) {
    final GenericResponse<ProductResponseDto> response = this.service.findById(id);
    if (Objects.nonNull(response.getBody())) {
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @GetMapping("/checkProducts")
  public ResponseEntity<GenericResponse<List<ProductInfo>>> checkProducts(@RequestParam final List<Integer> ids) {
    final GenericResponse<List<ProductInfo>> response = this.service.findByIds(ids);
    if (Objects.nonNull(response.getBody())) {
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @PostMapping("/saveProduct")
  public ResponseEntity<GenericResponse<ProductResponseDto>> saveProduct(@RequestBody final ProductRequestDto productRequestDto) {
    MaintenanceUtils.validRequestDto(productRequestDto);
    if (ObjectUtils.isNotEmpty(productRequestDto.getId())) {
      throw new GenericUnprocessableEntityException(GenericResponseConstants.UNPROCESSABLE_ENTITY_EXCEPTION);
    } else {
      return ResponseEntity.status(HttpStatus.CREATED).body(this.service.saveProduct(productRequestDto));
    }
  }

  @PutMapping("/updateProduct/{id}")
  public ResponseEntity<GenericResponse<ProductResponseDto>> updateProduct(@PathVariable(value = "id") final Integer id,
      @RequestBody final ProductRequestDto productRequestDto) {
    final GenericResponse<ProductResponseDto> response = this.service.findById(id);
    if (ObjectUtils.isNotEmpty(response.getBody())) {
      productRequestDto.setId(response.getBody().getId());
      MaintenanceUtils.validRequestDto(productRequestDto);
      return ResponseEntity.status(HttpStatus.OK).body(this.service.saveProduct(productRequestDto));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @GetMapping("/disabledOrEnabledProduct/{id}/{isActive}")
  public ResponseEntity<GenericResponse<ProductResponseDto>> disabledOrEnabledProduct(@PathVariable(value = "id") final Integer id,
      @PathVariable(value = "isActive") final Boolean isActive) {
    final GenericResponse<ProductResponseDto> response = this.service.findById(id);
    if (ObjectUtils.isNotEmpty(response.getBody())) {
      return ResponseEntity.status(HttpStatus.OK).body(this.service.disabledOrEnabledProduct(isActive, id));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @DeleteMapping("/deleteProduct/{id}")
  public ResponseEntity<GenericResponse<ProductResponseDto>> deleteProductById(@PathVariable(value = "id") final Integer id) {
    final GenericResponse<ProductResponseDto> response = this.service.findById(id);
    if (ObjectUtils.isNotEmpty(response.getBody())) {
      return ResponseEntity.status(HttpStatus.OK).body(this.service.deleteProduct(id));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }
}
