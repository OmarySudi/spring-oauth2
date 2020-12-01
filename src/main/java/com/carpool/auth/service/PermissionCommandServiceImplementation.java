package com.carpool.auth.service;

import com.carpool.auth.exeption.EntityNotFoundException;
import com.carpool.auth.exeption.InternalServerErrorException;
import com.carpool.auth.model.Permission;
import com.carpool.auth.model.Role;
import com.carpool.auth.repository.UserPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionCommandServiceImplementation implements PermissionCommandService{

    @Autowired
    UserPermissionRepository userPermissionRepository;

    @Override
    public Permission createPermission(Permission permission) {


        try{
            Permission newPermission = new Permission();

            newPermission.setPermissionName(permission.getPermissionName());

            return userPermissionRepository.save(newPermission);

        }catch(Exception ex){

            throw new InternalServerErrorException("Permission creation failed due to internal server error");
        }
    }

    @Override
    public Permission updatePermission(Integer id,Permission permission) {

        Optional<Permission> Permission = userPermissionRepository.findById(id);

        if(Permission.isPresent()){

            Permission currentPermission = Permission.get();

            currentPermission.setPermissionName(permission.getPermissionName());

            Permission updatedPermission = userPermissionRepository.save(currentPermission);

            return new Permission(updatedPermission.getId(),updatedPermission.getPermissionName());

        } else {
            throw new EntityNotFoundException("Permission with id "+id.toString()+"is not found in the database");
        }
    }

    @Override
    public String deletePermission(Integer id) {
        Optional<Permission> permission = userPermissionRepository.findById(id);

        if(permission.isPresent()){
            try{
                userPermissionRepository.delete(permission.get());
                return "Permission has been successfully deleted";

            }catch(Exception ex){

                throw new InternalServerErrorException("Permission deletion failed due to internal server error");
            }
        }else
            throw new EntityNotFoundException("You can't delete this permission,permission with id "+id.toString()+" is not found in the database");
    }

    @Override
    public Permission getPermission(Integer id) {
        Optional<Permission> permission = userPermissionRepository.findById(id);

        if(permission.isPresent())
            return permission.get();
        else
            throw new EntityNotFoundException("Permission with id "+id.toString()+" is not found in the database");
    }

    @Override
    public List<Permission> getPermissions() {
        List<Permission> permissionList = userPermissionRepository.findAll();

        if(permissionList.size() > 0){
            return permissionList;
        }else
            throw new EntityNotFoundException("There are no permissions in the database");
    }
}
