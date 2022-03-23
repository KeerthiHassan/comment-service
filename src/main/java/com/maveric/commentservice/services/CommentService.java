package com.maveric.commentservice.services;

import com.maveric.commentservice.dto.CommentResponse;
import com.maveric.commentservice.dto.UpdateComments;
import com.maveric.commentservice.model.Comment;

import java.util.List;

public interface CommentService {
    List<CommentResponse> getComments(String postId);
    CommentResponse createComment(String postId,Comment comment);
    Integer getCommentsCount(String postId);
    CommentResponse getCommentDetails(String postId,String commentId);
    CommentResponse updateComment(String postId,String commentId, UpdateComments updateComment);
    String deleteComment(String postId,String commentId);
}

