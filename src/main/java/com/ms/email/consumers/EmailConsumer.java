package com.ms.email.consumers;

import com.ms.email.dtos.EmailDto;
import com.ms.email.exceptions.ValidateException;
import com.ms.email.services.EmailService;
import com.ms.email.services.EmailWelcomeService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    private final EmailService emailService;
    private final EmailWelcomeService emailWelcomeService;

    public EmailConsumer(EmailService emailService, EmailWelcomeService emailWelcomeService) {
        this.emailService = emailService;
        this.emailWelcomeService = emailWelcomeService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queues.email}")
    public void listen(@Payload EmailDto emailDto) throws ValidateException {
        emailService.sendEmail(emailDto.convertToEmailModel(), 3);
    }

    @RabbitListener(queues = "${spring.rabbitmq.queues.welcome}")
    public void listenWelcome(@Payload EmailDto emailDto) throws ValidateException {
        emailWelcomeService.sendEmail(emailDto.convertToEmailModel(), 3);
    }
}