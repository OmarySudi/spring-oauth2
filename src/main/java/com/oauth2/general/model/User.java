package com.oauth2.general.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    public User() {
    }

    public User(User user){

        this.email = user.getEmail();
        this.accountNonExpired = user.isAccountNonExpired();
        this.enabled = user.isEnabled();
        this.credentialsNonExpired = user.isCredentialsNonExpired();
        this.accountNonlocked = user.isAccountNonlocked();
        this.password = user.getPassword();
        this.roles = user.getRoles();
        this.userID = user.getUserID();

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "accountNonExpired")
    private boolean accountNonExpired;

    @Column(name = "credentialsNonExpired")
    private boolean credentialsNonExpired;

    @Column(name = "accountNonlocked")
    private boolean accountNonlocked;

    @Column(name = "userID")
    private String userID;

    @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "role_user", joinColumns = {
                @JoinColumn(name="user_id",referencedColumnName = "id")},inverseJoinColumns = {
                @JoinColumn(name="role_id",referencedColumnName = "id")
        })
    private List<Role> roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isAccountNonlocked() {
        return accountNonlocked;
    }

    public void setAccountNonlocked(boolean accountNonlocked) {
        this.accountNonlocked = accountNonlocked;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
