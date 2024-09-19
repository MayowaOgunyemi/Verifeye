package com.dissertation.verifeye.controller;

import com.dissertation.verifeye.dto.ProductIssueDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dissertation.verifeye.dto.ProductDto;
import com.dissertation.verifeye.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
class ProductControllerTest {

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
        // Input DTO for registering a product
        ProductDto productDto = new ProductDto(1234567890L, "Product1", "Description", "12345", null);

        // Mock the product service to return the same product
        when(productService.registerProduct(any(ProductDto.class))).thenReturn(productDto);

        // Perform the POST request and capture the response
        MvcResult mvcResult = mockMvc.perform(post("/api/products/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andReturn();

        // Debug: Print the response content
        String responseContent = mvcResult.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);

        // Verify the response status
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());

        // Parse the response body as a ProductDto (assuming it's JSON)
        ProductDto responseDto = objectMapper.readValue(responseContent, ProductDto.class);

        // Verify the content of the response
        assertNotNull(responseDto);
        assertEquals("Product1", responseDto.getName());
        assertEquals("Description", responseDto.getDescription());
        assertEquals("12345", responseDto.getSerialNumber());
        // Verify ID is present
        assertNotNull(responseDto.getId());
    }

    // (2) Test to generate product QR code
    @Test
    void testGenerateQRCode() throws Exception {
        // Arrange
        Long productId = 12345L;
        String qrCodeResponse = "QRCodeImageBase64";
        when(productService.generateQRCode(productId)).thenReturn(qrCodeResponse);

        // Act & Assert
        mockMvc.perform(post("/api/products/{productId}/generate-qrcode", productId))
                .andExpect(status().isOk())
                .andExpect(content().string(qrCodeResponse));
    }

    // (3) Test to verify product
    @Test
    void testVerifyProduct() throws Exception {
        // Arrange
        ProductDto verificationRequest = new ProductDto(null, null, null, "qrcode123", null, null, null, "Product is valid");
        when(productService.verifyProduct(any(ProductDto.class))).thenReturn(verificationRequest.getVerificationResult());

        // Act & Assert
        mockMvc.perform(post("/api/products/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(verificationRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(verificationRequest.getVerificationResult()));
    }

    // (4) Test to update product information
    @Test
    void testUpdateProduct() throws Exception {
        // Arrange
        Long productId = 12345L;
        ProductDto updatedProduct = new ProductDto("UpdatedProduct", "Updated Description", "12345", null, null, null, null, null);
        when(productService.updateProduct(eq(productId), any(ProductDto.class))).thenReturn(updatedProduct);

        // Act & Assert
        mockMvc.perform(put("/api/products/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("UpdatedProduct"));
    }

    // (5) Test to deactivate/delete product
    @Test
    void testDeactivateProduct() throws Exception {
        // Arrange
        Long productId = 12345L;
        when(productService.deactivateProduct(productId)).thenReturn("Product deactivated");

        // Act & Assert
        mockMvc.perform(put("/api/products/{productId}/deactivate", productId))
                .andExpect(status().isOk())
                .andExpect(content().string("Product deactivated"));
    }

    // (6) Test to view product history
    @Test
    void testViewProductHistory() throws Exception {
        // Arrange
        Long productId = 12345L;
        List<ProductDto> history = Arrays.asList(
                new ProductDto(null, null, null, null, "Event1", "2023-09-15", null, null),
                new ProductDto(null, null, null, null, "Event2", "2023-09-16", null, null)
        );
        when(productService.viewProductHistory(productId)).thenReturn(history);

        // Act & Assert
        mockMvc.perform(get("/api/products/{productId}/history", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].event").value("Event1"))
                .andExpect(jsonPath("$[1].event").value("Event2"));
    }

    // (7) Test to report product issue
    @Test
    void testReportProductIssue() throws Exception {
        // Arrange
        Long productId = 12345L;
        ProductIssueDto issueDto = new ProductIssueDto(productId, "Low", "Issue details");
        when(productService.reportProductIssue(eq(productId), any(ProductIssueDto.class))).thenReturn("Issue reported");

        // Act & Assert
        mockMvc.perform(post("/api/product-issues/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(issueDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Issue reported"));
    }

    // (8) Test to verify product by QR code
    @Test
    void testVerifyProductByQRCode() throws Exception {
        // Arrange
        String qrcodeId = "qrcode123";
        String verificationResult = "Product verified";
        when(productService.verifyByQRCode(qrcodeId)).thenReturn(verificationResult);

        // Act & Assert
        mockMvc.perform(get("/api/products/qrcode/{qrcodeId}/verify", qrcodeId))
                .andExpect(status().isOk())
                .andExpect(content().string(verificationResult));
    }
}
