package com.project.busapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${mail.from}") // Get from application.properties
    private String fromAddress;

    @Value("${mail.subject.booking.confirmation}")
    private String bookingConfirmationSubject;


    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    @Override
    public void sendBookingConfirmationEmail(String to, String bookingDetails) {
        sendSimpleMessage(to, bookingConfirmationSubject, bookingDetails);
    }
}