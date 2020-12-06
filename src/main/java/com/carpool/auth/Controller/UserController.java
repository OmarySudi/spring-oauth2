package com.carpool.auth.Controller;

import com.carpool.auth.dto.UserCreateDTO;
import com.carpool.auth.exeption.InternalServerErrorException;
import com.carpool.auth.model.User;
import com.carpool.auth.repository.UserDetailsRepository;
import com.carpool.auth.service.UserCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    UserCommandService userCommandService;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    //@PreAuthorize("permitAll()")
    @PostMapping(value = "/register")
    public ResponseEntity<User> createUser(@RequestBody UserCreateDTO userCreateDTO){

        return new ResponseEntity<>(userCommandService.createUser(userCreateDTO),HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> fetchAll(){
        return new ResponseEntity<>(userCommandService.getAllUsers(),HttpStatus.OK);
    }

    @GetMapping(value = "/registration_redirect")
    public ModelAndView confirm_registration(@RequestParam(name="userID") String userID,Model model){

        Optional<User> optional = userDetailsRepository.findByUserID(userID);

        User user = optional.get();

        if(user.getRoles().contains("Role_driver")){

            user.setAccountNonExpired(true);
            user.setAccountNonlocked(true);
            user.setCredentialsNonExpired(true);
            user.setEmailConfirmation(true);

        }else {

            user.setAccountNonExpired(true);
            user.setAccountNonlocked(true);
            user.setCredentialsNonExpired(true);
            user.setAccountNotification(true);
            user.setEnabled(true);
            user.setEmailConfirmation(true);

        }

        try{
            userDetailsRepository.save(user);

            ModelAndView modelAndView = new ModelAndView();

            modelAndView.setViewName("login_redirect");

            return modelAndView;

        }catch(Exception ex){
            throw new InternalServerErrorException("There is internal server error,try again to confirm");
        }

    }

    @PostMapping(value="/set-user-roles")
    public ResponseEntity<User> setUserRoles(@RequestBody UserCreateDTO userCreateDTO){
        return new ResponseEntity<>(userCommandService.setRoles(userCreateDTO),HttpStatus.OK);
    }
}
