package com.carpool.auth.repository;

import com.carpool.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<Role,Integer> {

    Role findByRoleNameContaining(String roleName);
}
