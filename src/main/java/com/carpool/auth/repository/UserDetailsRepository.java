package com.carpool.auth.repository;

import com.carpool.auth.dto.UserCreateDTO;
import com.carpool.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String name);

    Optional<User> findByUserID(String userID);

}
