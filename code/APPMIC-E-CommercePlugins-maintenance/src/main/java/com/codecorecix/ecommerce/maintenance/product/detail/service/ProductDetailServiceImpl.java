package com.codecorecix.ecommerce.maintenance.product.detail.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.codecorecix.ecommerce.event.entities.ProductDetail;
import com.codecorecix.ecommerce.exception.GenericException;
import com.codecorecix.ecommerce.maintenance.product.detail.dto.request.ProductDetailRequestDto;
import com.codecorecix.ecommerce.maintenance.product.detail.dto.response.ProductDetailResponseDto;
import com.codecorecix.ecommerce.maintenance.product.detail.mapper.ProductDetailFieldsMapper;
import com.codecorecix.ecommerce.maintenance.product.detail.repository.ProductDetailRepository;
import com.codecorecix.ecommerce.utils.GenericErrorMessage;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService {

  private final ProductDetailRepository repository;

  private final ProductDetailFieldsMapper mapper;

  @Override
  public ProductDetailResponseDto saveDetail(final ProductDetailRequestDto productDetailRequestDto) {
    final ProductDetail productDetail = this.repository.save(this.mapper.sourceToDestination(productDetailRequestDto));
    return this.mapper.destinationToSource(productDetail);
  }

  @Override
  @Transactional
  public void deleteDetail(final Integer id) {
    final Optional<ProductDetail> productDetail = this.repository.findById(id);
    if (productDetail.isPresent()) {
      this.repository.deleteDetail(id);
    } else {
      throw new GenericException(GenericErrorMessage.DATABASE_DELETE_ERROR);
    }
  }

  @Override
  public ProductDetailResponseDto findById(final Integer id) {
    final Optional<ProductDetail> productImage = this.repository.findById(id);
    return productImage.map(this.mapper::destinationToSource).orElseGet(ProductDetailResponseDto::new);
  }

  @Override
  public List<ProductDetailResponseDto> getDetailByProductId(final Integer productId) {
    final List<ProductDetail> productDetails = this.repository.findAllDetailByIdProduct(productId);
    if (ObjectUtils.isNotEmpty(productDetails)) {
      return this.mapper.toDto(productDetails);
    } else {
      return new ArrayList<>();
    }
  }

  @Override
  @Transactional
  public void deleteAllDetailsByProductId(final Integer productId) {
    try {
      this.repository.deleteAllDetailsByProductId(productId);
    } catch (final Exception e) {
      throw new GenericException(GenericErrorMessage.DATABASE_DELETE_ERROR);
    }
  }
}
