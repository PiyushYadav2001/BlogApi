package com.blogApi.Controller;

import com.blogApi.Service.CommentService;
import com.blogApi.playload.ApiResponce;
import com.blogApi.playload.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
     private CommentService commentService;

      @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment, @PathVariable Integer postId){
          CommentDto comment1 = this.commentService.createComment(comment, postId);
          return new ResponseEntity<CommentDto>(comment1, HttpStatus.CREATED);
      }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponce> deleteComment(@PathVariable Integer commentId){
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponce>(new ApiResponce("Comment Deleted SuccesFully", true),HttpStatus.OK );

    }
}
