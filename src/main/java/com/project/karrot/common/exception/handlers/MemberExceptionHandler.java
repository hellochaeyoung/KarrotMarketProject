package com.project.karrot.common.exception.handlers;

import com.project.karrot.common.exception.ErrorResponse;
import com.project.karrot.common.exception.exceptions.EmailDuplicatedException;
import com.project.karrot.common.exception.exceptions.NickNameDuplicatedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MemberExceptionHandler {

    @ExceptionHandler(EmailDuplicatedException.class)
    public ResponseEntity<?> handleEmailDuplicatedException(EmailDuplicatedException ex) {
        log.error("handleEmailDuplicatedException", ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(NickNameDuplicatedException.class)
    public ResponseEntity<?> handleNickNameDuplicatedException(NickNameDuplicatedException ex) {
        log.error("handleNicknameDuplicatedException", ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
}
