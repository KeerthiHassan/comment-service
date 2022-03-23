package com.maveric.commentservice.repo;

import com.maveric.commentservice.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepo extends MongoRepository<Comment,String> {
Comment findBycommentId(String commentId);
List<Comment> findBypostId(String postId);
}
