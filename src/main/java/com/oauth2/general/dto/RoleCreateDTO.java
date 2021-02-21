package com.oauth2.general.dto;

import com.oauth2.general.model.Permission;
import com.oauth2.general.service.RoleCommandService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.util.List;

public class RoleCreateDTO {

    @Autowired
    RoleCommandService roleCommandService;

    private Integer id;


    @ApiModelProperty(notes = "name of the role")
    @NotNull
    private String roleName;

    @ApiModelProperty(notes = "permissions of the role")
    @NotNull
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
