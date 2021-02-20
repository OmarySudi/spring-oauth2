package com.oauth2.general.Controller;

import com.oauth2.general.dto.RoleCreateDTO;
import com.oauth2.general.model.Role;
import com.oauth2.general.repository.UserRoleRepository;
import com.oauth2.general.service.RoleCommandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = {"roleController"})
@RequestMapping("api/v1/roles")
public class RoleController {

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    RoleCommandService roleCommandService;

    @ApiOperation(value = "Creating a role")
    @PostMapping("/create")
    public ResponseEntity<Role> createRole(@RequestBody RoleCreateDTO roleCreateDTO){

        return new ResponseEntity<>(roleCommandService.createRole(roleCreateDTO), HttpStatus.CREATED);
    }

    @ApiOperation(value = "update permissions of a certain role")
    @PutMapping(value="/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleCreateDTO> updatePermissions(@PathVariable(value = "id") Integer id, @RequestBody RoleCreateDTO roleCreateDTO){
        return  new ResponseEntity<>(roleCommandService.updateRole(id,roleCreateDTO),HttpStatus.OK);
    }

    @ApiOperation(value = "deleting a role")
    @DeleteMapping(value="/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteRole(@PathVariable(value = "id") Integer id){

        return new ResponseEntity<>(roleCommandService.deleteRole(id),HttpStatus.OK);
    }

    @ApiOperation(value = "fetch a role")
    @GetMapping(value="/{id}")
    public ResponseEntity<Role> getRole(@PathVariable(value="id") Integer id){

        return new ResponseEntity<>(roleCommandService.getRole(id),HttpStatus.OK);
    }

    @ApiOperation(value = "fetching all roles")
    @GetMapping("/all")
    public ResponseEntity<List<Role>> getRoles(){
        return new ResponseEntity<>(roleCommandService.getRoles(),HttpStatus.OK);
    }
}
