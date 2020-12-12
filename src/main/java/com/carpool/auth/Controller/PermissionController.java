package com.carpool.auth.Controller;

import com.carpool.auth.model.Permission;
import com.carpool.auth.model.Role;
import com.carpool.auth.repository.UserPermissionRepository;
import com.carpool.auth.service.PermissionCommandService;
import io.swagger.annotations.Api;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = {"permissionController"})
@RequestMapping("api/v1/permissions")
public class PermissionController {

    @Autowired
    PermissionCommandService permissionCommandService;

    @PostMapping
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission){
        return new ResponseEntity<>(permissionCommandService.createPermission(permission), HttpStatus.CREATED);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Permission> updatePermission(@PathVariable(value="id") Integer id,@RequestBody Permission permission){

        return new ResponseEntity<>(permissionCommandService.updatePermission(id,permission),HttpStatus.OK);
    }

    @DeleteMapping(value="/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deletePermission(@PathVariable(value = "id") Integer id){

        return new ResponseEntity<>(permissionCommandService.deletePermission(id),HttpStatus.OK);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Permission> getPermission(@PathVariable(value="id") Integer id){

        return new ResponseEntity<>(permissionCommandService.getPermission(id),HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Permission>> getPermissions(){
        return new ResponseEntity<>(permissionCommandService.getPermissions(),HttpStatus.OK);
    }

}
