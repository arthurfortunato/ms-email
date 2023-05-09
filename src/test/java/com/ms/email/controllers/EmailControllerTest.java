package com.ms.email.controllers;

import com.ms.email.dtos.EmailDto;
import com.ms.email.exceptions.ValidateException;
import com.ms.email.services.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailControllerTest {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EmailController emailController;

    @Test
    public void shouldSendingEmail() throws ValidateException {
        EmailDto emailDto = new EmailDto();
        emailDto.setOwnerRef("owner");
        emailDto.setEmailFrom("from@test.com");
        emailDto.setEmailTo("to@test.com");
        emailDto.setSubject("Test email");
        emailDto.setText("This is a test email");

        ResponseEntity<Object> responseEntity = emailController.sendingEmail(emailDto, null);
        assert responseEntity.getStatusCode() == HttpStatus.CREATED;
        verify(emailService, times(1)).sendEmail(eq(emailDto.convertToEmailModel()), eq(null), eq(3));
    }
}
