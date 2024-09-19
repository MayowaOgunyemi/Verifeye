package com.dissertation.verifeye.entity;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_issues")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String issueDescription;

    @Column(nullable = false)
    private LocalDateTime reportedAt;

    @Column
    private String status; // e.g., Open, In Progress, Resolved

}
