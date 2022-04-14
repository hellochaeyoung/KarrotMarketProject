package com.project.karrot.common.exception.exceptions;

import com.project.karrot.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class EmailDuplicatedException extends RuntimeException{

    private ErrorCode errorCode;

    public EmailDuplicatedException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
