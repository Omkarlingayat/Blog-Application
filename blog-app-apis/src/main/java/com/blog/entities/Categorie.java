package com.blog.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categorie")
public class Categorie {
    @Id
    @JoinColumn(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categorieId;

    @JoinColumn(name = "title")
    private String categorieTitle;

    @JoinColumn(name = "description")
    private String categorieDescription;

    // one category contains multiple post
    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();
}
