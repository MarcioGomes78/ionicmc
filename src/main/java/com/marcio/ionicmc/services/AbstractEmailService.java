package com.marcio.ionicmc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.marcio.ionicmc.domain.Pedido;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    // Remetente padrão
    private String sender;

    @Autowired
    // Motor de processamento de templates
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

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
    // Prepara e-mail HTML
    protected String htmlFromTemplatePedido(Pedido obj) {
        Context context = new Context();
        // Variável que será usada no template do html -> pedido.html
        context.setVariable("pedido", obj);
        // Processa o template -> retorna o html montado
        return templateEngine.process("email/confirmacaoPedido", context);
    }

    @Override
    public void sendOrderConfirmationHtmlEmail(Pedido obj) {
        try {
            MimeMessage mm = prepareMimeMessageFromPedido(obj);
            sendHtmlEmail(mm);
        } catch (MessagingException e) {
            sendOrderConfirmationEmail(obj); // Fallback para e-mail simples
        }
    }

    // Método auxiliar para preparar o MimeMessage
    protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        // MimeMessageHelper(mimeMessage, true) -> true indica que o e-mail conterá html
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            // Destinatário
            helper.setTo(obj.getCliente().getEmail());
            // Remetente
            helper.setFrom(sender);
            // Assunto
            helper.setSubject("Pedido confirmado! Código: " + obj.getId());
            // Data do pedido
            helper.setSentDate(new Date(System.currentTimeMillis()));
            // Texto do e-mail
            helper.setText(htmlFromTemplatePedido(obj), true);
        
        return mimeMessage;
    }
}   
