package com.marcotettamanti.userauthentication.model.entities;

import com.marcotettamanti.userauthentication.model.primarykey.UserPermissionPK;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@EqualsAndHashCode
@Setter
@Getter
@Table(name = "tb_user_permission")
public class UserPermission {
  
  @EmbeddedId
  private UserPermissionPK id;

  @ManyToOne
  @MapsId("userId")
  private User user;

  @ManyToOne
  @MapsId("permissionId")
  private Permission permission;
}
