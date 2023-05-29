package com.ms.email.swagger;

import com.ms.email.dtos.EmailDto;
import com.ms.email.exceptions.ValidateException;
import com.ms.email.services.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class Swagger {

    @Value("${spring.rabbitmq.queues.email}")
    private String queue;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostMapping(value = "/swagger/sending-email", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Send Email")
    @ApiResponses({
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "404", description = "BAD REQUEST",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = "BAD REQUEST"))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = "Internal Server Error")))
    })
    public ResponseEntity<Object> sendingEmail(
            @RequestParam("ownerRef") @NotBlank String ownerRef,
            @RequestParam("emailFrom") @NotBlank @Email String emailFrom,
            @RequestParam("emailTo") @NotBlank @Email String emailTo,
            @RequestParam(value = "copy", required = false) @Email String copy,
            @RequestParam("subject") @NotBlank String subject,
            @RequestParam("text") @NotBlank String text,
            @RequestParam(value = "attachment", required = false) MultipartFile attachment
    ) throws ValidateException {
        EmailDto emailDto = new EmailDto();
        emailDto.setOwnerRef(ownerRef);
        emailDto.setEmailFrom(emailFrom);
        emailDto.setEmailTo(emailTo);
        emailDto.setCopy(copy);
        emailDto.setSubject(subject);
        emailDto.setText(text);

        try {
            emailDto.validate();
            if (attachment != null && !attachment.isEmpty()) {
                emailDto.setAttachmentBytes(attachment.getBytes());
                String attachmentFileName = attachment.getOriginalFilename();
                emailDto.setAttachmentName(attachmentFileName);
                rabbitTemplate.convertAndSend(queue, emailDto);
            } else {
                rabbitTemplate.convertAndSend(queue, emailDto.convertToEmailModel());
            }

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing email");
        } catch (ValidateException e) {
            if (e.getMessage().contains("BAD REQUEST")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
            throw e;
        }
    }
}
