package com.stdevcamp.authsystembackend.repository;

import com.stdevcamp.authsystembackend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
