package com.myblog8.service;

import com.myblog8.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto getcommentsBycommentId(long commentId);

    List<CommentDto> getAllcomments();

    void deletebyCommentid(long commentId);

    CommentDto updatecomentbycommentId(long commentId, CommentDto commentDto);
}