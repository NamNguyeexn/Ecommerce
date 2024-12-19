package com.common.services.impl;

import com.common.services.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync(proxyTargetClass = true)
public class EmailService implements IEmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Async("MailExecutor")
    @Override
    public void sendEmail(String from, String to, String subject, String body) {
        String sub = "Valid Account ";
        String bod = "Your new password for your account is: ";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(sub + subject);
        message.setText(bod + body);
        mailSender.send(message);
    }

    @Override
    @Async("MailExecutor")
    public void sendEmails(String from, String[] to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}
