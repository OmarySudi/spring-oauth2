package com.carpool.auth.Controller;

import com.carpool.auth.dto.UserCreateDTO;
import com.carpool.auth.model.User;
import com.carpool.auth.service.UserCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    UserCommandService userCommandService;

    //@PreAuthorize("permitAll()")
    @PostMapping(value = "/register")
    public ResponseEntity<User> createUser(@RequestBody UserCreateDTO userCreateDTO){

        return new ResponseEntity<>(userCommandService.createUser(userCreateDTO),HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> fetchAll(){
        return new ResponseEntity<>(userCommandService.getAllUsers(),HttpStatus.OK);
    }

    @PostMapping(value="/set-user-roles")
    public ResponseEntity<User> setUserRoles(@RequestBody UserCreateDTO userCreateDTO){
        return new ResponseEntity<>(userCommandService.setRoles(userCreateDTO),HttpStatus.OK);
    }
}
