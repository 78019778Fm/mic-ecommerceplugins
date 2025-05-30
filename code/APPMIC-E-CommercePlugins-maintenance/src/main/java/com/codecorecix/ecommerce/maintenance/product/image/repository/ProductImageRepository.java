package com.codecorecix.ecommerce.maintenance.product.image.repository;

import java.util.List;
import java.util.Optional;

import com.codecorecix.ecommerce.event.entities.ProductImage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {

  @Query("SELECT PI FROM ProductImage PI WHERE PI.imageUrl = :fileId")
  Optional<ProductImage> findProductImageByImageUrl(final String fileId);

  @Query("SELECT PI FROM ProductImage PI WHERE PI.product.id = :productId")
  List<ProductImage> findProductImagesByProductId(final Integer productId);

  @Modifying
  @Query("DELETE FROM ProductImage PI WHERE PI.id = :imageId")
  void deleteImage(final Integer imageId);

  @Modifying
  @Query("DELETE FROM ProductImage PI WHERE PI.product.id = :productId")
  void deleteAllImagesByProductId(@Param("productId") Integer productId);
}
