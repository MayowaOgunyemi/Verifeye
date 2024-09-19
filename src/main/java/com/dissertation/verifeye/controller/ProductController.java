package com.dissertation.verifeye.controller;

import com.dissertation.verifeye.dto.ProductDto;
import com.dissertation.verifeye.service.ProductService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    // Register Product
    @PostMapping("/register")
    public ResponseEntity<ProductDto> registerProduct(@RequestBody ProductDto productDto) {
        ProductDto registeredProduct = productService.registerProduct(productDto);
        return ResponseEntity.ok(registeredProduct);
    }

    // Generate QR Code
    @PostMapping("/{productId}/generate-qrcode")
    public ResponseEntity<String> generateQRCode(@PathVariable Long productId) {
        String qrCode = productService.generateQRCode(productId);
        return ResponseEntity.ok(qrCode);
    }

    // Update Product Information
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId, @RequestBody ProductDto productDto) {
        ProductDto updatedProduct = productService.updateProduct(productId, productDto);
        return ResponseEntity.ok(updatedProduct);
    }

    // Deactivate Product
    @PutMapping("/{productId}/deactivate")
    public ResponseEntity<String> deactivateProduct(@PathVariable Long productId) {
        String result = productService.deactivateProduct(productId);
        return ResponseEntity.ok(result);
    }

    // View Product History
    @GetMapping("/{productId}/history")
    public ResponseEntity<List<ProductDto>> viewProductHistory(@PathVariable Long productId) {
        List<ProductDto> history = productService.viewProductHistory(productId);
        return ResponseEntity.ok(history);
    }

    // Verify Product
    @PostMapping("/verify")
    public ResponseEntity<String> verifyProduct(@RequestBody ProductDto verificationRequest) {
        String verificationResult = productService.verifyProduct(verificationRequest);
        return ResponseEntity.ok(verificationResult);
    }

    // Verify Product by QR Code
    @GetMapping("/qrcode/{qrCodeId}/verify")
    public ResponseEntity<String> verifyProductByQRCode(@PathVariable String qrCodeId) {
        String verificationResult = productService.verifyByQRCode(qrCodeId);
        return ResponseEntity.ok(verificationResult);
    }
}
