package com.marcio.ionicmc.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.marcio.ionicmc.domain.Pedido;

@Service
public interface EmailService {
    void sendOrderConfirmationEmail(Pedido obj);

    void sendEmail(SimpleMailMessage msg);

    // void sendOrderConfirmationHtmlEmail(Pedido obj);

    // void sendHtmlEmail(MimeMessage msg);
}
