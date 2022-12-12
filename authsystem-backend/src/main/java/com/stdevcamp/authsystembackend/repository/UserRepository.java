package com.stdevcamp.authsystembackend.repository;

import com.stdevcamp.authsystembackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
