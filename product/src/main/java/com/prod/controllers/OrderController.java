package com.prod.controllers;

import com.common.DTO.ResponseObject;
import com.prod.DTO.AccountDTO;
import com.prod.DTO.UserDTO;
import com.prod.facades.DTO.BillDTO;
import com.prod.facades.DTO.OrderDTO;
import com.prod.facades.IOrderFacade;
import com.prod.kafka.producers.OrderProducer;
import com.prod.models.ENUM.Order_Status;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@Slf4j
public class OrderController extends Controller<OrderDTO> {
    @Autowired
    private OrderProducer orderProducer;
    @Autowired
    private IOrderFacade orderFacade;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject<String>> createOrder(@RequestBody OrderDTO orderDto,
                                                              HttpServletRequest request) {
        try {
            UserDTO user = getUser(request);
            AccountDTO accountDTO = getAccount(request);
            if (user == null)
                return notFoundUser();
            orderDto.setUser_id(user.getId());
            orderDto.setUser_email(accountDTO.getEmail());
            orderProducer.send("create-order", orderDto);
            return ResponseEntity.ok().body(
                    ResponseObject.<String>builder()
                            .message("Dang xu ly don hang")
                            .isSuccess(true)
                            .data(null)
                            .build()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return serverE();
        }
    }

    @PostMapping("/updateBill")
    public ResponseEntity<ResponseObject<BillDTO>> updateBill(@RequestBody BillDTO billDTO, HttpServletRequest request) {
        try {
            UserDTO user = getUser(request);
            if (user == null) return notFoundUser();
            return ResponseEntity.ok().body(
                    ResponseObject.<BillDTO>builder()
                            .data(orderFacade.updateBill(billDTO))
                            .isSuccess(true)
                            .message("Da thuc hien cap nhat dia chi don hang")
                            .build()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return serverE();
        }
    }

    @GetMapping("/change-status")
    public ResponseEntity<ResponseObject<OrderDTO>> changeStatus(@RequestParam int id,
                                                                 HttpServletRequest request) {
        try {
            AccountDTO accountDTO = getAccount(request);
            if (accountDTO == null) return accountNotFound();
            if (accountDTO.getRole().getName().equals("ADMIN")) {
                OrderDTO dto = orderFacade.changeStatus(id);
                if (dto == null)
                    return ResponseEntity.badRequest().body(
                            ResponseObject.<OrderDTO>builder()
                                    .message("Khong the thay doi trang thai don hang")
                                    .build()
                    );
                else
                    return ResponseEntity.ok().body(
                            ResponseObject.<OrderDTO>builder()
                                    .data(dto)
                                    .isSuccess(true)
                                    .message("Da cap nhat trang thai don hang")
                                    .build()
                    );
            } else return notOwner();
        } catch (Exception e) {
            log.error(e.getMessage());
            return serverError(e);
        }
    }

    @GetMapping("/byUserId")
    public ResponseEntity<ResponseObject<Page<OrderDTO>>> findByUserId(@RequestParam(defaultValue = "1") int page,
                                                                       @RequestParam(defaultValue = "10") int limit,
                                                                       HttpServletRequest request) {
        try {
            UserDTO user = getUser(request);
            if (user == null) return notFoundUser();
            AccountDTO accountDTO = getAccount(request);
            if (accountDTO == null) return accountNotFoundP();
            if (!accountDTO.getRole().getName().equals("ADMIN")) return notOwnersP();
            Page<OrderDTO> res = orderFacade.getOrdersByUserId(user.getId(), page, limit);
            if (res.isEmpty())
                return ResponseEntity.badRequest().body(
                        ResponseObject.<Page<OrderDTO>>builder()
                                .message("Khong tim thay don dat hang nao cua nguoi dung")
                                .build()
                );
            else
                return ResponseEntity.ok().body(
                        ResponseObject.<Page<OrderDTO>>builder()
                                .data(res)
                                .isSuccess(true)
                                .message("Da tim thay cac don dat hang cua nguoi dung")
                                .build()
                );
        } catch (Exception e) {
            log.error(e.getMessage());
            return serverE();
        }
    }
    @GetMapping("/byStatus")
    public ResponseEntity<ResponseObject<Page<OrderDTO>>> getOrdersByStatus(@RequestParam(defaultValue = "1") int page,
                                                                       @RequestParam(defaultValue = "10") int limit,
                                                                       @RequestParam(defaultValue = "CHO_XAC_NHAN") String status,
                                                                       HttpServletRequest request) {
        try {
            UserDTO user = getUser(request);
            if (user == null) return notFoundUser();
            AccountDTO accountDTO = getAccount(request);
            if (accountDTO == null) return accountNotFoundP();
            if (!accountDTO.getRole().getName().equals("ADMIN")) return notOwnersP();
            Page<OrderDTO> res = orderFacade.getAllOrdersByStatus(status, page, limit);
            if (res.isEmpty())
                return ResponseEntity.badRequest().body(
                        ResponseObject.<Page<OrderDTO>>builder()
                                .message("Khong tim thay don dat hang nao co trang thai " + status)
                                .build()
                );
            else
                return ResponseEntity.ok().body(
                        ResponseObject.<Page<OrderDTO>>builder()
                                .data(res)
                                .isSuccess(true)
                                .message("Da tim thay cac don dat hang theo trang thai " + status)
                                .build()
                );
        } catch (Exception e) {
            log.error(e.getMessage());
            return serverE();
        }
    }

    private ResponseEntity<ResponseObject<Page<OrderDTO>>> notOwnersP() {
        return ResponseEntity.badRequest().body(
                ResponseObject.<Page<OrderDTO>>builder()
                        .message(NOT_OWNER)
                        .build()
        );
    }

    private ResponseEntity<ResponseObject<Page<OrderDTO>>> accountNotFoundP() {
        return ResponseEntity.badRequest().body(
                ResponseObject.<Page<OrderDTO>>builder()
                        .message(NOT_FOUND_ACCOUNT)
                        .build()
        );
    }

    @GetMapping("/getBills")
    public ResponseEntity<ResponseObject<Page<BillDTO>>> getBills(@RequestParam(defaultValue = "1") int page,
                                                                  @RequestParam(defaultValue = "10") int limit,
                                                                  HttpServletRequest request) {
        try {
            UserDTO user = getUser(request);
            if (user == null) return notFoundUser();
            Page<BillDTO> res = orderFacade.getBillsByUserId(user.getId(), page, limit);
            if (res.isEmpty())
                return ResponseEntity.badRequest().body(
                        ResponseObject.<Page<BillDTO>>builder()
                                .message("Khong tim thay hoa don nao cua nguoi dung")
                                .build()
                );
            else
                return ResponseEntity.ok().body(
                        ResponseObject.<Page<BillDTO>>builder()
                                .message("Da tim thay cac hoa don cua nguoi dung")
                                .isSuccess(true)
                                .data(res)
                                .build()
                );
        } catch (Exception e) {
            log.error(e.getMessage());
            return serverE();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseObject<Page<OrderDTO>>> getAllOrders(@RequestParam(defaultValue = "1") int page,
                                                                       @RequestParam(defaultValue = "10") int size,
                                                                       HttpServletRequest request) {
        try {
            AccountDTO accountDTO = getAccount(request);
            if (accountDTO == null) return accountNotFoundP();
            if (accountDTO.getRole().getName().equals("ADMIN")) {
                return ResponseEntity.ok().body(
                        ResponseObject.<Page<OrderDTO>>builder()
                                .data(orderFacade.getAllOrders(page, size))
                                .isSuccess(true)
                                .message("Da tim thay cac don dat hang")
                                .build()
                );
            } else return notOwnersP();
        } catch (Exception e) {
            log.error(e.getMessage());
            return serverE();
        }
    }

    @Override
    public ResponseEntity<ResponseObject<List<OrderDTO>>> notOwners() {
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject<List<OrderDTO>>> serverErrors() {
        return null;
    }

    private <T> ResponseEntity<ResponseObject<T>> notFoundUser() {
        return ResponseEntity.badRequest().body(
                ResponseObject.<T>builder()
                        .message(NOT_FOUND_USER)
                        .build());
    }

    private <T> ResponseEntity<ResponseObject<T>> serverE() {
        return ResponseEntity.badRequest().body(
                ResponseObject.<T>builder()
                        .message(NOT_OWNER)
                        .build()
        );
    }
}
