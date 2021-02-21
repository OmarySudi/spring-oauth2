package com.oauth2.general.service;

import com.oauth2.general.dto.RoleCreateDTO;
import com.oauth2.general.model.Permission;
import com.oauth2.general.model.Role;
import com.oauth2.general.response.CustomResponse;

import java.util.List;

public interface RoleCommandService {

    List<Permission> setPermissionList(String permissions[]);

    void setRolePermissions(Integer roleID,String[] permissions);

    CustomResponse<RoleCreateDTO> updateRole(Integer id,RoleCreateDTO roleCreateDTO);

    CustomResponse<Role> createRole(RoleCreateDTO roleCreateDTO);

    CustomResponse<Role> deleteRole(Integer id);

    CustomResponse<Role>  getRole(Integer id);

    CustomResponse<Role> getRoles();
}
