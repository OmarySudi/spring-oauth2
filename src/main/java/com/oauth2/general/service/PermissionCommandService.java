package com.oauth2.general.service;

import com.oauth2.general.model.Permission;

import java.util.List;

public interface PermissionCommandService {

    Permission createPermission(Permission permission);

    Permission updatePermission(Integer id,Permission permission);

    String deletePermission(Integer id);

    Permission getPermission(Integer id);

    List<Permission> getPermissions();
}
