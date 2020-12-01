package com.carpool.auth.Controller;

import com.carpool.auth.dto.RoleCreateDTO;
import com.carpool.auth.model.Role;
import com.carpool.auth.repository.UserRoleRepository;
import com.carpool.auth.service.RoleCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/roles")
public class RoleController {

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    RoleCommandService roleCommandService;

    @PostMapping("/create")
    public ResponseEntity<Role> createRole(@RequestBody RoleCreateDTO roleCreateDTO){

        return new ResponseEntity<>(roleCommandService.createRole(roleCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping(value="/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleCreateDTO> updatePermissions(@PathVariable(value = "id") Integer id, @RequestBody RoleCreateDTO roleCreateDTO){
        return  new ResponseEntity<>(roleCommandService.updateRole(id,roleCreateDTO),HttpStatus.OK);
    }

    @DeleteMapping(value="/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteRole(@PathVariable(value = "id") Integer id){

        return new ResponseEntity<>(roleCommandService.deleteRole(id),HttpStatus.OK);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Role> getRole(@PathVariable(value="id") Integer id){

        return new ResponseEntity<>(roleCommandService.getRole(id),HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Role>> getRoles(){
        return new ResponseEntity<>(roleCommandService.getRoles(),HttpStatus.OK);
    }
}
