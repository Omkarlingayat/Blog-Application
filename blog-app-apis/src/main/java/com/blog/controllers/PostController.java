package com.blog.controllers;

import com.blog.config.AppConsts;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDTO;
import com.blog.response.PostResponse;
import com.blog.services.FileService;
import com.blog.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    // create post
    @PostMapping("/user/{userId}/category/{categoryId}/create")
    public ResponseEntity<ApiResponse> createPost(@Valid @RequestBody PostDTO postDTO, @PathVariable Integer userId, @PathVariable Integer categoryId){
        ApiResponse response = postService.createPost(postDTO,userId,categoryId);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    // update post
    @PutMapping("/update/{id}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable Integer id){
        PostDTO updatePost = postService.updatePost(postDTO,id);
        return ResponseEntity.ok(updatePost);
    }

    // delete post
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer id){
        ApiResponse response = postService.deletePost(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Integer id) {
        System.out.println("--------- start controller");

        try {
            PostDTO postDTO = postService.getPostById(id);
            System.out.println("postDTO = " + postDTO);
            return ResponseEntity.ok(postDTO);
        } catch (ResourceNotFoundException ex) {
            // Handle ResourceNotFoundException and return a proper error response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            // Handle other exceptions if needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }


    // get all posts
    @GetMapping("/get")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = AppConsts.PAGE_NUMBER, required = false)Integer pageNumber,
                                                     @RequestParam(value = "pageSize", defaultValue = AppConsts.PAGE_SIZE, required = false)Integer pageSize,
                                                    @RequestParam(value = "sortBy", defaultValue = AppConsts.SORT_BY, required = false)String sortBy,
                                                    @RequestParam(value = "sortDir", defaultValue = AppConsts.SORT_DIR, required = false)String sortDir){
        PostResponse postResponse = postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
        return ResponseEntity.ok(postResponse);
    }

    // get all posts by category id
    @GetMapping("/get/category/{id}")
    public ResponseEntity<List<PostDTO>> getAllPostsByCategoryId(@PathVariable Integer id){
        List<PostDTO> postDTOList = postService.getAllPostBYCategory(id);
        return new ResponseEntity<>(postDTOList,HttpStatus.OK);
    }

    // get all posts by user id
    @GetMapping("/get/user/{id}")
    public ResponseEntity<List<PostDTO>> getAllPostsByUserId(@PathVariable Integer id){
        List<PostDTO> postDTOList = postService.getAllPostsByUser(id);
        return new ResponseEntity<>(postDTOList,HttpStatus.OK);
    }

    // get posts by key or search
    @GetMapping("/get/search/{key}")
    public ResponseEntity<List<PostDTO>> getPostBySearchingTitle(@PathVariable String key){
        List<PostDTO> postDTOList = postService.searchPosts("%"+key+"%");
        return ResponseEntity.ok(postDTOList);
    }

    // post image upload
    @PostMapping("/image/upload/{postId}")
    public ResponseEntity<PostDTO> uploadPostImage(@RequestParam("image")MultipartFile image, @PathVariable Integer postId) throws IOException {
        PostDTO postDTO = postService.getPostById(postId);
        String fileName = this.fileService.uploadImage(path,image);
        postDTO.setImageName(fileName);
        PostDTO updatePost = postService.updatePost(postDTO, postId);
        return new ResponseEntity<PostDTO>(updatePost,HttpStatus.OK);
    }

    // display image or method to serve files
    @GetMapping(value = "image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable String imageName, HttpServletResponse response)throws IOException{
        InputStream resource = fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
