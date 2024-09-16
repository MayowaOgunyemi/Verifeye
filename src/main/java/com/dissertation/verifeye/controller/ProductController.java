package com.dissertation.verifeye.controller;

import com.dissertation.verifeye.dto.ProductDto;
import com.dissertation.verifeye.service.ProductService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    //Register Product
    @PostMapping("/register")
    public ResponseEntity<ProductDto> registerProduct(@RequestBody ProductDto productDto) {
        ProductDto registeredProduct = productService.registerProduct(productDto);
        return ResponseEntity.ok(registeredProduct);
    }

    //Generate QR Code
    @PostMapping("/{productID}/generate-qrcode")
    public ResponseEntity<String> generateQRCode(@PathVariable String productID) {
        String qrCode = productService.generateQRCode(productID);
        return ResponseEntity.ok(qrCode);
    }

    //Verify product
    @PostMapping("/verify")
    public ResponseEntity<String> verifyProduct(@RequestBody ProductVerificationRequest verificationRequest) {
        String verificationResult = productService.verifyProduct(verificationRequest);
        return ResponseEntity.ok(verificationResult);
    }

    //Update product Information
    @PutMapping("/{productID}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String productID, @RequestBody ProductDto productDto) {
        ProductDto updatedProduct = productService.updateProduct(productID, productDto);
        return ResponseEntity.ok(updatedProduct);
    }

    //Deactivate Product
    @PutMapping("/{productID}/deactivate")
    public ResponseEntity<String> deactivateProduct(@PathVariable String productID) {
        String result = productService.deactivateProduct(productID);
        return ResponseEntity.ok(result);
    }

    //View Product History
    @GetMapping("/{productID}/history")
    public ResponseEntity<List<ProductHistoryDto>> viewProductHistory(@PathVariable String productID) {
        List<ProductHistoryDto> history = productService.viewProductHistory(productID);
        ResponseEntity<List<ProductHistoryDto>> ok = ResponseEntity.ok(history);
        return ok;
    }

    //Report Product Issue
    @PostMapping("/{productID}/issues")
    public ResponseEntity<String> reportProductIssue(@PathVariable String productID, @RequestBody ProductIssueDto issueDto) {
        String result = productService.reportProductIssue(productID, issueDto);
        return ResponseEntity.ok(result);
    }

    //Verify Product by QR code
    @GetMapping("/{qrcodeID}/verify")
    public ResponseEntity<String> verifyProductByQRCode(@PathVariable String qrcodeID) {
        String verificationResult = productService.verifyByQRCode(qrcodeID);
        return ResponseEntity.ok(verificationResult);
    }


}
