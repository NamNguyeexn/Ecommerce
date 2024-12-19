package com.iden.custom;


import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CustomErrorDecoder implements ErrorDecoder{
    private static final Logger log = LoggerFactory.getLogger(CustomErrorDecoder.class);
    private final ErrorDecoder defaultErrorDecoder = new Default(); // Dùng lỗi mặc định nếu cần

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() >= 400 && response.status() < 500) {
            log.info("loi 400");
            // Xử lý các mã trạng thái lỗi 4xx
            // Trả về một ngoại lệ tùy chỉnh hoặc một ngoại lệ thông báo
            return new CustomClientException("Client error: " + response.status());
        }

        // Xử lý các mã trạng thái khác bằng cách sử dụng lỗi mặc định
        return defaultErrorDecoder.decode(methodKey, response);
    }


}

