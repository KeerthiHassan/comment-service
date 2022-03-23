package com.maveric.commentservice.controller;

import com.maveric.commentservice.dto.CommentResponse;
import com.maveric.commentservice.dto.UpdateComments;
import com.maveric.commentservice.model.Comment;
import com.maveric.commentservice.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CommentController {
    
    @Autowired
    CommentService commentService;


    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponse> createComment(@PathVariable ("postId") String postId,@RequestBody Comment comment){
        return new ResponseEntity<CommentResponse>(commentService.createComment(postId,comment),HttpStatus.CREATED);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable ("postId") String postId,@PathVariable("commentId") String commentId,@RequestBody UpdateComments updateComments){
        return new ResponseEntity<CommentResponse>(commentService.updateComment(postId,commentId,updateComments),HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deletePost(@PathVariable ("postId") String postId,@PathVariable("commentId") String commentId){
        return new ResponseEntity<String>(commentService.deleteComment(postId,commentId),HttpStatus.OK);
    }



}
