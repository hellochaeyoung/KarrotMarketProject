package com.project.karrot.common.exception.exceptions;

import com.project.karrot.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class NickNameDuplicatedException extends RuntimeException{

    private ErrorCode errorCode;

    public NickNameDuplicatedException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
