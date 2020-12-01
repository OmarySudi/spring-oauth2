package com.carpool.auth.service;

import com.carpool.auth.dto.RoleCreateDTO;
import com.carpool.auth.model.Permission;
import com.carpool.auth.model.Role;

import java.util.List;

public interface RoleCommandService {

    List<Permission> setPermissionList(String permissions[]);

    void setRolePermissions(Integer roleID,String[] permissions);

    RoleCreateDTO updateRole(Integer id,RoleCreateDTO roleCreateDTO);

    Role createRole(RoleCreateDTO roleCreateDTO);

    String deleteRole(Integer id);

    Role getRole(Integer id);

    List<Role> getRoles();
}
