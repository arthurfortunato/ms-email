package com.ms.email.dtos;

import com.ms.email.exceptions.ExceptionCodes;
import com.ms.email.exceptions.ValidateException;
import com.ms.email.models.EmailModel;
import com.ms.email.models.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class EmailDtoTest {

    @Test
    public void shouldConvertToEmailModel() {
        EmailDto emailDto = new EmailDto();
        emailDto.setOwnerRef("ownerRef");
        emailDto.setEmailFrom("test@test.com");
        emailDto.setEmailTo("recipient@test.com");
        emailDto.setSubject("Test email");
        emailDto.setText("This is a test email");

        EmailModel emailModel = emailDto.convertToEmailModel();

        assertEquals(emailDto.getOwnerRef(), emailModel.getOwnerRef());
        assertEquals(emailDto.getEmailFrom(), emailModel.getEmailFrom());
        assertEquals(emailDto.getEmailTo(), emailModel.getEmailTo());
        assertEquals(emailDto.getSubject(), emailModel.getSubject());
        assertEquals(emailDto.getText(), emailModel.getText());
    }

    @Test
    public void shouldTestValidate() throws ValidateException {
        EmailDto emailDto = new EmailDto();

        emailDto.setOwnerRef("ownerRef");
        emailDto.setEmailFrom("test@test.com");
        emailDto.setEmailTo("recipient@test.com");
        emailDto.setSubject("Test email");
        emailDto.setText("This is a test email");

        emailDto.validate();

        assertDoesNotThrow(emailDto::validate);
    }

    @Test
    public void shouldThrowBadRequestOwner() {
        EmailDto emailDto = new EmailDto();
        emailDto.setOwnerRef("");
        emailDto.setEmailFrom("test@test.com");
        emailDto.setEmailTo("recipient@test.com");
        emailDto.setSubject("Test email");
        emailDto.setText("This is a test email");

        ValidateException exception = assertThrows(ValidateException.class, emailDto::validate);
        assertEquals(ValidationError.getFrom(ExceptionCodes.BAD_REQUEST_OWNER).getMessage(), exception.getMessage());
    }

    @Test
    public void shouldThrowBadRequestEmailFrom() {
        EmailDto emailDto = new EmailDto();
        emailDto.setOwnerRef("ownerRef");
        emailDto.setEmailFrom("");
        emailDto.setEmailTo("recipient@test.com");
        emailDto.setSubject("Test email");
        emailDto.setText("This is a test email");

        ValidateException exception = assertThrows(ValidateException.class, emailDto::validate);
        assertEquals(ValidationError.getFrom(ExceptionCodes.BAD_REQUEST_EMAIL).getMessage(), exception.getMessage());
    }

    @Test
    public void shouldThrowBadRequestEmailTo() {
        EmailDto emailDto = new EmailDto();
        emailDto.setOwnerRef("ownerRef");
        emailDto.setEmailFrom("test@test.com");
        emailDto.setEmailTo("");
        emailDto.setSubject("Test email");
        emailDto.setText("This is a test email");

        ValidateException exception = assertThrows(ValidateException.class, emailDto::validate);
        assertEquals(ValidationError.getFrom(ExceptionCodes.BAD_REQUEST_EMAIL).getMessage(), exception.getMessage());
    }

    @Test
    public void shouldThrowBadRequestSubject() {
        EmailDto emailDto = new EmailDto();
        emailDto.setOwnerRef("ownerRef");
        emailDto.setEmailFrom("test@test.com");
        emailDto.setEmailTo("recipient@test.com");
        emailDto.setSubject("");
        emailDto.setText("This is a test email");

        ValidateException exception = assertThrows(ValidateException.class, emailDto::validate);
        assertEquals(ValidationError.getFrom(ExceptionCodes.BAD_REQUEST_SUBJECT).getMessage(), exception.getMessage());
    }
}