package com.prod.models.carts;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "colors")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String value;
    private String code;
    @Builder.Default
    private LocalDateTime create_at = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime update_at = LocalDateTime.now();
}
