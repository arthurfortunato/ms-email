package com.ms.email.services;

import com.ms.email.enums.StatusEmail;
import com.ms.email.exceptions.ValidateException;
import com.ms.email.models.EmailModel;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailService {

    Logger logger = LogManager.getLogger(EmailService.class);

    private final JavaMailSender emailSender;

    public void sendEmail(EmailModel emailModel, String attachmentFileName, int maxRetries) throws ValidateException {
        logger.info("Starting sending email");

        emailModel.setSendDateEmail(LocalDateTime.now());

        int retryCount = 0;
        boolean emailSent = false;

        while (!emailSent && retryCount <= maxRetries) {
            MimeMessage message = emailSender.createMimeMessage();

            try {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
                mimeMessageHelper.setFrom(emailModel.getEmailFrom());
                mimeMessageHelper.setTo(emailModel.getEmailTo());
                mimeMessageHelper.setSubject(emailModel.getSubject());
                mimeMessageHelper.setText(emailModel.getText(), true);

                if (emailModel.getAttachmentBytes() != null) {
                    ByteArrayResource attachmentResource = new ByteArrayResource(emailModel.getAttachmentBytes());
                    mimeMessageHelper.addAttachment(attachmentFileName, attachmentResource);
                }

                emailSender.send(message);
                emailModel.setStatusEmail(StatusEmail.SENT);
                emailSent = true;
                logger.info("Email successfully sent!");
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
