package com.oauth2.general.service;

import com.oauth2.general.dto.RoleCreateDTO;
import com.oauth2.general.exeption.EntityNotFoundException;
import com.oauth2.general.exeption.InternalServerErrorException;
import com.oauth2.general.model.Permission;
import com.oauth2.general.model.Role;
import com.oauth2.general.repository.UserPermissionRepository;
import com.oauth2.general.repository.UserRoleRepository;
import com.oauth2.general.response.CustomResponse;
import com.oauth2.general.response.CustomResponseCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleCommandServiceImplementation implements RoleCommandService{

    @Autowired
    UserPermissionRepository userPermissionRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    RoleCommandService roleCommandService;

    @Override
    public List<Permission> setPermissionList(String permissions[]) {

        List<Permission> permissionList = new ArrayList<>();

        int i;

        if(permissions.length > 0){

            for(i=0; i< permissions.length; i++){
                permissionList.add(userPermissionRepository.findByPermissionNameContaining(permissions[i]));
            }
        }

        return permissionList;
    }

    @Override
    public void setRolePermissions(Integer roleID,String[] permissions) {

        Optional<Role> role = userRoleRepository.findById(roleID);

        if(role.isPresent()) {
            role.get().setPermissions(this.setPermissionList(permissions));
        } else {
            throw new EntityNotFoundException("You can't set permissions to this role, role with id "+roleID.toString()+" is not found in the database");
        }
    }

    @Override
    public CustomResponse<RoleCreateDTO> updateRole(Integer id, RoleCreateDTO roleCreateDTO) {

        CustomResponse<RoleCreateDTO> response = new CustomResponse<>();
        List<RoleCreateDTO> roles = new ArrayList<>();
        Optional<Role> optionalRole = userRoleRepository.findById(id);

        if(optionalRole.isPresent()) {

            Role currentRole = optionalRole.get();
            Role updatedRole;
            RoleCreateDTO roleCreateDTO1;

            currentRole.setRoleName(roleCreateDTO.getRoleName());

            if(roleCreateDTO.getPermissions().length > 0)
                currentRole.setPermissions(this.setPermissionList(roleCreateDTO.getPermissions()));

            updatedRole = userRoleRepository.save(currentRole);

            roleCreateDTO1 =  new RoleCreateDTO(updatedRole.getId(),updatedRole.getRoleName(),updatedRole.getPermissions());

            roles.add(roleCreateDTO1);
            response.setObjects(roles);
            response.setGeneralErrorCode(CustomResponseCodes.OPERATION_SUCCESSFULLY);
            response.setMessage("Role has been updated successfully");

        }
        else{

            response.setGeneralErrorCode(CustomResponseCodes.RECORD_NOT_FOUND);
            response.addErrorToList("No role found");
            response.setMessage("There is no role found with the supplied id");
            response.setDetails("No role with the specified id found in the database, make sure you have supplied correct id");
        }

        return response;
    }

    @Override
    public CustomResponse<Role> createRole(RoleCreateDTO roleCreateDTO) {
        CustomResponse<Role> response = new CustomResponse<>();
        List<Role> roles = new ArrayList<>();
        Role newRole = new Role();

        try{

            newRole.setRoleName(roleCreateDTO.getRoleName());
            newRole.setPermissions(this.setPermissionList(roleCreateDTO.getPermissions()));
            userRoleRepository.save(newRole);

            roles.add(newRole);
            response.setObjects(roles);
            response.setGeneralErrorCode(CustomResponseCodes.OPERATION_SUCCESSFULLY);
            response.setMessage("Role has been created successfully");
        }
        catch(Exception ex){

            response.setGeneralErrorCode(CustomResponseCodes.FAILED_TO_CREATE_RECORD);
            response.addErrorToList("Operation failed");
            response.setMessage("Role failed to be created");
            response.setDetails("There is an internal error which caused role creation to fail");
        }

       return response;
    }

    @Override
    public CustomResponse<Role> deleteRole(Integer id){
        CustomResponse<Role> response = new CustomResponse<>();
        List<Role> roles = new ArrayList<>();
        Optional<Role> optionalRole = userRoleRepository.findById(id);
        Role role;

        if(optionalRole.isPresent()){
            role = optionalRole.get();

            try{

                userRoleRepository.delete(role);
                roles.add(role);
                response.setObjects(roles);
                response.setGeneralErrorCode(CustomResponseCodes.OPERATION_SUCCESSFULLY);
                response.setMessage("Role has been successfully deleted");

            }catch(Exception ex){

                response.setGeneralErrorCode(CustomResponseCodes.DELETE_OPERATION_FAILED);
                response.addErrorToList("Operation failed");
                response.setMessage("Failed to delete a role from database");
                response.setDetails("There is an internal error which caused role deletion to fail");
            }

        }else{

            response.setGeneralErrorCode(CustomResponseCodes.USER_NOT_FOUND);
            response.addErrorToList("No role");
            response.setMessage("There is no role found with the supplied ID");
            response.setDetails("No role with the specified ID found in the database, make sure you have supplied correct ID");
        }

        return response;
    }

    @Override
    public CustomResponse<Role> getRole(Integer id) {

        CustomResponse<Role> response = new CustomResponse<>();
        List<Role> roles = new ArrayList<>();
        Optional<Role> optionalRole = userRoleRepository.findById(id);

        if(optionalRole.isPresent()){

            roles.add(optionalRole.get());
            response.setObjects(roles);
            response.setGeneralErrorCode(CustomResponseCodes.OPERATION_SUCCESSFULLY);
            response.setMessage("Operation successfully");

        }else {

            response.setGeneralErrorCode(CustomResponseCodes.RECORD_NOT_FOUND);
            response.addErrorToList("No role found");
            response.setMessage("There is no role found with the supplied id");
            response.setDetails("No role with the specified id found in the database, make sure you have supplied correct id");
        }

        return response;
    }

    @Override
    public CustomResponse<Role> getRoles() {

        List<Role> roles = userRoleRepository.findAll();
        CustomResponse<Role> response = new CustomResponse<>();

        if(roles.size() > 0){

            response.setObjects(roles);
            response.setGeneralErrorCode(CustomResponseCodes.OPERATION_SUCCESSFULLY);
            response.setMessage("Operation successfully");

        } else{

            response.setGeneralErrorCode(CustomResponseCodes.USER_NOT_FOUND);
            response.addErrorToList("No roles");
            response.setMessage("There are no roles found in the database");
            response.setDetails("There are no roles found in the database");
        }

        return response;
    }
}
