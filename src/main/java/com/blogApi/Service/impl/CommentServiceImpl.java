package com.blogApi.Service.impl;

import com.blogApi.Exception.ResourceNotFoundException;
import com.blogApi.Repo.CommentRepo;
import com.blogApi.Repo.PostRepo;
import com.blogApi.Service.CommentService;
import com.blogApi.entity.Comment;
import com.blogApi.entity.Post;
import com.blogApi.playload.CommentDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
     private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "Post id", postId));
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment save = this.commentRepo.save(comment);
        return this.modelMapper.map(save,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
           Comment com=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment ","CommentId",commentId));
           this.commentRepo.delete(com);
    }
}
