package com.myblog8.controller;

import com.myblog8.payload.PostDto;
import com.myblog8.payload.PostResponse;
import com.myblog8.service.PostService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

//hi i am navya
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

// to create a bean/object either you can give @Autowired or you can do it by constructor based injection.

    private  PostService postService;



    public PostController(PostService postService) {

        this.postService = postService;
    }


    //http://localhost:8080/api/post ,note:if you put <?> return type can be anything,if any errors in dto binding result takes the error and gives the message in postman

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/post")
    public ResponseEntity<?> savePost(@Valid @RequestBody PostDto postDto, BindingResult result){//this method confusion
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        PostDto dto = postService.savePost(postDto);//flow of this,is this dto same as return dto?
        return new ResponseEntity<>(dto, HttpStatus.CREATED);//201//bit confusion
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")//http://localhost:8080/api/post/1   //delete
    public ResponseEntity<String> deletePost(@PathVariable("id") long id) {
        postService.deletePost(id);//arguments c
        return new ResponseEntity<>("post is deleted", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")//update ,response entity to talk with postman andalsocan send list in postman.

    public ResponseEntity<PostDto> updatePost(@PathVariable("id") long id, @RequestBody PostDto postDto) {
        PostDto dto = postService.updatePost(id, postDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/{id}")//retrieve with id,its a specif id we want to retrieve thus there is no any return type of list
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id) {
        PostDto dto = postService.getPostById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping//http://localhost:8080/api/post?pageNo=0&pageSize=5&sortBy=title&sortDir=asc
    public PostResponse getPosts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PostResponse postResponse = postService.getPosts(pageNo, pageSize, sortBy, sortDir);
        return postResponse;
    }
}
