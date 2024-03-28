package com.blog.services;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CommentDTO;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    // create comment
    ApiResponse createComment(CommentDTO commentDTO, Integer postId, Integer userId);

    // delete comment
    void deleteComment(Integer commentId);

    // get comment
    CommentDTO getComment(Integer commentId);
}
