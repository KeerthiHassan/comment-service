package com.maveric.commentservice.controller;

import com.maveric.commentservice.dto.CommentResponse;
import com.maveric.commentservice.dto.Commentdto;
import com.maveric.commentservice.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentResponse>>  getComments(@PathVariable ("postId") String postId){
        return new ResponseEntity<List<CommentResponse>>(commentService.getComments(postId), HttpStatus.OK);
    }
    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentResponse> createComment(@PathVariable ("postId") String postId,@Valid  @RequestBody Commentdto commentdto){
        return new ResponseEntity<CommentResponse>(commentService.createComment(postId,commentdto),HttpStatus.CREATED);
    }
    @GetMapping("/{postId}/comments/count")
    public ResponseEntity<Integer>  getCommentsCount(@PathVariable ("postId") String postId){
        return new ResponseEntity<Integer>(commentService.getCommentsCount(postId), HttpStatus.OK);
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentResponse>  getCommentDetails(@PathVariable ("postId") String postId,@PathVariable("commentId") String commentId){
        return new ResponseEntity<CommentResponse>(commentService.getCommentDetails(postId,commentId), HttpStatus.OK);
    }
    @PutMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable ("postId") String postId,@PathVariable("commentId") String commentId,@Valid @RequestBody Commentdto updateComments){
        return new ResponseEntity<CommentResponse>(commentService.updateComment(postId,commentId,updateComments),HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<String> deletePost(@PathVariable ("postId") String postId,@PathVariable("commentId") String commentId){
        return new ResponseEntity<String>(commentService.deleteComment(postId,commentId),HttpStatus.OK);
    }
}
