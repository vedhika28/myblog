package com.myblog8.service.impl;

import
        com.myblog8.entity.Post;
import com.myblog8.exception.ResourceNotFound;
import com.myblog8.payload.PostDto;
import com.myblog8.payload.PostResponse;
import com.myblog8.repository.PostRepository;
import com.myblog8.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    private PostRepository postRepository;
    private ModelMapper modelMapper;//thisis not a builtin library ioc doesnot know how to create object/bean




    @Override
    public PostDto savePost(PostDto postDto) {//we are giving arguments in service also serviceimpl?
//       Post post=new Post();//converting dto to entity
//       post.setId(postDto.getId());
//       post.setTitle(postDto.getTitle());
//       post.setDescription(postDto.getDescription());
//       post.setContent(postDto.getContent());
        Post post = mapToentity(postDto);
        Post savedPost = postRepository.save(post);
        PostDto dto = mapToDto(savedPost);
        return dto;

//        PostDto dto=new PostDto();//converting entity to dto
//        dto.setId(savedPost.getId());
//        dto.setTitle(savedPost.getTitle());
//        dto.setDescription(savedPost.getDescription());
//        dto.setContent(savedPost.getContent());
//        return dto;//this flow
    }

    @Override
    public void deletePost(long id) {

        postRepository.deleteById(id);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        Post post= postRepository.findById(id).orElseThrow(//findbyid is return type is entity
                ()->new ResourceNotFound("post not found with id:"+id)
        );
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post updatePost = postRepository.save(post);
       PostDto dto= mapToDto(updatePost);
       return dto;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFound("post not found with id"+id)
        );
        PostDto dto = mapToDto(post);
        return dto;

    }

    @Override
    public PostResponse getPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();//ternary operator (if else)

        Pageable pageable =PageRequest.of(pageNo, pageSize, sort);//here we are giving (Sort.By) because it takes sort objects with strings
        Page<Post> pagePosts = postRepository.findAll(pageable);

        List<Post> posts = pagePosts.getContent();//here we have to convert it to list so we apply getcontent
        List<PostDto> postDto = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());//bitconfusioin
        PostResponse postResponse=new PostResponse();
        postResponse.setPostDto(postDto);
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setPageNo(pagePosts.getNumber());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setLast(pagePosts.isLast());//for boolean we write this.




        return postResponse;


    }

    public PostDto mapToDto(Post post){
        PostDto dto = modelMapper.map(post, PostDto.class); //this one line converts the whole entity object to dto object

        // PostDto dto=new PostDto();//converting entity to dto
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
        return dto;

    }
    Post mapToentity(PostDto postDto){
        Post post = modelMapper.map(postDto, Post.class);
//        Post post=new Post();//converting dto to entity
//        post.setId(postDto.getId());
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }
}

