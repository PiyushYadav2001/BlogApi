package com.blogApi.Service;

import com.blogApi.playload.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto,Integer postId);

    void deleteComment(Integer commentId);
}
