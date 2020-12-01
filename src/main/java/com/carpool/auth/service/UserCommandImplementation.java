package com.carpool.auth.service;

import com.carpool.auth.dto.UserCreateDTO;
import com.carpool.auth.exeption.EntityNotFoundException;
import com.carpool.auth.exeption.InternalServerErrorException;
import com.carpool.auth.model.Permission;
import com.carpool.auth.model.Role;
import com.carpool.auth.model.User;
import com.carpool.auth.repository.UserDetailsRepository;
import com.carpool.auth.repository.UserPermissionRepository;
import com.carpool.auth.repository.UserRoleRepository;
import com.carpool.auth.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserCommandImplementation implements UserCommandService{

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    UserCommandService userCommandService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    UserPermissionRepository userPermissionRepository;

    @Override
    public User createUser(UserCreateDTO userCreateDTO) {

        try{
            User newUser = new User();

            newUser.setUsername(userCreateDTO.getUsername());
            newUser.setFullName(userCreateDTO.getFullName());
            //newUser.setEmail(userCreateDTO.getEmail());
            newUser.setPhoneNumber(userCreateDTO.getPhoneNumber());
            newUser.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));

            newUser.setRoles(this.setRolesList(userCreateDTO.getRoles()));

            newUser.setUserID(Utilities.generateRandomString());

            return userDetailsRepository.save(newUser);

        } catch(Exception ex){

            throw new InternalServerErrorException("Can not register user, there is internal server error");
        }
    }

    @Override
    public List<Role> setRolesList(String[] roles) {

        List<Role> rolesList = new ArrayList<>();

        int i;

        if(roles.length > 0){

            for(i=0; i< roles.length; i++){
                rolesList.add(userRoleRepository.findByRoleNameContaining(roles[i]));
            }
        }

        return rolesList;
    }

    @Override
    public User setRoles(UserCreateDTO userCreateDTO) {

        Optional<User> optionalUser = userDetailsRepository.findByUserID(userCreateDTO.getUserID());

        if(optionalUser.isPresent()){
            optionalUser.get().setRoles(userCommandService.setRolesList(userCreateDTO.getRoles()));
        } else {
            throw new EntityNotFoundException("The user with userID "+userCreateDTO.getUserID()+"is no found in the database");
        }

        return optionalUser.get();
    }

    @Override
    public List<User> getAllUsers() {

        List<User> users = userDetailsRepository.findAll();

        if(users.size() > 0){
            return users;
        } else
             throw new EntityNotFoundException("There are no users in the database");
    }

    @Override
    public User getUser(String userID) {

        Optional<User> optionalUser = userDetailsRepository.findByUserID(userID);

        optionalUser.orElseThrow(()->new EntityNotFoundException("The is no user with id "+userID+" found in the database"));

        return optionalUser.get();
    }
}
