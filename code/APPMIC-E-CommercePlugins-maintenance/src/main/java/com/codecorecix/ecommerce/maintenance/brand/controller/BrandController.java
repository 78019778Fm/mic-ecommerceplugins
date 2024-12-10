package com.codecorecix.ecommerce.maintenance.brand.controller;

import java.util.List;
import java.util.Objects;

import com.codecorecix.ecommerce.exception.GenericUnprocessableEntityException;
import com.codecorecix.ecommerce.maintenance.brand.api.dto.request.BrandRequestDto;
import com.codecorecix.ecommerce.maintenance.brand.api.dto.response.BrandResponseDto;
import com.codecorecix.ecommerce.maintenance.brand.service.BrandService;
import com.codecorecix.ecommerce.maintenance.brand.utils.BrandConstants;
import com.codecorecix.ecommerce.utils.GenericResponse;
import com.codecorecix.ecommerce.utils.MaintenanceUtils;

import jakarta.validation.Valid;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/brands")
public class BrandController {

  private final BrandService service;

  public BrandController(final BrandService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<GenericResponse<List<BrandResponseDto>>> getAllBrands() {
    return ResponseEntity.status(HttpStatus.OK).body(this.service.getAllBrands());
  }

  @GetMapping("/active")
  public ResponseEntity<GenericResponse<List<BrandResponseDto>>> getActiveBrands() {
    return ResponseEntity.status(HttpStatus.OK).body(this.service.getActiveBrands());
  }

  @GetMapping("/{id}")
  public ResponseEntity<GenericResponse<BrandResponseDto>> getBrandById(@PathVariable(value = "id") final Integer id) {
    final GenericResponse<BrandResponseDto> response = this.service.findById(id);
    if (Objects.nonNull(response.getBody())) {
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @PostMapping
  public ResponseEntity<GenericResponse<BrandResponseDto>> saveBrand(@RequestBody final BrandRequestDto brandRequestDto) {
    if (ObjectUtils.isNotEmpty(brandRequestDto.getId())) {
      throw new GenericUnprocessableEntityException(BrandConstants.UNPROCESSABLE_ENTITY_EXCEPTION);
    } else {
      MaintenanceUtils.validRequestDto(brandRequestDto);
      return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(brandRequestDto));
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<GenericResponse<BrandResponseDto>> updateBrand(@Valid @PathVariable(value = "id") final Integer id,
      @RequestBody final BrandRequestDto brandRequestDto) {
    final GenericResponse<BrandResponseDto> response = this.service.findById(id);
    if (ObjectUtils.isNotEmpty(response.getBody())) {
      brandRequestDto.setId(id);
      MaintenanceUtils.validRequestDto(brandRequestDto);
      return ResponseEntity.status(HttpStatus.OK).body(this.service.save(brandRequestDto));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @PatchMapping("/{id}/status")
  public ResponseEntity<GenericResponse<BrandResponseDto>> updateBrandStatus(@PathVariable(value = "id") final Integer id,
      @RequestParam(value = "isActive") final Boolean isActive) {
    final GenericResponse<BrandResponseDto> response = this.service.findById(id);
    if (ObjectUtils.isNotEmpty(response.getBody())) {
      return ResponseEntity.status(HttpStatus.OK).body(this.service.updateBrandStatus(isActive, id));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<GenericResponse<BrandResponseDto>> deleteBrand(@PathVariable(value = "id") final Integer id) {
    final GenericResponse<BrandResponseDto> response = this.service.findById(id);
    if (ObjectUtils.isNotEmpty(response.getBody())) {
      return ResponseEntity.status(HttpStatus.OK).body(this.service.deleteById(id));
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }
}
