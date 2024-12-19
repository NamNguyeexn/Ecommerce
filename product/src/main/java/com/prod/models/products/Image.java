package com.prod.models.products;

import com.prod.models.ENUM.Type_Image;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "images")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String src;
    private Type_Image type;
    private int product_id;
    @Builder.Default
    private LocalDateTime create_at = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime update_at = LocalDateTime.now();
}
