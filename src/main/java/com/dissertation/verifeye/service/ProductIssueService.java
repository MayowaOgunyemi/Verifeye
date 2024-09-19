package com.dissertation.verifeye.service;

import com.dissertation.verifeye.dto.ProductIssueDto;
import com.dissertation.verifeye.entity.ProductIssue;
import com.dissertation.verifeye.repository.ProductIssueRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductIssueService {

    private final ProductIssueRepository productIssueRepository;

    // Report a new product issue
    public ProductIssueDto reportProductIssue(ProductIssueDto productIssueDto) {
        ProductIssue productIssue = mapDtoToEntity(productIssueDto);
        ProductIssue savedProductIssue = productIssueRepository.save(productIssue);
        return mapEntityToDto(savedProductIssue);
    }

    // Get all issues for a product
    public List<ProductIssueDto> getProductIssues(Long productId) {
        List<ProductIssue> issues = productIssueRepository.findByProductId(productId);
        return issues.stream().map(this::mapEntityToDto).collect(Collectors.toList());
    }

    // Map DTO to entity
    private ProductIssue mapDtoToEntity(ProductIssueDto productIssueDto) {
        ProductIssue productIssue = new ProductIssue();
        productIssue.setId(productIssueDto.getId());
        productIssue.setIssueDescription(productIssueDto.getIssueDescription());
        return productIssue;
    }

    // Map entity to DTO
    private ProductIssueDto mapEntityToDto(ProductIssue productIssue) {
        ProductIssueDto productIssueDto = new ProductIssueDto();
        productIssueDto.setId(productIssue.getId());
        productIssueDto.setIssueDescription(productIssue.getIssueDescription());
        return productIssueDto;
    }
}
