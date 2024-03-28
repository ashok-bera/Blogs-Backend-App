package com.spring.blogs.repositories;

import com.spring.blogs.entities.Category;
import com.spring.blogs.entities.Post;
import com.spring.blogs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {

    // all custom methods
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
    List<Post> findByTitleContaining(String title);
}
