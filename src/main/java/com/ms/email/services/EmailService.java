package com.ms.email.services;

import com.ms.email.enums.StatusEmail;
import com.ms.email.models.EmailModel;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class EmailService {

    private final JavaMailSender emailSender;

    @Autowired
    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendEmail(EmailModel emailModel, String attachmentFileName) {
        log.info("Starting sending email");
        emailModel.setSendDateEmail(LocalDateTime.now());

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
        } catch (MessagingException e) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
            log.error(">> Error to send email: {}", e.getMessage());
        }

        log.info("Finishing sending email");
    }
}
