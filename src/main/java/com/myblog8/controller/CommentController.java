package com.myblog8.controller;

import com.myblog8.payload.CommentDto;
import com.myblog8.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class CommentController {
@Autowired
    private CommentService commentService;

//http://localhost:8080/api/posts/1/comments
    @PostMapping("/posts/{postId}/comments")//with postid save the comment in db.
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId, @RequestBody CommentDto commentDto){

        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }
    @GetMapping("/posts/{postId}/comments")//how to fetch comments by postid.
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") Long postId){
        return commentService.getCommentsByPostId(postId);
    }
    @GetMapping ("/{commentId}/comments") //how to fetch comment by commentid.

    public ResponseEntity<CommentDto> getcommentsBycommentId(@PathVariable(value="commentId") long commentId ){
       return new ResponseEntity<>(commentService.getcommentsBycommentId(commentId),HttpStatus.OK);
    }
    @GetMapping("/getAllcomments")       //how to fetch all the comments.
    public List<CommentDto> getAllcomments(){
          return commentService.getAllcomments();
    }
    @DeleteMapping("/{commentId}/deletecomment")
    public ResponseEntity<String> deletebyCommentid(@PathVariable(value = "commentId") long commentId){
            commentService.deletebyCommentid(commentId);
            return new ResponseEntity<>("comment is deleted",HttpStatus.OK);
    }
    @PutMapping("/{commentId}/comments/update")
    public ResponseEntity<CommentDto> updatecomentbycommentId(@PathVariable(value = "commentId") long commentId,
                                                              @RequestBody CommentDto commentDto){
        CommentDto commentDto1 = commentService.updatecomentbycommentId(commentId, commentDto);
        return new ResponseEntity<>(commentDto1,HttpStatus.OK);

    }
}