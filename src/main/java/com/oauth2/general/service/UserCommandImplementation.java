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
    public User createUser(UserCreateDTO userCreateDTO){
        Optional<User> optionalUser = userDetailsRepository.findByEmail(userCreateDTO.getEmail());

        if(!optionalUser.isPresent())
        {
            if(userCreateDTO.getPassword().equals(userCreateDTO.getPassword_confirm()))
            {
                try{

                    User newUser = new User();

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
        User foundUser;
        Optional<User> optionalUser = userDetailsRepository.findByUserID(userCreateDTO.getUserID());

        if(optionalUser.isPresent()){
            optionalUser.get().setRoles(userCommandService.setRolesList(userCreateDTO.getRoles()));
            foundUser = userDetailsRepository.save(optionalUser.get());
        } else {
            throw new EntityNotFoundException("The user with userID "+userCreateDTO.getUserID()+"is no found in the database");
        }

        return foundUser;
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
    public User getUserByEmail(String email) {

        Optional<User> optionalUser = userDetailsRepository.findByEmail(email);

        optionalUser.orElseThrow(()->new EntityNotFoundException("The is no user with email "+email+" found in the database"));

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
