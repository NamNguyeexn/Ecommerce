package com.prod.services.caches.impl;

import com.common.services.IEmailService;
import com.prod.models.products.Product;
import com.prod.services.caches.IEmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class EmailSender implements IEmailSender {
    private final String emailServer = "montoan01102002@gmail.com";
    @Autowired
    private IEmailService emailService;
    @Override
    public void outOfProduct(String email, String product) {
        String subject = "San pham " + product + " da het hang";
        String body = "Khach hang than mem, san pham " + product
                + " o trong gio hang cua ban da het hang," +
                " hay tham khao cac san pham khac cua chung toi ";
        emailService.sendEmail(emailServer, email, subject, body);
    }

    @Override
    public void lowQuantityProduct(String email, String product, int quantity) {
        String subject = "San pham " + product + " khong con nhieu";
        String body = "Khach hang than mem, san pham " + product
                + " o trong gio hang cua ban so luong khong con nhieu, " +
                "hay hoan thanh don hang cua ban";
        emailService.sendEmail(emailServer, email, subject, body);
    }
}
