package com.carpool.auth.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    @NotNull
    private String permissionName;

    public Permission() {
    }

    public Permission(Integer id, String permissionName) {
        this.id = id;
        this.permissionName = permissionName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "name='" + permissionName + '\'' +
                '}';
    }
}
