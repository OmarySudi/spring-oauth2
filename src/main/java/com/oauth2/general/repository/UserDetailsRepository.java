package com.oauth2.general.repository;

import com.oauth2.general.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String name);

    Optional<User> findByUserID(String userID);

}
