package com.ms.email.services;

import com.ms.email.enums.StatusEmail;
import com.ms.email.exceptions.ValidateException;
import com.ms.email.models.EmailModel;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private JavaMailSender emailSender;

    @Test
    @DisplayName("Test send email successfully")
    public void shouldSendEmail() throws ValidateException {
        EmailModel emailModel = new EmailModel();
        emailModel.setEmailFrom("test@test.com");
        emailModel.setEmailTo("recipient@test.com");
        emailModel.setSubject("Test email");
        emailModel.setText("This is a test email");

        MimeMessage mimeMessage = mock(MimeMessage.class);

        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
        emailService.sendEmail(emailModel, "testAttachment.txt", 3);

        verify(emailSender, times(1)).send(mimeMessage);
        assert (emailModel.getStatusEmail() == StatusEmail.SENT);
    }

    @Test
    public void shouldThrowNullPointerException() {
        EmailModel emailModel = new EmailModel();
        emailModel.setEmailFrom(null);
        emailModel.setEmailTo(null);
        emailModel.setSubject(null);
        emailModel.setText(null);

        assertThrows(NullPointerException.class, () -> emailService.sendEmail(emailModel, "testAttachment.txt", 3));
        verify(emailSender, never()).send(any(MimeMessage.class));
    }
}
