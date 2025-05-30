package com.codecorecix.ecommerce.maintenance.brand.service;

import java.util.List;

import com.codecorecix.ecommerce.maintenance.brand.api.dto.request.BrandRequestDto;
import com.codecorecix.ecommerce.maintenance.brand.api.dto.response.BrandResponseDto;
import com.codecorecix.ecommerce.utils.GenericResponse;

public interface BrandService {

  /**
   * Method used to list all brand.
   *
   * @return List of BrandResponseDto.
   */
  GenericResponse<List<BrandResponseDto>> getAllBrands();

  /**
   * Method used to list all active brands.
   *
   * @return List of BrandResponseDto.
   */
  GenericResponse<List<BrandResponseDto>> getActiveBrands();

  /**
   * Method used to save the brands.
   *
   * @param brandRequestDto The brand request dto.
   * @return List of BrandResponseDto.
   */
  GenericResponse<BrandResponseDto> save(final BrandRequestDto brandRequestDto);

  /**
   * Method used to delete the brand.
   *
   * @param id The id of the brand.
   * @return List of BrandResponseDto.
   */
  GenericResponse<BrandResponseDto> deleteById(final Integer id);

  /**
   * Method used to update the status of the brand.
   *
   * @param isActive True if is active or false en otherwise.
   * @return List of BrandResponseDto.
   */
  GenericResponse<BrandResponseDto> updateBrandStatus(final Boolean isActive, final Integer id);

  /**
   * Method used to find the brand by id.
   *
   * @param id The id of the brand.
   * @return List of BrandResponseDto.
   */
  GenericResponse<BrandResponseDto> findById(final Integer id);
}
