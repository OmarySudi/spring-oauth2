package com.oauth2.general.service;

import com.oauth2.general.dto.UserCreateDTO;
import com.oauth2.general.model.Role;
import com.oauth2.general.model.User;
import com.oauth2.general.response.CustomResponse;

import java.util.List;

public interface UserCommandService {

    public CustomResponse<User> createUser(UserCreateDTO userCreateDTO);

    List<Role> setRolesList(String roles[]);

    CustomResponse<User> setRoles(UserCreateDTO userCreateDTO);

    CustomResponse<User> getAllUsers();

    CustomResponse<User> getUser(String userID);

    CustomResponse<User> getUserByEmail(String email);

    CustomResponse<User> deleteUser(String userID);
}
