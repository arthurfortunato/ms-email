package com.ms.email.models;

import com.ms.email.enums.StatusEmail;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class EmailModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String ownerRef;
    private String emailFrom;
    private String emailTo;
    private String subject;
    private String copy;
    private byte[] attachmentBytes;
    private String text;
    private LocalDateTime sendDateEmail;
    private StatusEmail statusEmail;
    private String attachmentName;
}
