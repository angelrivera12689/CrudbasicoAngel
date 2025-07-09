package com.sena.crud_basic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendRecoveryToken(String toEmail, String token) {
        String subject = "Recuperación de contraseña";
String recoveryLink = "http://localhost:5500/reset-password.html?token=" + token;
        String body = "Has solicitado restablecer tu contraseña.\n\n" +
                      "Haz clic en el siguiente enlace para continuar:\n" + recoveryLink +
                      "\n\nEste enlace expirará en 1 hora.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}
