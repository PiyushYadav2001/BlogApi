package com.blogApi.Service;

import com.blogApi.entity.Post;
import com.blogApi.playload.PostDto;
import com.blogApi.playload.PostResponce;

import java.util.List;

public interface PostService {

     PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);



     PostDto updatePost(PostDto postDto , Integer postId);

     void deletePost(Integer postId);

     PostResponce getAllPost (Integer pageSize,
                              Integer pageNumber,String sortBy,String sortDi);

     PostDto getPostById(Integer postId);

     List<PostDto> getPostByCategory(Integer catagoryId);

     List<PostDto>  getPostByUser(Integer userId);

     List<PostDto> searchPosts(String keyword);

     
}
