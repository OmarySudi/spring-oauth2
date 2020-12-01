package com.carpool.auth.service;

import com.carpool.auth.dto.UserCreateDTO;
import com.carpool.auth.model.Role;
import com.carpool.auth.model.User;

import java.util.List;

public interface UserCommandService {

    public User createUser(UserCreateDTO userCreateDTO);

    List<Role> setRolesList(String roles[]);

    User setRoles(UserCreateDTO userCreateDTO);

    List<User> getAllUsers();

    User getUser(String userID);
}
