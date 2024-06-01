package com.haduc.go_travel_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderService {
    private final JavaMailSender mailSender;

    public void sendSimpleEmail(String toEmail,
                                String subject,
                                String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("havanducdeptrai@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
        System.out.println("Mail Send send ...");
    }
}
