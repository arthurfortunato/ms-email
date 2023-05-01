package com.ms.email.controllers;

import com.ms.email.dtos.EmailDto;
import com.ms.email.exceptions.ValidateException;
import com.ms.email.models.EmailModel;
import com.ms.email.services.EmailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
public class EmailController {

    Logger logger = LogManager.getLogger(EmailController.class);

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sending-email")
    public ResponseEntity<Object> sendingEmail(@Valid EmailDto emailDto, @RequestParam("attachment") MultipartFile attachment) throws ValidateException {

        try {
            emailDto.validate();
            if (!attachment.isEmpty()) {
                emailDto.setAttachmentBytes(attachment.getBytes());
            }
            EmailModel emailModel = emailDto.convertToEmailModel();
            String attachmentFileName = attachment.getOriginalFilename();

            emailService.sendEmail(emailModel, attachmentFileName);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IOException e) {
            logger.error("An error occurred while processing the email: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing email");
        } catch (ValidateException e) {
            logger.error("Validation error occurred: {}", e.getMessage());
            if (e.getMessage().contains("BAD REQUEST")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
            throw e;
        }
    }

}
