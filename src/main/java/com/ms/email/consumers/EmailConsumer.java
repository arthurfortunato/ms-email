package com.ms.email.consumers;

import com.ms.email.dtos.EmailDto;
import com.ms.email.exceptions.ValidateException;
import com.ms.email.services.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    private final EmailService emailService;

    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listen(@Payload EmailDto emailDto) throws ValidateException {
        emailService.sendEmail(emailDto.convertToEmailModel(), 3);
    }
}