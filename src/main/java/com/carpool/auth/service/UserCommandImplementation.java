package com.carpool.auth.service;

import com.carpool.auth.dto.UserCreateDTO;
import com.carpool.auth.exeption.EntityExistException;
import com.carpool.auth.exeption.EntityNotFoundException;
import com.carpool.auth.exeption.InternalServerErrorException;
import com.carpool.auth.model.Mail;
import com.carpool.auth.model.Role;
import com.carpool.auth.model.User;
import com.carpool.auth.repository.UserDetailsRepository;
import com.carpool.auth.repository.UserPermissionRepository;
import com.carpool.auth.repository.UserRoleRepository;
import com.carpool.auth.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public User createUser(UserCreateDTO userCreateDTO){
        Optional<User> optionalUser = userDetailsRepository.findByUsername(userCreateDTO.getUsername());

        if(!optionalUser.isPresent())
        {
            if(userCreateDTO.getPassword().equals(userCreateDTO.getPassword_confirm()))
            {
                try{

                    User newUser = new User();

                    newUser.setUsername(userCreateDTO.getUsername());
                    newUser.setFullName(userCreateDTO.getFullName());
                    //newUser.setEmail(userCreateDTO.getEmail());
                    newUser.setPhoneNumber(userCreateDTO.getPhoneNumber());
                    newUser.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));

                    newUser.setRoles(this.setRolesList(userCreateDTO.getRoles()));

                    newUser.setUserID(Utilities.generateRandomString());

                    Mail mail = new Mail();
                    mail.setMailTo(userCreateDTO.getUsername());
                    mail.setSubject("Account Confirmation");
                    mail.setFrom("kekovasudi@gmail.com");
                    //mail.setText("This email is for verification of email");

                    Map<String,Object> model = new HashMap<String,Object>();
                    model.put("link","http://localhost:9090/api/v1/users/registration_redirect?userID="+newUser.getUserID());
                    mail.setProps(model);

                    emailService.sendComplexEmail(mail);
                    //emailService.sendSimpleEmail(mail);

                    return userDetailsRepository.save(newUser);

                } catch(Exception ex){

                    throw new InternalServerErrorException("Can not register user, there is internal server error");
                }
            }
            else
                throw new InternalServerErrorException("Passwords does not match");

        }else
            throw new InternalServerErrorException("Email is already used, use other email");
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

    @Override
    public String deleteUser(String userID) {
        Optional<User> optionalUser = userDetailsRepository.findByUserID(userID);

        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            try{
                userDetailsRepository.delete(user);

                return "User has successfully been deleted";

            }catch(Exception ex){
                throw new InternalServerErrorException("There is a server problem when deleting user, try again later");
            }

        }else
            throw new EntityNotFoundException("There is no user of "+userID+" found in the database");
    }
}
