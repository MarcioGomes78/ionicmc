package com.marcio.ionicmc.services;

import org.springframework.mail.SimpleMailMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MockEmailService extends AbstratictEmailService {

    private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

    @Override
    public void sendEmail(SimpleMailMessage msg) {
        LOG.info("Simulando envio de email...");
        LOG.info(msg.toString());
        LOG.info("Email enviado!");
    }

}
