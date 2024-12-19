package com.payment.controllers;

import com.common.DTO.ResponseObject;
import com.payment.DTO.PaymentDTO;
import com.payment.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class VnPayController {
    private final PaymentService paymentService;
    @GetMapping("/vn-pay")
    public ResponseObject<PaymentDTO.VNPayResponse> pay(HttpServletRequest request) {
        return ResponseObject.<PaymentDTO.VNPayResponse>builder()
                .isSuccess(true)
                .data( paymentService.createVnPayPayment(request))
                .message("Thanh cong")
                .build();
//                new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(request));
    }
    @GetMapping("/vn-pay-callback")
    public  ResponseEntity <ResponseObject<PaymentDTO.VNPayResponse>> payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            return ResponseEntity.badRequest().body( ResponseObject.<PaymentDTO.VNPayResponse>builder()
                    .isSuccess(true)
                    .data(null )
//                    PaymentDTO.VNPayResponse("00", "Success", "")
                    .message("Thanh cong")
                    .build());
//                    ResponseObject<>(HttpStatus.OK, "Success", new PaymentDTO.VNPayResponse("00", "Success", ""));
        } else {
            return ResponseEntity.badRequest().body(
                    ResponseObject.<PaymentDTO.VNPayResponse>builder()
                            .isSuccess(false)
                            .data(null)
                            .message("that bại")
                            .build()
            );
//                    new ResponseObject<>(HttpStatus.BAD_REQUEST, "Failed", null);
        }
    }

//    private final String VNPAY_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
//    private final String MERCHANT_CODE = "YOUR_MERCHANT_CODE";
//    private final String API_KEY = "YOUR_API_KEY";
//    private final String VNPAY_RETURN_URL = "http://localhost:3000/payment-result";
//
//    @PostMapping("/createPayment")
//    public String createPayment(@RequestBody Map<String, Object> request) {
//        String orderId = RandomStringUtils.randomNumeric(8); // Mã giao dịch mới
//        String amount = request.get("amount").toString();
//        String orderInfo = "Payment for order " + orderId;
//        String currCode = "VND";
//
//        Date now = new Date();
//        String createDate = new SimpleDateFormat("yyyyMMddHHmmss").format(now);
//
//        String vnp_TxnRef = orderId; // Mã giao dịch
//        String vnp_Amount = String.valueOf(Double.parseDouble(amount) * 100);
//        String vnp_SecureHash = generateSecureHash(vnp_TxnRef, vnp_Amount, createDate);
//
//        StringBuilder vnpUrl = new StringBuilder(VNPAY_URL);
//        vnpUrl.append("?vnp_Version=2.0.0")
//                .append("&vnp_Command=pay")
//                .append("&vnp_TmnCode=" + MERCHANT_CODE)
//                .append("&vnp_TransactionType=pay")
//                .append("&vnp_OrderInfo=" + orderInfo)
//                .append("&vnp_TxnRef=" + vnp_TxnRef)
//                .append("&vnp_Amount=" + vnp_Amount)
//                .append("&vnp_CurrCode=" + currCode)
//                .append("&vnp_CreateDate=" + createDate)
//                .append("&vnp_ReturnUrl=" + VNPAY_RETURN_URL)
//                .append("&vnp_SecureHash=" + vnp_SecureHash);
//
//        return vnpUrl.toString(); // Trả về URL thanh toán
//    }
//
//    private String generateSecureHash(String txnRef, String amount, String createDate) {
//        String data = "vnp_TmnCode=" + MERCHANT_CODE
//                + "&vnp_Amount=" + amount
//                + "&vnp_CurrCode=VND"
//                + "&vnp_OrderInfo=Payment for order " + txnRef
//                + "&vnp_TxnRef=" + txnRef
//                + "&vnp_CreateDate=" + createDate;
//
//        return Utils.hmacSHA256(data, API_KEY); // Phương thức mã hóa
//    }
}