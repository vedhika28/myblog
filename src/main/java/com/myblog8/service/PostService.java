package com.myblog8.service;

import com.myblog8.payload.PostDto;
import com.myblog8.payload.PostResponse;



public interface PostService {
    public PostDto savePost(PostDto postDto);

   public void deletePost(long id);

    public PostDto updatePost(long id, PostDto postDto);


   PostDto getPostById(long id);

    PostResponse getPosts(int pageNo, int pageSize, String sortBy, String sortDir);
}
