package com.myblog8.service.impl;

import com.myblog8.entity.Comment;
import com.myblog8.entity.Post;
import com.myblog8.exception.ResourceNotFound;
import com.myblog8.payload.CommentDto;
import com.myblog8.repository.CommentRepository;
import com.myblog8.repository.PostRepository;
import com.myblog8.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PostRepository postRepository;//?

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
       Comment comment =mapToEntity(commentDto);
        //here we are finding whether post exists or not with that id,if found retrieve the row orelse throw exception.
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("post not found with id :" + postId)
        );
        comment.setPost(post);// we are setting posts with comments so this method.
        Comment savedcomment = commentRepository.save(comment);
        CommentDto dto = mapToDto(savedcomment);

        return dto;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("post not found with id :" + postId));
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentDto> commentDtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public CommentDto getcommentsBycommentId(long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFound("comment not found with id :" + commentId));
        return mapToDto(comment);

    }

    @Override
    public List<CommentDto> getAllcomments() {
        List<Comment> all = commentRepository.findAll();
        List<CommentDto> commentDtos = all.stream().map(x -> mapToDto(x)).collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public void deletebyCommentid(long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFound("comment not found with id :" + commentId));
        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentDto updatecomentbycommentId(long commentId, CommentDto commentDto) {
        Comment comment=commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFound("comment not foud with id:"+ commentId)
        );
        Comment comment1 = mapToEntity(commentDto);
        comment.setBody(comment1.getBody());
        comment.setName(comment1.getName());
        comment.setEmail(comment1.getEmail());
        Comment comment2 = commentRepository.save(comment);
        return mapToDto(comment2);
    }

    private CommentDto mapToDto(Comment savedcomment) {
        CommentDto dto = modelMapper.map(savedcomment, CommentDto.class);
        return dto;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }

}
