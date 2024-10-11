package com.codecorecix.ecommerce.maintenance.product.image.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import com.codecorecix.ecommerce.exceptions.MaintenanceException;
import com.codecorecix.ecommerce.maintenance.drive.api.dto.response.GoogleDriveResponse;
import com.codecorecix.ecommerce.maintenance.drive.service.GoogleDriveService;
import com.codecorecix.ecommerce.maintenance.product.image.api.dto.request.ProductImageRequestDto;
import com.codecorecix.ecommerce.maintenance.product.image.api.dto.response.ProductImageResponseDto;
import com.codecorecix.ecommerce.maintenance.product.image.service.ProductImageService;
import com.codecorecix.ecommerce.maintenance.product.info.api.dto.response.ProductResponseDto;
import com.codecorecix.ecommerce.maintenance.product.info.mapper.ProductFieldsMapper;
import com.codecorecix.ecommerce.maintenance.product.info.service.ProductService;
import com.codecorecix.ecommerce.utils.GenericResponse;
import com.codecorecix.ecommerce.utils.GenericResponseConstants;
import com.codecorecix.ecommerce.utils.GenericUtils;
import com.codecorecix.ecommerce.utils.MaintenanceErrorMessage;
import com.codecorecix.ecommerce.utils.MaintenanceUtils;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/productImage")
@RequiredArgsConstructor
public class ProductImageController {

  public static final String MESSAGE = "The product id hasn't been found in the database";

  private final GoogleDriveService googleDriveService;

  private final ProductImageService productImageService;

  private final ProductService productService;

  private final ProductFieldsMapper productFieldsMapper;

  @PostMapping("/uploadImageProduct")
  public ResponseEntity<GenericResponse<ProductImageResponseDto>> uploadImage(@RequestParam("file") final MultipartFile file,
      @RequestParam("productId") final Integer productId) {
    try {
      final Path tempDir = Files.createTempDirectory(StringUtils.EMPTY);
      final Path tempFilePath = tempDir.resolve(Objects.requireNonNull(file.getOriginalFilename()));
      Files.write(tempFilePath, file.getBytes());
      final GenericResponse<ProductResponseDto> productResponse = this.productService.findById(productId);
      if (Objects.nonNull(productResponse.getBody())) {
        final GoogleDriveResponse googleDriveResponse = this.googleDriveService.uploadFile(tempFilePath.toFile(), file.getContentType());
        Files.delete(tempFilePath);
        Files.delete(tempDir);
        final ProductImageRequestDto productImageRequestDto = new ProductImageRequestDto(null,
            StringUtils.join(GenericResponseConstants.ORIGINAL_URL, googleDriveResponse.getUrl(), GenericResponseConstants.VIEW),
            productId);
        MaintenanceUtils.validRequestDto(productImageRequestDto);
        final GenericResponse<ProductImageResponseDto> productImageResponseDto =
            GenericUtils.buildGenericResponseSuccess(null, this.productImageService.saveImage(productImageRequestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(productImageResponseDto);
      } else {
        Files.delete(tempFilePath);
        Files.delete(tempDir);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(GenericUtils.buildGenericResponseError(MESSAGE, null));
      }
    } catch (final MaintenanceException e) {
      throw new MaintenanceException(e.getErrorMessage());
    } catch (IOException e) {
      throw new MaintenanceException(MaintenanceErrorMessage.ERROR_RESOURCE_NOT_FOUND);
    }
  }

  @DeleteMapping("/deleteImageProduct/{fileId}")
  public ResponseEntity<GenericResponse<ProductImageResponseDto>> deleteImage(@PathVariable(name = "fileId") final String fileId) {
    try {
      final ProductImageResponseDto productImageResponseDto = this.productImageService.findByUrlName(fileId);
      if (StringUtils.isNotEmpty(productImageResponseDto.getImageUrl())) {
        this.googleDriveService.deleteFile(fileId);
        this.productImageService.deleteImage(productImageResponseDto.getId());
        return ResponseEntity.status(HttpStatus.OK).body(GenericUtils.buildGenericResponseSuccess(null, null));
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            GenericUtils.buildGenericResponseSuccess(null, null));
      }
    } catch (final MaintenanceException e) {
      throw new MaintenanceException(MaintenanceErrorMessage.ERROR_DELETE_IMAGE);
    }
  }
}
