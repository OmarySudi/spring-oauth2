package com.oauth2.general.Controller;

import com.oauth2.general.dto.UserCreateDTO;
import com.oauth2.general.model.User;
import com.oauth2.general.repository.UserDetailsRepository;
import com.oauth2.general.response.CustomResponse;
import com.oauth2.general.service.UserCommandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/users")
@Api(tags = {"userController"})
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserCommandService userCommandService;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    //@PreAuthorize("permitAll()")
    @ApiOperation(value = "Registering a user in the application")
    @PostMapping(value = "/register", produces = "application/json")
//    public ResponseEntity<User> createUser(@RequestBody UserCreateDTO userCreateDTO){
//
//        return new ResponseEntity<>(userCommandService.createUser(userCreateDTO),HttpStatus.CREATED);
//    }
    public ResponseEntity<CustomResponse<User>> createUser(@RequestBody UserCreateDTO userCreateDTO){
        return new ResponseEntity<>(userCommandService.createUser(userCreateDTO),HttpStatus.CREATED);
    }

    @ApiOperation(value="Fetching all users registered in the application")
    @GetMapping
    public ResponseEntity< CustomResponse<User>> fetchAll(){
        return new ResponseEntity<>(userCommandService.getAllUsers(),HttpStatus.OK);
    }

    @ApiOperation(value = "Setting roles to the certain user")
    @PostMapping(value="/set-user-roles")
    public ResponseEntity<CustomResponse<User>> setUserRoles(@RequestBody UserCreateDTO userCreateDTO){
        return new ResponseEntity<>(userCommandService.setRoles(userCreateDTO),HttpStatus.OK);
    }

    @ApiOperation(value = "Deleting a user")
    @DeleteMapping(value="/delete/{userID}")
    public ResponseEntity<CustomResponse<User>> deleteUser(@PathVariable(value="userID") String userID){

        return new ResponseEntity<>(userCommandService.deleteUser(userID),HttpStatus.OK);

    }

    @ApiOperation(value = "fetch a user by his/her email")
    @GetMapping(value="/fetch-by-email/{email}")
    public ResponseEntity<CustomResponse<User>> getUserByEmail(@PathVariable(value="email") String email){

        return new ResponseEntity<>(userCommandService.getUserByEmail(email),HttpStatus.OK);
    }

    @ApiOperation(value = "fetch a user by his/her userID")
    @GetMapping(value="/fetch-by-id/{userID}")
    public ResponseEntity< CustomResponse<User>> getUserByUserID(@PathVariable(value="userID") String userID){

        return new ResponseEntity<>(userCommandService.getUser(userID),HttpStatus.OK);
    }

}
