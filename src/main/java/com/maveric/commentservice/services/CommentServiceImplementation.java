package com.maveric.commentservice.services;

import com.maveric.commentservice.dto.CommentResponse;
import com.maveric.commentservice.dto.UpdateComments;
import com.maveric.commentservice.feign.Feign;
import com.maveric.commentservice.model.Comment;
import com.maveric.commentservice.repo.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class CommentServiceImplementation implements CommentService{

    @Autowired
    CommentRepo commentRepo;

    @Autowired
    Feign feign;


    @Override
    public List<CommentResponse> getComments(String postId) {
        List<Comment> commentList=commentRepo.findBypostId(postId);
        List<CommentResponse> commentResponse=new ArrayList<>();
        for(Comment comment:commentList){
            Integer count =feign.getLikesCount(comment.getCommentId()).getBody();
            CommentResponse commentResponsetemp =new CommentResponse();
            commentResponsetemp.setCommentId(comment.getCommentId());
            commentResponsetemp.setComment(comment.getComment());
            commentResponsetemp.setCommentedBy(comment.getCommentedBy());
            commentResponsetemp.setCreatedAt(comment.getCreatedAt());
            commentResponsetemp.setUpdatedAt(comment.getUpdatedAt());
            commentResponsetemp.setLikesCount(count);
            commentResponse.add(commentResponsetemp);
        }
        return commentResponse;
    }

    @Override
    public CommentResponse createComment(String postId,Comment comment) {

        CommentResponse commentResponse=new CommentResponse();
        comment.setPostId(postId);
        comment.setCreatedAt(LocalDate.now());
        comment.setUpdatedAt(LocalDate.now());

        Comment comments= commentRepo.save(comment);
        commentResponse.setCommentId(comments.getCommentId());
        commentResponse.setComment(comments.getComment());
        commentResponse.setCommentedBy(comments.getCommentedBy());
        commentResponse.setCreatedAt(comments.getCreatedAt());
        commentResponse.setUpdatedAt(comments.getUpdatedAt());
        commentResponse.setLikesCount(0);
        return commentResponse;
    }

    @Override
    public Integer getCommentsCount(String postId) {
        Integer count=commentRepo.findBypostId(postId).size();
        return count;
    }

    @Override
    public CommentResponse getCommentDetails(String postId,String commentId) {
        Integer count =feign.getLikesCount(commentId).getBody();
        Comment comment=commentRepo.findBycommentId(commentId);
        CommentResponse commentResponse=new CommentResponse();
        commentResponse.setCommentId(comment.getCommentId());
        commentResponse.setComment(comment.getComment());
        commentResponse.setCommentedBy(comment.getCommentedBy());
        commentResponse.setCreatedAt(comment.getCreatedAt());
        commentResponse.setUpdatedAt(comment.getUpdatedAt());
        commentResponse.setLikesCount(count);
        return commentResponse;
    }


    @Override
    public CommentResponse updateComment(String postId,String commentId, UpdateComments updateComments) {
        Comment comment=commentRepo.findBycommentId(commentId);
        comment.setComment(updateComments.getComment());
        comment.setCommentedBy(updateComments.getCommentedBy());
        comment.setUpdatedAt(LocalDate.now());
        Comment comments=commentRepo.save(comment);

        Integer count =feign.getLikesCount(commentId).getBody();

        CommentResponse commentResponse=new CommentResponse();
        commentResponse.setCommentId(comments.getCommentId());
        commentResponse.setComment(comments.getComment());
        commentResponse.setCommentedBy(comments.getCommentedBy());
        commentResponse.setCreatedAt(comments.getCreatedAt());
        commentResponse.setUpdatedAt(comments.getUpdatedAt());
        commentResponse.setLikesCount(count);
        return commentResponse;
    }
    @Override
    public String deleteComment(String postId,String commentId) {
        commentRepo.deleteById(commentId);
        return "Comment deleted successfully";
    }


}
