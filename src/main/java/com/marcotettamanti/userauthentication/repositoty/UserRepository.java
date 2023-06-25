package com.marcotettamanti.userauthentication.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcotettamanti.userauthentication.model.entities.User;
import java.util.List;




public interface UserRepository extends JpaRepository<User, Long> {

  User findByEmail(String email);
  User findByCodSecurity(String codSecurity);
}
