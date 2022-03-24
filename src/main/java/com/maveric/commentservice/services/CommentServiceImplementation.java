package com.maveric.commentservice.services;

import com.maveric.commentservice.dto.CommentResponse;
import com.maveric.commentservice.dto.Commentdto;
import com.maveric.commentservice.dto.UserResponse;
import com.maveric.commentservice.exception.CommentNotFound;
import com.maveric.commentservice.feign.LikeFeign;
import com.maveric.commentservice.feign.UserFeign;
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
    LikeFeign feign;


    @Autowired
    UserFeign userFeign;

    private String commentId;
    private UserResponse commentedBy;
    private String comment;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private Integer likesCount;

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
    public List<CommentResponse> getComments(String postId) {
        List<Comment> commentList=commentRepo.findBypostId(postId);
        List<CommentResponse> commentResponses=new ArrayList<>();
        for(Comment comment:commentList)
        {
            CommentResponse commentResponse=new CommentResponse();
            commentResponse.setCommentId(comment.getCommentId());
            commentResponse.setCommentedBy(userFeign.getUsersById(comment.getCommentedBy()).getBody());
            commentResponse.setComment(comment.getComment());
            commentResponse.setCreatedAt(comment.getCreatedAt());
            commentResponse.setUpdatedAt(comment.getUpdatedAt());
            Integer count =feign.getLikesCount(comment.getCommentId()).getBody();
            commentResponse.setLikesCount(count);
             commentResponses.add(commentResponse);
        }
        return commentResponses;
    }

    @Override
    public CommentResponse createComment(String postId,Commentdto commentdto) {
        Comment comment=new Comment();
        CommentResponse commentResponse=new CommentResponse();
        comment.setPostId(postId);
        comment.setCreatedAt(LocalDate.now());
        comment.setUpdatedAt(LocalDate.now());
        comment.setComment(commentdto.getComment());
        UserResponse userResponse=(userFeign.getUsersById(commentdto.getCommentedBy()).getBody());
        comment.setCommentedBy(commentdto.getCommentedBy());
        Comment comments=commentRepo.save(comment);
        return new CommentResponse(comments.getCommentId(),userFeign.getUsersById(comment.getCommentedBy()).getBody(),comments.getComment(),
                comments.getCreatedAt(),comments.getUpdatedAt(),feign.getLikesCount(comments.getCommentId()).getBody());
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
        if(comment==null)
            throw new CommentNotFound("Comment not found with Id "+commentId);
        CommentResponse commentResponse=new CommentResponse();
        commentResponse.setCommentId(comment.getCommentId());
        commentResponse.setComment(comment.getComment());
        commentResponse.setCommentedBy(userFeign.getUsersById(comment.getCommentedBy()).getBody());
        commentResponse.setCreatedAt(comment.getCreatedAt());
        commentResponse.setUpdatedAt(comment.getUpdatedAt());
        commentResponse.setLikesCount(count);
        return commentResponse;
    }

    @Override

    public CommentResponse updateComment(String postId,String commentId, Commentdto updateComments) {

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
        commentResponse.setCommentedBy(userFeign.getUsersById(comment.getCommentedBy()).getBody());
        commentResponse.setCreatedAt(comments.getCreatedAt());
        commentResponse.setUpdatedAt(comments.getUpdatedAt());
        commentResponse.setLikesCount(count);
        return commentResponse;
    }

    @Override
    public String deleteComment(String postId,String commentId) {

        Comment comment=commentRepo.findBycommentId(commentId);
        if(comment==null)
        throw new CommentNotFound("Comment Doesn't Exists");
         commentRepo.deleteById(commentId);
        return "Comment deleted successfully";
    }
}
