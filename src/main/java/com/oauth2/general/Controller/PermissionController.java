package com.oauth2.general.Controller;

import com.oauth2.general.model.Permission;
import com.oauth2.general.response.CustomResponse;
import com.oauth2.general.service.PermissionCommandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    public ResponseEntity<CustomResponse<Permission>> createPermission(@RequestBody Permission permission){
        return new ResponseEntity<>(permissionCommandService.createPermission(permission), HttpStatus.CREATED);
    }

    @ApiOperation(value = "updating a permission")
    @PutMapping(value="/{id}")
    public ResponseEntity<CustomResponse<Permission>> updatePermission(@PathVariable(value="id") Integer id,@RequestBody Permission permission){

        return new ResponseEntity<>(permissionCommandService.updatePermission(id,permission),HttpStatus.OK);
    }

    @ApiOperation(value = "deleting a permission")
    @DeleteMapping(value="/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomResponse<Permission>> deletePermission(@PathVariable(value = "id") Integer id){

        return new ResponseEntity<>(permissionCommandService.deletePermission(id),HttpStatus.OK);
    }

    @ApiOperation(value = "get a certain permission")
    @GetMapping(value="/{id}")
    public ResponseEntity<CustomResponse<Permission>> getPermission(@PathVariable(value="id") Integer id){

        return new ResponseEntity<>(permissionCommandService.getPermission(id),HttpStatus.OK);
    }

    @ApiOperation(value = "get all permissions")
    @GetMapping("/all")
    public ResponseEntity<CustomResponse<Permission>> getPermissions(){
        return new ResponseEntity<>(permissionCommandService.getPermissions(),HttpStatus.OK);
    }

}
