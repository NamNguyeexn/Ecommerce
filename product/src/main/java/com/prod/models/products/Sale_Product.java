package com.prod.models.products;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "sale_product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sale_Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Builder.Default
    private String productId = "";
    private int saleId;
    @Builder.Default
    private LocalDateTime create_at = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime update_at = LocalDateTime.now();
}
