package com.oauth2.general.service;

import com.oauth2.general.dto.RoleCreateDTO;
import com.oauth2.general.exeption.EntityNotFoundException;
import com.oauth2.general.exeption.InternalServerErrorException;
import com.oauth2.general.model.Permission;
import com.oauth2.general.model.Role;
import com.oauth2.general.repository.UserPermissionRepository;
import com.oauth2.general.response.CustomResponse;
import com.oauth2.general.response.CustomResponseCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PermissionCommandServiceImplementation implements PermissionCommandService{

    @Autowired
    UserPermissionRepository userPermissionRepository;

    @Override
    public CustomResponse<Permission> createPermission(Permission permission) {
        CustomResponse<Permission> response = new CustomResponse<>();
        List<Permission> permissions = new ArrayList<>();

        try{

            Permission newPermission = new Permission();

            newPermission.setPermissionName(permission.getPermissionName());

            userPermissionRepository.save(newPermission);

            permissions.add(newPermission);
            response.setObjects(permissions);
            response.setGeneralErrorCode(CustomResponseCodes.OPERATION_SUCCESSFULLY);
            response.setMessage("Permission has been created successfully");
        }
        catch(Exception ex){

            response.setGeneralErrorCode(CustomResponseCodes.FAILED_TO_CREATE_RECORD);
            response.addErrorToList("Operation failed");
            response.setMessage("Permission failed to be created");
            response.setDetails("There is an internal error which caused permission creation to fail");
        }

        return response;
    }

    @Override
    public CustomResponse<Permission> updatePermission(Integer id,Permission permission) {

        CustomResponse<Permission> response = new CustomResponse<>();
        List<Permission> permissions = new ArrayList<>();
        Optional<Permission> optionalPermission = userPermissionRepository.findById(id);

        if(optionalPermission.isPresent()) {

            Permission currentPermission = optionalPermission.get();
            Permission updatedPermission;

            currentPermission.setPermissionName(permission.getPermissionName());
            updatedPermission = userPermissionRepository.save(currentPermission);

            permissions.add(updatedPermission);
            response.setObjects(permissions);
            response.setGeneralErrorCode(CustomResponseCodes.OPERATION_SUCCESSFULLY);
            response.setMessage("Permission has been updated successfully");

        }
        else{

            response.setGeneralErrorCode(CustomResponseCodes.RECORD_NOT_FOUND);
            response.addErrorToList("No permission found");
            response.setMessage("There is no permission found with the supplied id");
            response.setDetails("No permission with the specified id found in the database, make sure you have supplied correct id");
        }

        return response;
    }

    @Override
    public CustomResponse<Permission> deletePermission(Integer id) {

        CustomResponse<Permission> response = new CustomResponse<>();
        List<Permission> permissions = new ArrayList<>();
        Optional<Permission> optionalPermission = userPermissionRepository.findById(id);

        if(optionalPermission.isPresent()){

            try{

                userPermissionRepository.delete(optionalPermission.get());
                permissions.add(optionalPermission.get());
                response.setObjects(permissions);
                response.setGeneralErrorCode(CustomResponseCodes.OPERATION_SUCCESSFULLY);
                response.setMessage("Permission has been successfully deleted");

            }catch(Exception ex){

                response.setGeneralErrorCode(CustomResponseCodes.DELETE_OPERATION_FAILED);
                response.addErrorToList("Operation failed");
                response.setMessage("Failed to delete a permission from database");
                response.setDetails("There is an internal error which caused permission deletion to fail");
            }

        }else{

            response.setGeneralErrorCode(CustomResponseCodes.USER_NOT_FOUND);
            response.addErrorToList("No permission");
            response.setMessage("There is no permission found with the supplied ID");
            response.setDetails("No permission with the specified ID found in the database, make sure you have supplied correct ID");
        }

        return response;
    }

    @Override
    public CustomResponse<Permission> getPermission(Integer id) {

        CustomResponse<Permission> response = new CustomResponse<>();
        List<Permission> permissions = new ArrayList<>();
        Optional<Permission> optionalPermission = userPermissionRepository.findById(id);

        if(optionalPermission.isPresent()){

            permissions.add(optionalPermission.get());
            response.setObjects(permissions);
            response.setGeneralErrorCode(CustomResponseCodes.OPERATION_SUCCESSFULLY);
            response.setMessage("Operation successfully");

        }else {

            response.setGeneralErrorCode(CustomResponseCodes.RECORD_NOT_FOUND);
            response.addErrorToList("No permission found");
            response.setMessage("There is no permission found with the supplied id");
            response.setDetails("No permission with the specified id found in the database, make sure you have supplied correct id");
        }

        return response;
    }

    @Override
    public CustomResponse<Permission> getPermissions() {

        List<Permission> permissions = userPermissionRepository.findAll();
        CustomResponse<Permission> response = new CustomResponse<>();

        if(permissions.size() > 0){

            response.setObjects(permissions);
            response.setGeneralErrorCode(CustomResponseCodes.OPERATION_SUCCESSFULLY);
            response.setMessage("Operation successfully");

        } else{

            response.setGeneralErrorCode(CustomResponseCodes.USER_NOT_FOUND);
            response.addErrorToList("No permissions");
            response.setMessage("There are no permissions found in the database");
            response.setDetails("There are no permissions found in the database");
        }

        return response;
    }
}
