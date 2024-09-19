package com.dissertation.verifeye.controller;

import com.dissertation.verifeye.dto.ProductIssueDto;
import com.dissertation.verifeye.service.ProductIssueService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-issues")
@AllArgsConstructor
public class ProductIssueController {

    private final ProductIssueService productIssueService;

    // Report Product Issue
    @PostMapping("/report")
    public ResponseEntity<ProductIssueDto> reportProductIssue(@RequestBody ProductIssueDto productIssueDto) {
        ProductIssueDto reportedIssue = productIssueService.reportProductIssue(productIssueDto);
        return ResponseEntity.ok(reportedIssue);
    }

    // Get Product Issues
    @GetMapping("/{productId}")
    public ResponseEntity<List<ProductIssueDto>> getProductIssues(@PathVariable Long productId) {
        List<ProductIssueDto> issues = productIssueService.getProductIssues(productId);
        return ResponseEntity.ok(issues);
    }
}
