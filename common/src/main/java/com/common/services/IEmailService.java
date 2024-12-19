package com.common.services;


import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync(proxyTargetClass = true)
public interface IEmailService {
    void sendEmail(String from, String to, String subject, String body);
    void sendEmails(String from, String[] to, String subject, String body);
}
