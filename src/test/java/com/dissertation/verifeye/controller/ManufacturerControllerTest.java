package com.dissertation.verifeye.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.dissertation.verifeye.dto.ManufacturerDto;
import com.dissertation.verifeye.service.ManufacturerService;
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
public class ManufacturerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ManufacturerService manufacturerService;

    @InjectMocks
    private ManufacturerController manufacturerController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(manufacturerController).build();
    }

    // Your test methods for manufacturer-related endpoints
    @Test
    void registerManufacturer() {
        // Arrange
        Manufacturer manufacturer = new Manufacturer("John Doe", "johndoe@example.com", "password123");

        // Act
        ResponseEntity<Manufacturer> response = manufacturerController.registerManufacturer(manufacturer);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(manufacturer.getName(), response.getBody().getName());
    }

    @Test
    void loginManufacturer() {
        // Arrange
        Manufacturer manufacturer = new Manufacturer("John Doe", "johndoe@example.com", "password123");
        when(manufacturerService.login(anyString(), anyString())).thenReturn(manufacturer);

        // Act
        ResponseEntity<Manufacturer> response = manufacturerController.loginManufacturer(manufacturer.getEmail(), manufacturer.getPassword());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(manufacturer.getName(), response.getBody().getName());
    }
}
