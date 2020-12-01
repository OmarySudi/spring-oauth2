package com.carpool.auth.repository;

import com.carpool.auth.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPermissionRepository extends JpaRepository<Permission,Integer> {

    Permission findByPermissionNameContaining(String name);
}
