package com.blogApi.Service.impl;

import com.blogApi.Exception.ResourceNotFoundException;
import com.blogApi.Repo.CategoryRepo;
import com.blogApi.Repo.PostRepo;
import com.blogApi.Repo.UserRepo;
import com.blogApi.Service.PostService;
import com.blogApi.entity.Category;
import com.blogApi.entity.Post;
import com.blogApi.entity.User;
import com.blogApi.playload.PostDto;
import com.blogApi.playload.PostResponce;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "User ID", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
        Post map = this.modelMapper.map(postDto, Post.class);
        map.setImageName("default.pnj");
        map.setAddedDate(String.valueOf(new Date()));
        map.setUser(user);
        map.setCategory(category);
        Post save = this.postRepo.save(map);
        return this.modelMapper.map(save,PostDto.class);
    }


    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post save = this.postRepo.save(post);
        return this.modelMapper.map(save,PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
        this.postRepo.delete(post);
    }

    @Override
    public PostResponce getAllPost(Integer pageNumber,Integer pageSize
                                ,String sortBy ,String sortDi) {
        Sort sort=null;
        if(sortDi.equalsIgnoreCase("asc")){
            sort = Sort.by(sortBy).ascending();
        }else {
            sort = Sort.by(sortBy).descending();
        }

        PageRequest of = PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> alls = this.postRepo.findAll(of);
        List<Post> all = alls.getContent();

        List<PostDto> collect = all.stream().map(e -> this.modelMapper.map(e, PostDto.class)).collect(Collectors.toList());
          PostResponce postResponce=new PostResponce();
          postResponce.setContent(collect);
          postResponce.setPageNumber(alls.getNumber());
          postResponce.setPageSize(alls.getSize());
          postResponce.setTotalElement(alls.getTotalElements());
          postResponce.setTotalPages(alls.getTotalPages());
          postResponce.setLastPage(alls.isLast());
        return postResponce;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "Post Id", postId));

        return  this.modelMapper.map(post,PostDto.class);
    }

    @Override
    public List<PostDto> getPostByCategory(Integer catagoryId) {
        Category category = this.categoryRepo.findById(catagoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", catagoryId));
        List<Post> postsa = this.postRepo.findByCategory(category);
        List<PostDto> collect = postsa.stream().map((posts) -> this.modelMapper.map(posts, PostDto.class)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","User ID",userId));
        List<Post> byUser = this.postRepo.findByUser(user);
        List<PostDto> collect = byUser.stream().map(e -> this.modelMapper.map(e, PostDto.class)).collect(Collectors.toList());

        return collect;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> byTitleContaining = this.postRepo.findByTitleContaining(keyword);
        List<PostDto> collect = byTitleContaining.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return collect;
    }
}
