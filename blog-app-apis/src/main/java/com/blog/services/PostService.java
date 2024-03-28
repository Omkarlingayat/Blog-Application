package com.blog.services;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDTO;
import com.blog.response.PostResponse;

import java.util.List;

public interface PostService {
    // create post
    ApiResponse createPost(PostDTO postDTO, Integer userId, Integer categoryId);

    // update post
    PostDTO updatePost(PostDTO postDTO, Integer postId);

    // delete post
    ApiResponse deletePost(Integer postId);

    // get post by its id
    PostDTO getPostById(Integer postId);

    // get all posts
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    // get all posts by category
    List<PostDTO> getAllPostBYCategory(Integer categorieId);

    // get all posts by user
    List<PostDTO> getAllPostsByUser(Integer userId);

    // search post
    List<PostDTO> searchPosts(String keyword);
}
