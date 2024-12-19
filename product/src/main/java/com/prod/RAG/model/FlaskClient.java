package com.prod.RAG.model;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
@Component
public class FlaskClient {

    private static final String FLASK_URL = "http://localhost:5000/query";

    public static String getAnswerFromFlask(String question) {
        try {
            // Tạo RestTemplate
            RestTemplate restTemplate = new RestTemplate();

            // Tạo request body
            Map<String, String> requestBody = Map.of("question", question);

            // Cấu hình headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

            // Gửi POST request tới Flask
            ResponseEntity<Map> response = restTemplate.exchange(
                    FLASK_URL,
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            // Xử lý kết quả trả về
            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseBody = response.getBody();
                if (responseBody != null && responseBody.containsKey("answer")) {
                    return (String) responseBody.get("answer");
                }
            }

            return "Không nhận được câu trả lời từ Flask.";
        } catch (Exception e) {
            System.err.println("Lỗi khi gọi Flask API: " + e.getMessage());
            return "Lỗi khi kết nối tới Flask.";
        }
    }
}
