package com.carpool.auth.dto;

import com.carpool.auth.model.Permission;
import com.carpool.auth.service.RoleCommandService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RoleCreateDTO {

    @Autowired
    RoleCommandService roleCommandService;

    private Integer id;

    private String roleName;

    private String[] permissions;

    List<Permission> permissionList;

    public RoleCreateDTO() {
    }

    public RoleCreateDTO(Integer id, String roleName, List<Permission> permissionList) {
        this.id = id;
        this.roleName = roleName;
        this.permissionList = permissionList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String[] getPermissions() {
        return this.permissions;
    }

    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }

    public List<Permission> setPermissionList(String[] permissions){

        this.permissionList = roleCommandService.setPermissionList(permissions);

        return permissionList;
    }
}
