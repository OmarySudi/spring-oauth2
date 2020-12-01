package com.carpool.auth.service;

import com.carpool.auth.model.Permission;
import com.carpool.auth.model.Role;

import java.util.List;

public interface PermissionCommandService {

    Permission createPermission(Permission permission);

    Permission updatePermission(Integer id,Permission permission);

    String deletePermission(Integer id);

    Permission getPermission(Integer id);

    List<Permission> getPermissions();
}
