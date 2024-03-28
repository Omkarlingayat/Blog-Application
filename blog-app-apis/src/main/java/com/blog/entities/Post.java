package com.blog.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String title;
    private String content;
    private Date addedDate;
    private String imageName;

    // many posts have one category
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    // many posts are posted by same or user
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    // one post have multiple comments
    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();
}
