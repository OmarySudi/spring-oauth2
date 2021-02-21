package com.oauth2.general.service;

import com.oauth2.general.model.Permission;
import com.oauth2.general.response.CustomResponse;

import java.util.List;

public interface PermissionCommandService {

    CustomResponse<Permission> createPermission(Permission permission);

    CustomResponse<Permission> updatePermission(Integer id,Permission permission);

    CustomResponse<Permission> deletePermission(Integer id);

    CustomResponse<Permission> getPermission(Integer id);

    CustomResponse<Permission> getPermissions();
}
