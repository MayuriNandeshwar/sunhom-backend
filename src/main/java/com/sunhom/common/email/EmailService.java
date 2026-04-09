// package com.sunhom.common.email;

// import lombok.RequiredArgsConstructor;
// import org.springframework.mail.SimpleMailMessage;
// import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.stereotype.Service;

// @Service
// @RequiredArgsConstructor
// public class EmailService {

// private final JavaMailSender mailSender;

// public void sendNewsletterWelcome(String email) {

// SimpleMailMessage message = new SimpleMailMessage();

// message.setFrom("info@sunhom.in");
// message.setTo(email);

// message.setSubject("Welcome to SUNHOM");

// message.setText(
// "Welcome to SUNHOM.\n\n" +
// "Thank you for subscribing to our fragrance newsletter.\n\n" +
// "You will now receive exclusive offers and launches.\n\n" +
// "Team SUNHOM\n" +
// "www.sunhom.in");

// mailSender.send(message);
// }
// }