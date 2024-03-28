package com.blog.services.impl;

import com.blog.entities.Categorie;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDTO;
import com.blog.repositories.CategorieRepository;
import com.blog.repositories.PostRepository;
import com.blog.repositories.UserRepository;
import com.blog.response.PostResponse;
import com.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    // create post
    @Override
    public ApiResponse createPost(PostDTO postDTO, Integer userId, Integer categoryId) {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","id",userId));
        Categorie categorie = categorieRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category","id",categoryId));
        Post post = postDtoToPost(postDTO);
        post.setImageName("default.jpg");
        post.setAddedDate(new Date());
        post.setCategorie(categorie);
        post.setUser(user);

        postRepository.save(post);
        ApiResponse response = new ApiResponse("Post created successfully",true);

        return response;
    }

    // update post
    @Override
    public PostDTO updatePost(PostDTO postDTO, Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id",postId));
        if(postDTO.getTitle() != null){
            post.setTitle(postDTO.getTitle());
        }
        if(postDTO.getContent() != null){
            post.setContent(postDTO.getContent());
        }
        if(postDTO.getImageName() != null){
            post.setImageName(postDTO.getImageName());
        }
        post.setAddedDate(new Date());

        postRepository.save(post);
        PostDTO updatedPost = postToPostDto(post);
        ApiResponse response = new ApiResponse("Post updated successfully",true);
        return updatedPost;
    }

    // delete post
    @Override
    public ApiResponse deletePost(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id",postId));
        postRepository.delete(post);
        ApiResponse response = new ApiResponse("Post deleted successfully",true);
        return response;
    }

    // get post by its id
    @Override
    public PostDTO getPostById(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id",postId));
        PostDTO postDTO = postToPostDto(post);
        return postDTO;
    }

    // get all posts
    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        Pageable p = PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> pagePost = this.postRepository.findAll(p);
        List<Post> posts = pagePost.getContent();

        List<PostDTO> postDTOList = posts.stream().map(post -> this.postToPostDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();

        postResponse.setContent(postDTOList);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public List<PostDTO> getAllPostBYCategory(Integer categorieId) {
        Categorie categorie = categorieRepository.findById(categorieId).orElseThrow(()-> new ResourceNotFoundException("category","id",categorieId));
        List<Post> posts = postRepository.findAllByCategorie(categorie);
        List<PostDTO> postDTOList = posts.stream().map(post -> this.postToPostDto(post)).collect(Collectors.toList());

        return postDTOList;
    }

    @Override
    public List<PostDTO> getAllPostsByUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        List<Post> posts = postRepository.findAllByUser(user);
        List<PostDTO> postDTOList = posts.stream().map(post -> this.postToPostDto(post)).collect(Collectors.toList());
        return postDTOList;
    }

    @Override
    public List<PostDTO> searchPosts(String keyword) {
        List<Post> posts = postRepository.searchByTitle(keyword);
        List<PostDTO> postDTOList = posts.stream().map(post -> postToPostDto(post)).collect(Collectors.toList());
        return postDTOList;
    }

    // convert post in to postDto
    public PostDTO postToPostDto(Post post){
        PostDTO postDTO = modelMapper.map(post, PostDTO.class);
        return postDTO;
    }

    // convert postDTO into post
    public Post postDtoToPost(PostDTO postDTO){
        Post post = modelMapper.map(postDTO,Post.class);
        return post;
    }
}
