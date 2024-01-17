package com.myblog8.repository;

import com.myblog8.entity.Comment;
import com.myblog8.payload.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(long postId);//custom method to find particular column in database in this layer.
}