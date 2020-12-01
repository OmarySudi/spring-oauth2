package com.carpool.auth.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String roleName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="permission_role",joinColumns = {
            @JoinColumn(name = "role_id",referencedColumnName = "id")},inverseJoinColumns = {
            @JoinColumn(name = "permission_id",referencedColumnName = "id") })
    private List<Permission> permissions;

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

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
