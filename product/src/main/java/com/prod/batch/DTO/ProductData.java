package com.prod.batch.DTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="products_data")
public class ProductData {
    @Id
    private int id;
    private String title;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    @Nullable
    private Double star;
    private int price;
    private String categoryCode;
    private String priceCode;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String images;
    private int quantity;
    private Double sale;
    private int quantitySold;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
