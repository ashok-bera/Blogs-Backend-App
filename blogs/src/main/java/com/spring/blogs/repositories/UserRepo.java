package com.spring.blogs.repositories;

import com.spring.blogs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//Here this Jparepo takes entity on which queries need to be run and The primary key
public interface UserRepo extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String email);
}
