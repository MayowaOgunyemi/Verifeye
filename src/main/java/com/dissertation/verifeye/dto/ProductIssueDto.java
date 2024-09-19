package com.dissertation.verifeye.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductIssueDto {

    private Long id; // Assuming you use Long as the ID type in the entity
    private Long productId;
    private String issueDescription;
    private String issueStatus; // e.g., "Reported", "Resolved"
    private String reportedBy; // Can be consumer or manufacturer
    private String reportedDate; // Date when the issue was reported
}
