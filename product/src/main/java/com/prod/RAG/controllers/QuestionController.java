package com.prod.RAG.controllers;

import com.prod.RAG.model.FlaskClient;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/ques")
public class QuestionController {

    @GetMapping("/ask")
    public String handleQuestion(@RequestParam String question) {
        if (question == null || question.isEmpty()) {
            return "Câu hỏi không được để trống.";
        }

        // Gửi câu hỏi tới Flask
        String answer = FlaskClient.getAnswerFromFlask(question);
        return "Câu trả lời từ hệ thống: " + answer;
    }
}
