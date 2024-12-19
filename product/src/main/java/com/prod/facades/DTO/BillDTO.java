package com.prod.facades.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillDTO {
    private int id;
    private String phone;
    private String address;
    private String user;
    private String bill_status;
    private int order_id;
}
