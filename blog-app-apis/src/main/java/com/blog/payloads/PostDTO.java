package com.blog.payloads;

import com.blog.entities.Categorie;
import com.blog.entities.Comment;
import com.blog.entities.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Integer id;

    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    private String imageName;
    private Date addedDate;
    private User user;
    private Categorie categorie;
    private Set<CommentDTO> comments = new HashSet<>();
}
