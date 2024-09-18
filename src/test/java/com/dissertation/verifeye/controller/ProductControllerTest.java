package com.dissertation.verifeye.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.dissertation.verifeye.dto.ProductDto;
import com.dissertation.verifeye.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

// Static imports for readability
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@ExtendWith(SpringExtension.class)
public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    // (1) Test for product registration
    @Test
    void testRegisterProduct() throws Exception {
        // Arrange
        ProductDto productDto = new ProductDto("Product1", "Description", "12345");
        when(productService.registerProduct(any(ProductDto.class))).thenReturn(productDto);

        // Act & Assert
        mockMvc.perform(post("/api/products/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Product1"));
    }

    // (2) Test to generate product QR code
    @Test
    void testGenerateQRCode() throws Exception {
        // Arrange
        String productId = "12345";
        String qrCode = "QRCodeImageBase64";
        when(productService.generateQRCode(anyString())).thenReturn(qrCode);

        // Act & Assert
        mockMvc.perform(post("/api/products/{productID}/generate-qrcode", productId))
                .andExpect(status().isOk())
                .andExpect(content().string(qrCode));
    }

    // (3) Test to verify product
    @Test
    void testVerifyProduct() throws Exception {
        // Arrange
        String verificationResult = "Product is valid";
        ProductVerificationRequest verificationRequest = new ProductVerificationRequest("qrcode123");
        when(productService.verifyProduct(any(ProductVerificationRequest.class))).thenReturn(verificationResult);

        // Act & Assert
        mockMvc.perform(post("/api/products/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(verificationRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(verificationResult));
    }

    // (4) Test to update product information
    @Test
    void testUpdateProduct() throws Exception {
        // Arrange
        String productId = "12345";
        ProductDto updatedProduct = new ProductDto("UpdatedProduct", "Updated Description", "12345");
        when(productService.updateProduct(anyString(), any(ProductDto.class))).thenReturn(updatedProduct);

        // Act & Assert
        mockMvc.perform(put("/api/products/{productID}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("UpdatedProduct"));
    }

    // (5) Test to deactivate/delete product
    @Test
    void testDeactivateProduct() throws Exception {
        // Arrange
        String productId = "12345";
        when(productService.deactivateProduct(anyString())).thenReturn("Product deactivated");

        // Act & Assert
        mockMvc.perform(put("/api/products/{productID}/deactivate", productId))
                .andExpect(status().isOk())
                .andExpect(content().string("Product deactivated"));
    }

    // (6) Test to view product history
    @Test
    void testViewProductHistory() throws Exception {
        // Arrange
        String productId = "12345";
        List<ProductHistoryDto> history = Arrays.asList(
                new ProductHistoryDto("Event1", "2023-09-15"),
                new ProductHistoryDto("Event2", "2023-09-16")
        );
        when(productService.viewProductHistory(anyString())).thenReturn(history);

        // Act & Assert
        mockMvc.perform(get("/api/products/{productID}/history", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].event").value("Event1"))
                .andExpect(jsonPath("$[1].event").value("Event2"));
    }

    // (7) Test to report product issue
    @Test
    void testReportProductIssue() throws Exception {
        // Arrange
        String productId = "12345";
        ProductIssueDto issueDto = new ProductIssueDto("Packaging damaged", "Low");
        when(productService.reportProductIssue(anyString(), any(ProductIssueDto.class))).thenReturn("Issue reported");

        // Act & Assert
        mockMvc.perform(post("/api/products/{productID}/issues", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(issueDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Issue reported"));
    }

    // (8) Test to verify product by QR code
    @Test
    void testVerifyProductByQRCode() throws Exception {
        // Arrange
        String qrcodeID = "qrcode123";
        String verificationResult = "Product verified";
        when(productService.verifyByQRCode(anyString())).thenReturn(verificationResult);

        // Act & Assert
        mockMvc.perform(get("/qrcode/{qrcodeID}/verify", qrcodeID))
                .andExpect(status().isOk())
                .andExpect(content().string(verificationResult));
    }


}
