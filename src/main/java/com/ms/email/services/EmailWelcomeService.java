package com.ms.email.services;

import com.ms.email.enums.StatusEmail;
import com.ms.email.models.EmailModel;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;

@Service
public class EmailWelcomeService {

    Logger logger = LogManager.getLogger(EmailService.class);

    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;

    public EmailWelcomeService(JavaMailSender emailSender, TemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    public void sendEmail(EmailModel emailModel, int maxRetries) {
        logger.info("Starting sending email");

        emailModel.setSendDateEmail(LocalDateTime.now());

        int retryCount = 0;
        boolean emailSent = false;

        while (!emailSent && retryCount <= maxRetries) {
            MimeMessage message = emailSender.createMimeMessage();
            Context context = new Context();
            context.setVariable("message", emailModel.getText());
            String content = templateEngine.process("SendCodeVerification", context);

            try {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
                mimeMessageHelper.setFrom(emailModel.getEmailFrom());
                mimeMessageHelper.setTo(emailModel.getEmailTo());
                mimeMessageHelper.setSubject(emailModel.getSubject());
                mimeMessageHelper.setText(content, true);

                emailSender.send(message);
                emailModel.setStatusEmail(StatusEmail.SENT);
                emailSent = true;
                logger.info("Email Status: " + emailModel.getStatusEmail());
            } catch (MessagingException e) {
                emailModel.setStatusEmail(StatusEmail.ERROR);
                logger.error(">> Error to send email: {}", e.getMessage());
                retryCount++;
            }
        }

        if (!emailSent) {
            logger.error(">> Max retry count reached. Email could not be sent.");
        }

        logger.info("Finishing sending email");
    }
}
