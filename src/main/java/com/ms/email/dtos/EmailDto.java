package com.ms.email.dtos;

import com.ms.email.exceptions.ExceptionCodes;
import com.ms.email.exceptions.ValidateException;
import com.ms.email.models.EmailModel;
import com.ms.email.models.ValidationError;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

@Data
public class EmailDto {

    @NotBlank
    private String ownerRef;

    @NotBlank
    @Email
    private String emailFrom;

    @NotBlank
    @Email
    private String emailTo;

    @Email
    private String copy;

    @NotBlank
    private String subject;

    private String text;

    private byte[] attachmentBytes;

    private String attachmentName;

    public EmailModel convertToEmailModel() {
        var emailModel = new EmailModel();
        BeanUtils.copyProperties(this, emailModel);
        return emailModel;
    }

    public void validate() throws ValidateException {
        if (StringUtils.isBlank(this.ownerRef)) {
            throw new ValidateException(ValidationError.getFrom(ExceptionCodes.BAD_REQUEST_OWNER));
        }
        if (StringUtils.isBlank(this.emailFrom)) {
            throw new ValidateException(ValidationError.getFrom(ExceptionCodes.BAD_REQUEST_EMAIL));
        }
        if (StringUtils.isBlank(this.emailTo)) {
            throw new ValidateException(ValidationError.getFrom(ExceptionCodes.BAD_REQUEST_EMAIL));
        }
        if (StringUtils.isBlank(this.subject)) {
            throw new ValidateException(ValidationError.getFrom(ExceptionCodes.BAD_REQUEST_SUBJECT));
        }
    }
}


