package com.prod.models.orders;

import com.prod.models.ENUM.Bill_Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bills")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int order_id;
    //nguoi nhan
    private String user;
    private String address;
    private String phone;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Bill_Status status = Bill_Status.CHUA_THANH_TOAN;
    @Builder.Default
    private LocalDateTime create_at = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime update_at = LocalDateTime.now();
}
