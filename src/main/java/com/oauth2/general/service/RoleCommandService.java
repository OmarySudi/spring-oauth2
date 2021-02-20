package com.oauth2.general.service;

import com.oauth2.general.dto.RoleCreateDTO;
import com.oauth2.general.model.Permission;
import com.oauth2.general.model.Role;

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
