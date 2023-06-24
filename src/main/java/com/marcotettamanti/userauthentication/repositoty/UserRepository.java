package com.marcotettamanti.userauthentication.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcotettamanti.userauthentication.model.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
  
}
