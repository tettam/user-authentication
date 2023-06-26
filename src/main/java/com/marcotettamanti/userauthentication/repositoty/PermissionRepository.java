package com.marcotettamanti.userauthentication.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcotettamanti.userauthentication.model.entities.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
  
}
