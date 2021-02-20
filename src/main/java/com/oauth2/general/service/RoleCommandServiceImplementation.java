package com.oauth2.general.service;

import com.oauth2.general.dto.RoleCreateDTO;
import com.oauth2.general.exeption.EntityNotFoundException;
import com.oauth2.general.exeption.InternalServerErrorException;
import com.oauth2.general.model.Permission;
import com.oauth2.general.model.Role;
import com.oauth2.general.repository.UserPermissionRepository;
import com.oauth2.general.repository.UserRoleRepository;
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
    public RoleCreateDTO updateRole(Integer id, RoleCreateDTO roleCreateDTO) {

        Optional<Role> role = userRoleRepository.findById(id);

        if(role.isPresent()) {

            Role currentRole = role.get();

            currentRole.setRoleName(roleCreateDTO.getRoleName());

            if(roleCreateDTO.getPermissions().length > 0)
                currentRole.setPermissions(this.setPermissionList(roleCreateDTO.getPermissions()));

            Role updatedRole = userRoleRepository.save(currentRole);

            return new RoleCreateDTO(updatedRole.getId(),updatedRole.getRoleName(),updatedRole.getPermissions());
        }
        else
            throw new EntityNotFoundException("You can't update this role,role with id "+id.toString()+" is not found in the database");
    }

    @Override
    public Role createRole(RoleCreateDTO roleCreateDTO) {

        try{
            Role newRole = new Role();

            newRole.setRoleName(roleCreateDTO.getRoleName());

            System.out.println(roleCreateDTO.getRoleName());

            newRole.setPermissions(this.setPermissionList(roleCreateDTO.getPermissions()));

            return userRoleRepository.save(newRole);
        }
        catch(Exception ex){

            throw new InternalServerErrorException("Role creation failed due to internal server error");
        }

    }

    @Override
    public String deleteRole(Integer id){

        Optional<Role> role = userRoleRepository.findById(id);

        if(role.isPresent()){
            try{
                userRoleRepository.delete(role.get());
                return "Role has been successfully deleted";
            }catch(Exception ex){

                throw new InternalServerErrorException("Role deletion failed due to internal server error");
            }
        }else
            throw new EntityNotFoundException("You can't delete this role,role with id "+id.toString()+" is not found in the database");
    }

    @Override
    public Role getRole(Integer id) {

        Optional<Role> role = userRoleRepository.findById(id);

        if(role.isPresent())
            return role.get();
        else
            throw new EntityNotFoundException("Role with id "+id.toString()+" is not found in the database");
    }

    @Override
    public List<Role> getRoles() {

        List<Role> rolelist = userRoleRepository.findAll();

        if(rolelist.size() > 0){
            return rolelist;
        }else
            throw new EntityNotFoundException("There are no roles in the database");
    }
}
