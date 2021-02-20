package com.oauth2.general.repository;

import com.oauth2.general.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<Role,Integer> {

    Role findByRoleNameContaining(String roleName);
}
