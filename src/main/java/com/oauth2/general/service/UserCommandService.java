package com.oauth2.general.service;

import com.oauth2.general.dto.UserCreateDTO;
import com.oauth2.general.model.Role;
import com.oauth2.general.model.User;

import java.util.List;

public interface UserCommandService {

    public User createUser(UserCreateDTO userCreateDTO);

    List<Role> setRolesList(String roles[]);

    User setRoles(UserCreateDTO userCreateDTO);

    List<User> getAllUsers();

    User getUser(String userID);

    User getUserByEmail(String email);

    String deleteUser(String userID);
}
