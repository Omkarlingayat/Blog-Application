package com.blog.services.impl;

import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.CommentDTO;
import com.blog.repositories.CommentRepository;
import com.blog.repositories.PostRepository;
import com.blog.repositories.UserRepository;
import com.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    // create comment
    @Override
    public ApiResponse createComment(CommentDTO commentDTO, Integer postId, Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","id",userId));
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id",postId));
        Comment comment = modelMapper.map(commentDTO,Comment.class);
        comment.setPost(post);
        comment.setUser(user);
        commentRepository.save(comment);
        return new ApiResponse("Comment created successfully",true);
    }

    // delete comment
    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment","id",commentId));
        commentRepository.delete(comment);
    }

    // get comment
    @Override
    public CommentDTO getComment(Integer commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment","id",commentId));
        CommentDTO commentDTO = modelMapper.map(comment,CommentDTO.class);
        return commentDTO;
    }
}
