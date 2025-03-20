package com.project.busapp.service;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
    void sendBookingConfirmationEmail(String to, String bookingDetails);
}