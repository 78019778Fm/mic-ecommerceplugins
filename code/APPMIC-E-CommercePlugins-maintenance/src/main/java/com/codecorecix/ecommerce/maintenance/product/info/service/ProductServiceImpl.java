package com.codecorecix.ecommerce.maintenance.product.info.service;

import java.util.List;
import java.util.Optional;

import com.codecorecix.ecommerce.event.entities.Product;
import com.codecorecix.ecommerce.event.models.ProductInfo;
import com.codecorecix.ecommerce.maintenance.product.detail.service.ProductDetailService;
import com.codecorecix.ecommerce.maintenance.product.image.service.ProductImageService;
import com.codecorecix.ecommerce.maintenance.product.info.api.dto.request.ProductRequestDto;
import com.codecorecix.ecommerce.maintenance.product.info.api.dto.response.ProductResponseDto;
import com.codecorecix.ecommerce.maintenance.product.info.mapper.ProductFieldsMapper;
import com.codecorecix.ecommerce.maintenance.product.info.repository.ProductRepository;
import com.codecorecix.ecommerce.maintenance.product.info.utils.ProductConstants;
import com.codecorecix.ecommerce.utils.GenericResponse;
import com.codecorecix.ecommerce.utils.GenericResponseConstants;
import com.codecorecix.ecommerce.utils.GenericUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductDetailService productDetailService;

  private final ProductImageService productImageService;

  private final ProductRepository productRepository;

  private final ProductFieldsMapper mapper;

  @Override
  public GenericResponse<List<ProductResponseDto>> getAllProducts() {
    return new GenericResponse<>(GenericResponseConstants.RPTA_OK, GenericResponseConstants.CORRECT_OPERATION,
        this.mapper.toDto(this.productRepository.findAll()));
  }

  @Override
  public GenericResponse<List<ProductResponseDto>> getActiveProducts() {
    return new GenericResponse<>(GenericResponseConstants.RPTA_OK, GenericResponseConstants.CORRECT_OPERATION,
        this.mapper.toDto(this.productRepository.findByIsActiveIsTrue()));
  }

  @Override
  @Transactional
  public GenericResponse<ProductResponseDto> save(final ProductRequestDto productRequestDto) {
    final Product productInfo = this.mapper.sourceToDestination(productRequestDto);
    final Product product = this.productRepository.save(productInfo);
    return new GenericResponse<>(GenericResponseConstants.RPTA_OK, GenericResponseConstants.CORRECT_OPERATION,
        this.mapper.destinationToSource(product));
  }

  @Override
  @Transactional
  public GenericResponse<ProductResponseDto> deleteProductById(final Integer id) {
    final Optional<Product> product = this.productRepository.findById(id);
    if (product.isPresent()) {
      this.productDetailService.deleteAllDetailsByProductId(id);
      this.productImageService.deleteAllImagesByProductId(id);
      this.productRepository.deleteById(id);
      return new GenericResponse<>(GenericResponseConstants.RPTA_OK, GenericResponseConstants.CORRECT_OPERATION, null);
    } else {
      return new GenericResponse<>(GenericResponseConstants.RPTA_ERROR,
          StringUtils.joinWith(GenericResponseConstants.DASH, GenericResponseConstants.INCORRECT_OPERATION,
              ProductConstants.FIND_MESSAGE_ERROR),
          null);
    }
  }

  @Override
  @Transactional
  public GenericResponse<ProductResponseDto> updateProductStatus(final Boolean isActive, final Integer id) {
    final Optional<Product> product = this.productRepository.findById(id);
    if (product.isPresent()) {
      this.productRepository.disabledOrEnabledProduct(isActive, id);
      return new GenericResponse<>(GenericResponseConstants.RPTA_OK, GenericResponseConstants.CORRECT_OPERATION, null);
    } else {
      return new GenericResponse<>(GenericResponseConstants.RPTA_ERROR,
          StringUtils.joinWith(GenericResponseConstants.DASH, GenericResponseConstants.INCORRECT_OPERATION,
              ProductConstants.FIND_MESSAGE_ERROR),
          null);
    }
  }

  @Override
  public GenericResponse<ProductResponseDto> findById(final Integer id) {
    final Optional<Product> product = this.productRepository.findById(id);
    return product.map(
            value -> GenericUtils.buildGenericResponseSuccess(ProductConstants.FIND_MESSAGE, this.mapper.destinationToSource(value)))
        .orElseGet(() -> GenericUtils.buildGenericResponseError(ProductConstants.FIND_MESSAGE_ERROR, null));
  }

  @Override
  public GenericResponse<List<ProductInfo>> findByIds(final List<Integer> ids) {
    return new GenericResponse<>(GenericResponseConstants.RPTA_OK, GenericResponseConstants.CORRECT_OPERATION,
        this.productRepository.findByProductsByIds(ids));
  }
}
