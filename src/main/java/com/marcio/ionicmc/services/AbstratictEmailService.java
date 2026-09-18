package com.marcio.ionicmc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.marcio.ionicmc.domain.Pedido;

@Service
public abstract class AbstratictEmailService implements EmailService {

    @Value("${default.sender}")
    private String sender;

    @Override
    public void sendOrderConfirmationEmail(Pedido obj) {
        SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
        SimpleMailMessage sm = new SimpleMailMessage();
        // Destinatário
        sm.setTo(obj.getCliente().getEmail());
        // Remetente
        sm.setFrom(sender);
        // Assunto
        sm.setSubject("Pedido confirmado! Código: " + obj.getId());
        // Data do pedido
        sm.setSentDate(new Date(System.currentTimeMillis()));
        // toString() de Pedido
        sm.setText(obj.toString());
        return sm;
    }

}
