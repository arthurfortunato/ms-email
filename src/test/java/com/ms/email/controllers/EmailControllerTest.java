package com.ms.email.controllers;

import com.ms.email.dtos.EmailDto;
import com.ms.email.exceptions.ValidateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailControllerTest {

    @InjectMocks
    private EmailController emailController;

    @Mock
    RabbitTemplate rabbitTemplate;

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
        verify(rabbitTemplate, times(1)).convertAndSend(null, emailDto.convertToEmailModel());
    }
}
