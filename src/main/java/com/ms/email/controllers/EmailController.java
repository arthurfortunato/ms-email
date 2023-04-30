package com.ms.email.controllers;

import com.ms.email.dtos.EmailDto;
import com.ms.email.models.EmailModel;
import com.ms.email.services.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class EmailController {

    @Autowired
    EmailService emailService;

    @PostMapping("/sending-email")
    public ResponseEntity<EmailModel> sendingEmail(@ModelAttribute @Valid EmailDto emailDto,
                                                   @RequestParam("attachment") MultipartFile attachment) throws IOException {
        EmailModel emailModel = new EmailModel();
        String attachmentFileName = attachment.getOriginalFilename();
        if (!attachment.isEmpty()) {
            emailDto.setAttachmentBytes(attachment.getBytes());
        }
        BeanUtils.copyProperties(emailDto, emailModel);
        emailService.sendEmail(emailModel, attachmentFileName);
        return new ResponseEntity<>(emailModel, HttpStatus.CREATED);
    }

}
