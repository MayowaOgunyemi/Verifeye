package com.dissertation.verifeye.repository;

import com.dissertation.verifeye.entity.ProductIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductIssueRepository extends JpaRepository<ProductIssue, Long> {

    List<ProductIssue> findByProductId(Long productId);
}
