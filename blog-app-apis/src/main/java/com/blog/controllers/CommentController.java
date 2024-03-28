package com.blog.controllers;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CommentDTO;
import com.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    // create comment
    @PostMapping("/user/{userId}/post/{postId}/create")
    public ResponseEntity<ApiResponse> createComment(@RequestBody CommentDTO commentDTO, @PathVariable Integer postId, @PathVariable Integer userId){
        ApiResponse apiResponse = commentService.createComment(commentDTO,postId,userId);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    // delete comment
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
        commentService.deleteComment(commentId);
        ApiResponse apiResponse = new ApiResponse("Comment deleted successfully",true);
        return  new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    // get comment
    @GetMapping("/get/{commentId}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable Integer commentId){
        CommentDTO commentDTO = commentService.getComment(commentId);
        return new ResponseEntity<>(commentDTO,HttpStatus.OK);
    }
}
