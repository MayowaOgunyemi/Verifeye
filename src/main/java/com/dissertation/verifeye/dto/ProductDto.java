package com.dissertation.verifeye.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id; // Assuming you use Long as the ID type in the entity
    private String name;
    private String description;
    private String serialNumber;
    private String qrCode;

}
