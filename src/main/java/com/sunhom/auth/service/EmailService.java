package com.sunhom.auth.service;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Value("${sunhom.mail.from:no-reply@sunhom.com}")
    private String fromEmail;

    @Value("${spring.mail.username:}")
    private String mailUsername;

    public void sendVerificationEmail(String toEmail, String recipientName, String code) {
        String subject = "Verify Your SUNHOM Account - " + code;
        String name = (recipientName != null && !recipientName.isBlank()) ? recipientName : "Valued Customer";

        String htmlContent = """
            <!DOCTYPE html>
            <html>
            <head>
              <meta charset="utf-8">
              <style>
                body { font-family: 'Helvetica Neue', Arial, sans-serif; background-color: #FAF8F5; margin: 0; padding: 24px; color: #2B2520; }
                .container { max-width: 560px; margin: 0 auto; background: #ffffff; border-radius: 16px; border: 1px solid #E8E1D7; padding: 36px 32px; box-shadow: 0 4px 20px rgba(0,0,0,0.04); }
                .brand { text-align: center; font-size: 26px; font-weight: bold; letter-spacing: 3px; color: #1E1B18; margin-bottom: 24px; text-transform: uppercase; font-family: Georgia, serif; }
                h2 { font-size: 20px; font-weight: 600; color: #1E1B18; margin-top: 0; margin-bottom: 12px; }
                p { font-size: 14px; line-height: 1.6; color: #5A534B; margin: 8px 0 16px 0; }
                .code-box { background: #F8F5F0; border: 2px dashed #D97706; border-radius: 12px; padding: 18px; text-align: center; margin: 24px 0; }
                .code { font-size: 32px; font-weight: 700; letter-spacing: 8px; color: #B45309; font-family: monospace; }
                .expiry { font-size: 12px; color: #8C827A; margin-top: 6px; }
                .footer { border-top: 1px solid #EFEAE2; margin-top: 32px; padding-top: 20px; font-size: 12px; color: #A8A29E; text-align: center; }
              </style>
            </head>
            <body>
              <div class="container">
                <div class="brand">SUNHOM</div>
                <h2>Verify Your Email Address</h2>
                <p>Hello %s,</p>
                <p>Thank you for creating an account with SUNHOM. Please use the verification code below to verify your email address and activate your account:</p>
                <div class="code-box">
                  <div class="code">%s</div>
                  <div class="expiry">Valid for 15 minutes</div>
                </div>
                <p>If you did not request this verification, you can safely ignore this email.</p>
                <div class="footer">
                  &copy; SUNHOM. All rights reserved.<br>
                  Premium Handcrafted Fragrances & Lifestyle
                </div>
              </div>
            </body>
            </html>
            """.formatted(name, code);

        sendEmail(toEmail, subject, htmlContent, code, "EMAIL VERIFICATION");
    }

    public void sendPasswordResetEmail(String toEmail, String recipientName, String code) {
        String subject = "Reset Your SUNHOM Password - " + code;
        String name = (recipientName != null && !recipientName.isBlank()) ? recipientName : "Valued Customer";

        String htmlContent = """
            <!DOCTYPE html>
            <html>
            <head>
              <meta charset="utf-8">
              <style>
                body { font-family: 'Helvetica Neue', Arial, sans-serif; background-color: #FAF8F5; margin: 0; padding: 24px; color: #2B2520; }
                .container { max-width: 560px; margin: 0 auto; background: #ffffff; border-radius: 16px; border: 1px solid #E8E1D7; padding: 36px 32px; }
                .brand { text-align: center; font-size: 26px; font-weight: bold; letter-spacing: 3px; color: #1E1B18; margin-bottom: 24px; text-transform: uppercase; font-family: Georgia, serif; }
                h2 { font-size: 20px; font-weight: 600; color: #1E1B18; margin-top: 0; }
                p { font-size: 14px; line-height: 1.6; color: #5A534B; }
                .code-box { background: #F8F5F0; border: 2px dashed #D97706; border-radius: 12px; padding: 18px; text-align: center; margin: 24px 0; }
                .code { font-size: 32px; font-weight: 700; letter-spacing: 8px; color: #B45309; font-family: monospace; }
                .footer { border-top: 1px solid #EFEAE2; margin-top: 32px; padding-top: 20px; font-size: 12px; color: #A8A29E; text-align: center; }
              </style>
            </head>
            <body>
              <div class="container">
                <div class="brand">SUNHOM</div>
                <h2>Password Reset Request</h2>
                <p>Hello %s,</p>
                <p>We received a request to reset your password. Use the code below to complete the reset:</p>
                <div class="code-box">
                  <div class="code">%s</div>
                  <div style="font-size: 12px; color: #8C827A; margin-top: 6px;">Valid for 15 minutes</div>
                </div>
                <p>If you did not make this request, please ensure your account credentials are secure.</p>
                <div class="footer">&copy; SUNHOM. All rights reserved.</div>
              </div>
            </body>
            </html>
            """.formatted(name, code);

        sendEmail(toEmail, subject, htmlContent, code, "PASSWORD RESET");
    }

    public void sendWelcomeEmail(String toEmail, String recipientName) {
        String subject = "Welcome to SUNHOM!";
        String name = (recipientName != null && !recipientName.isBlank()) ? recipientName : "Valued Customer";

        String htmlContent = """
            <!DOCTYPE html>
            <html>
            <body style="font-family: 'Helvetica Neue', Arial, sans-serif; background-color: #FAF8F5; margin: 0; padding: 24px;">
              <div style="max-width: 560px; margin: 0 auto; background: #ffffff; border-radius: 16px; border: 1px solid #E8E1D7; padding: 36px 32px;">
                <div style="text-align: center; font-size: 26px; font-weight: bold; letter-spacing: 3px; color: #1E1B18; margin-bottom: 24px; text-transform: uppercase; font-family: Georgia, serif;">SUNHOM</div>
                <h2 style="font-size: 20px; color: #1E1B18;">Welcome to the SUNHOM Family</h2>
                <p style="font-size: 14px; line-height: 1.6; color: #5A534B;">Hello %s,</p>
                <p style="font-size: 14px; line-height: 1.6; color: #5A534B;">Your email has been verified and your account is now fully active! You can now explore our curated scents, save items to your wishlist, and experience seamless checkout.</p>
                <div style="text-align: center; margin: 28px 0;">
                  <a href="http://localhost:3000" style="display: inline-block; background: #D97706; color: #ffffff; text-decoration: none; padding: 12px 28px; border-radius: 9999px; font-weight: 600; font-size: 14px;">Shop Collections</a>
                </div>
                <div style="border-top: 1px solid #EFEAE2; margin-top: 32px; padding-top: 20px; font-size: 12px; color: #A8A29E; text-align: center;">&copy; SUNHOM. All rights reserved.</div>
              </div>
            </body>
            </html>
            """.formatted(name);

        sendEmail(toEmail, subject, htmlContent, "", "WELCOME");
    }

    private void sendEmail(String toEmail, String subject, String htmlContent, String code, String actionType) {
        log.info("===========================================================");
        log.info("📧 [{}] To: {}", actionType, toEmail);
        if (code != null && !code.isBlank()) {
            log.info("🔢 [VERIFICATION CODE]: {}", code);
        }
        log.info("===========================================================");

        if (mailSender == null || mailUsername == null || mailUsername.isBlank()) {
            log.info("ℹ️ Mail server username not set. Verification code is logged above for development. Configure spring.mail.username in application.properties to send live emails.");
            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(message);
            log.info("✅ Live email successfully dispatched via SMTP to {}", toEmail);
        } catch (Exception ex) {
            log.warn("⚠️ Could not send live email via SMTP: {}. Verification code is active and logged above: {}", ex.getMessage(), code);
        }
    }
}
