package com.marcotettamanti.userauthentication.model.entities;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_user")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements UserDetails{
  
  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String email;
  private String telephone;
  private String password;
  private LocalDateTime dateSendCode;
  private String codSecurity;

  @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private Set<UserPermission> userPermissions;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    return userPermissions;
  }

  @Override
  public String getUsername() {
    
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {

     return true;
  }

  @Override
  public boolean isEnabled() {
    
    return true;
  }
}
