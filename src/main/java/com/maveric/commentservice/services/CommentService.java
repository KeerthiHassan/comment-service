package com.maveric.commentservice.services;

import com.maveric.commentservice.dto.CommentResponse;
import com.maveric.commentservice.dto.UpdateComments;
import com.maveric.commentservice.model.Comment;

import java.util.List;

public interface CommentService {

    CommentResponse createComment(String postId,Comment comment);
    CommentResponse updateComment(String postId,String commentId, UpdateComments updateComment);
}
