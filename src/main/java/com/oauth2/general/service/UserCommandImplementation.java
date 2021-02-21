package com.oauth2.general.service;

import com.oauth2.general.dto.UserCreateDTO;
import com.oauth2.general.exeption.EntityNotFoundException;
import com.oauth2.general.exeption.InternalServerErrorException;
import com.oauth2.general.model.Mail;
import com.oauth2.general.model.Role;
import com.oauth2.general.model.User;
import com.oauth2.general.repository.UserDetailsRepository;
import com.oauth2.general.repository.UserPermissionRepository;
import com.oauth2.general.repository.UserRoleRepository;
import com.oauth2.general.response.CustomResponse;
import com.oauth2.general.response.CustomResponseCodes;
import com.oauth2.general.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.SendFailedException;
import java.util.*;

@Service
public class UserCommandImplementation implements UserCommandService{

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    UserCommandService userCommandService;

    @Autowired
    EmailService emailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    UserPermissionRepository userPermissionRepository;

    @Override
    public CustomResponse<User> createUser(UserCreateDTO userCreateDTO){
        CustomResponse<User> response = new CustomResponse<>();
        List<User> users = new ArrayList<>();
        User newUser = new User();

        Optional<User> optionalUser = userDetailsRepository.findByEmail(userCreateDTO.getEmail());

        if(!optionalUser.isPresent())
        {
            if(userCreateDTO.getPassword().equals(userCreateDTO.getPassword_confirm()))
            {
                try{
                    newUser.setEmail(userCreateDTO.getEmail());
                    newUser.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));

                    if(userCreateDTO.getRoles().length > 0)
                        newUser.setRoles(this.setRolesList(userCreateDTO.getRoles()));

                    newUser.setUserID(Utilities.generateRandomString());

                    //SENDING EMAIL:
//                    Mail mail = new Mail();
//                    mail.setMailTo(userCreateDTO.getUsername());
//                    mail.setSubject("Account Confirmation");
//                    mail.setFrom("kekovasudi@gmail.com");
//                    Map<String,Object> model = new HashMap<String,Object>();
//                    model.put("link","http://188.166.59.90:9090/api/v1/users/registration_redirect?userID="+newUser.getUserID());
//                    mail.setProps(model);
//
//                    try{
//                        emailService.sendComplexEmail(mail);
//                         }catch(SendFailedException ex){
//
//                         throw new InternalServerErrorException(ex.getMessage());
//                    }

                    userDetailsRepository.save(newUser);

                    users.add(newUser);
                    response.setObjects(users);
                    response.setGeneralErrorCode(CustomResponseCodes.OPERATION_SUCCESSFULLY);
                    response.setMessage("User has been created successfully");

                } catch(Exception ex){

                    response.setGeneralErrorCode(CustomResponseCodes.FAILED_TO_CREATE_RECORD);
                    response.addErrorToList("Operation failed");
                    response.setMessage("User failed to be created");
                    response.setDetails("There is an internal error which caused user creation to fail");
                }
            }
            else{

                response.setGeneralErrorCode(CustomResponseCodes.VALIDATION_ERROR);
                response.addErrorToList("Passwords does not match");
                response.setMessage("Passwords does not match");
                response.setDetails("Passwords and confirm password should match");
            }

        }else{
            response.setGeneralErrorCode(CustomResponseCodes.EMAIL_FOUND);
            response.addErrorToList("Email already used");
            response.setMessage("Email is already used, use other email");
            response.setDetails("There is an account with the same email found in the databases,email should be unique");
        }

        return response;
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
    public CustomResponse<User> setRoles(UserCreateDTO userCreateDTO) {
        User foundUser;
        CustomResponse<User> response = new CustomResponse<>();
        List<User> users = new ArrayList<>();
        Optional<User> optionalUser = userDetailsRepository.findByUserID(userCreateDTO.getUserID());

        if(optionalUser.isPresent()){
            optionalUser.get().setRoles(userCommandService.setRolesList(userCreateDTO.getRoles()));
            foundUser = userDetailsRepository.save(optionalUser.get());

            users.add(foundUser);
            response.setObjects(users);
            response.setGeneralErrorCode(CustomResponseCodes.OPERATION_SUCCESSFULLY);
            response.setMessage("Operation successfully");

        } else {

            response.setGeneralErrorCode(CustomResponseCodes.USER_NOT_FOUND);
            response.addErrorToList("No user");
            response.setMessage("There is no user found with the supplied ID");
            response.setDetails("No user with the specified ID found in the database, make sure you have supplied correct ID");
        }

        return response;
    }

    @Override
    public CustomResponse<User> getAllUsers() {
        List<User> users = userDetailsRepository.findAll();
        CustomResponse<User> response = new CustomResponse<>();

        if(users.size() > 0){

            response.setObjects(users);
            response.setGeneralErrorCode(CustomResponseCodes.OPERATION_SUCCESSFULLY);
            response.setMessage("Operation successfully");

        } else{

            response.setGeneralErrorCode(CustomResponseCodes.USER_NOT_FOUND);
            response.addErrorToList("No users");
            response.setMessage("There are no users found in the database");
            response.setDetails("There are no users found in the database");
        }

        return response;
    }

    @Override
    public  CustomResponse<User> getUser(String userID) {
        CustomResponse<User> response = new CustomResponse<>();
        List<User> users = new ArrayList<>();
        Optional<User> optionalUser = userDetailsRepository.findByUserID(userID);

        if(optionalUser.isPresent()){

            users.add(optionalUser.get());
            response.setObjects(users);
            response.setGeneralErrorCode(CustomResponseCodes.OPERATION_SUCCESSFULLY);
            response.setMessage("Operation successfully");
        }else {
            response.setGeneralErrorCode(CustomResponseCodes.USER_NOT_FOUND);
            response.addErrorToList("No user");
            response.setMessage("There is no user found with the supplied userID");
            response.setDetails("No user with the specified userID found in the database, make sure you have supplied correct userID");
        }

        return response;
    }

    @Override
    public CustomResponse<User> getUserByEmail(String email) {
        CustomResponse<User> response = new CustomResponse<>();
        List<User> users = new ArrayList<>();
        Optional<User> optionalUser = userDetailsRepository.findByEmail(email);

        if(optionalUser.isPresent()){
            users.add(optionalUser.get());
            response.setObjects(users);
            response.setGeneralErrorCode(CustomResponseCodes.OPERATION_SUCCESSFULLY);
            response.setMessage("Operation successfully");
        }else {
            response.setGeneralErrorCode(CustomResponseCodes.USER_NOT_FOUND);
            response.addErrorToList("No user");
            response.setMessage("There is no user found with the supplied email");
            response.setDetails("No user with the specified email found in the database, make sure you have supplied correct email");
        }

        return response;
    }

    @Override
    public CustomResponse<User> deleteUser(String userID) {
        CustomResponse<User> response = new CustomResponse<>();
        List<User> users = new ArrayList<>();
        Optional<User> optionalUser = userDetailsRepository.findByUserID(userID);
        User user;

        if(optionalUser.isPresent()){
            user = optionalUser.get();

            try{
                userDetailsRepository.delete(user);

                users.add(user);
                response.setObjects(users);
                response.setGeneralErrorCode(CustomResponseCodes.OPERATION_SUCCESSFULLY);
                response.setMessage("User has been successfully deleted");

            }catch(Exception ex){

                response.setGeneralErrorCode(CustomResponseCodes.DELETE_OPERATION_FAILED);
                response.addErrorToList("Operation failed");
                response.setMessage("Failed to delete a user from database");
                response.setDetails("There is an internal error which caused user deletion to fail");
            }

        }else{

            response.setGeneralErrorCode(CustomResponseCodes.USER_NOT_FOUND);
            response.addErrorToList("No user");
            response.setMessage("There is no user found with the supplied ID");
            response.setDetails("No user with the specified ID found in the database, make sure you have supplied correct ID");
        }

        return response;
    }


}
