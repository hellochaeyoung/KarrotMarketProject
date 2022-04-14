package com.project.karrot.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    EMAIL_DUPLICATION(400, "MEMBER-EMAIL-ERROR-400", "EMAIL DUPLICATED"),
    NICKNAME_DUPLICATION(400, "MEMBER-NICKNAME-ERROR-400", "NICKNAME DUPLICATED");

    private int status;
    private String errorCode;
    private String message;

}
