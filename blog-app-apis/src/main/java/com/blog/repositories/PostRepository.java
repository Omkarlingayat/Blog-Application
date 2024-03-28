package com.blog.repositories;

import com.blog.entities.Categorie;
import com.blog.entities.Post;
import com.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("SELECT p FROM Post p WHERE p.categorie = :categorie")
    List<Post> findAllByCategorie(Categorie categorie);

    @Query("SELECT p FROM Post p WHERE p.user = :user")
    List<Post> findAllByUser(User user);

    @Query("SELECT p FROM Post p where p.title like :title")
    List<Post> searchByTitle(@Param("title")String title);
}
