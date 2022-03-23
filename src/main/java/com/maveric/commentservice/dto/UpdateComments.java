package com.maveric.commentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateComments {
    private String comment;
    private LocalDate updatedAt;
    private String commentedBy;
}
