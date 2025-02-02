package com.codecorecix.ecommerce.maintenance.user.repository;

import com.codecorecix.ecommerce.event.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  @Modifying
  @Query("UPDATE User U SET U.isActive = ?1 WHERE U.id = ?2")
  void disabledOrEnabledUser(final Boolean isActive, final Integer id);
}
