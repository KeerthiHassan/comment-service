package com.maveric.commentservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    private String commentId;
    private String commentedBy;
    private String comment;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String postId;

}
