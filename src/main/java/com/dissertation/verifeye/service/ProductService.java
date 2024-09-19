package com.dissertation.verifeye.service;

import com.dissertation.verifeye.dto.ProductDto;
import com.dissertation.verifeye.dto.ProductIssueDto;
import com.dissertation.verifeye.entity.Product;
import com.dissertation.verifeye.repository.ProductRepository;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // Register a new product
    public ProductDto registerProduct(ProductDto productDto) {
        Product product = mapDtoToEntity(productDto);
        Product savedProduct = productRepository.save(product);
        return mapEntityToDto(savedProduct);
    }

    // Update an existing product
    public ProductDto updateProduct(Long productId, ProductDto productDto) {
        Optional<Product> existingProductOptional = productRepository.findById(productId);
        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();
            existingProduct.setName(productDto.getName());
            existingProduct.setDescription(productDto.getDescription());
            // Update other fields as necessary

            Product savedProduct = productRepository.save(existingProduct);
            return mapEntityToDto(savedProduct);
        } else {
            throw new RuntimeException("Product with ID " + productId + " not found.");
        }
    }

    // Get a product by ID
    public ProductDto getProductById(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        return productOptional.map(this::mapEntityToDto).orElse(null);
    }

    // Get all products
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapEntityToDto).collect(Collectors.toList());
    }

    // Generate QR code for a product
    public String generateQRCode(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            // Simulate QR code generation
            return "QRCodeImageBase64"; // Replace with actual QR code generation logic
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    // Deactivate a product
    public String deactivateProduct(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            productRepository.deleteById(productId);
            return "Product deactivated";
        } else {
            throw new RuntimeException("Product with ID " + productId + " not found.");
        }
    }

    // View product history
    public List<ProductDto> viewProductHistory(Long productId) {
        // Simulate history retrieval
        // Replace with actual logic to retrieve product history
        return List.of(); // Example return, replace with actual implementation
    }

    // Verify product by serial number
    public String verifyProduct(ProductDto verificationRequest) {
        Optional<Product> productOptional = productRepository.findBySerialNumber(verificationRequest.getSerialNumber());
        if (productOptional.isPresent()) {
            return "Product is valid";
        } else {
            return "Product is invalid";
        }
    }

    // Verify product by QR code
    public String verifyByQRCode(String qrCodeID) {
        Optional<Product> productOptional = productRepository.findByQrCode(qrCodeID);
        if (productOptional.isPresent()) {
            return "Product verified";
        } else {
            return "Product not found";
        }
    }

    // Map DTO to entity
    private Product mapDtoToEntity(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setSerialNumber(productDto.getSerialNumber());
        product.setQrCode(productDto.getQrCode());
        return product;
    }

    // Map entity to DTO
    private ProductDto mapEntityToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setSerialNumber(product.getSerialNumber());
        productDto.setQrCode(product.getQrCode());
        return productDto;
    }
}
