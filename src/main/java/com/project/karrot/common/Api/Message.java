package com.project.karrot.common.Api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message<T> {

    private int httpStatus;
    private T data;

    @SuppressWarnings("unchecked")
    public static <T> Message<T> OK() {
        return (Message<T>) Message.builder()
                .httpStatus(200)
                .build();
    }

    @SuppressWarnings("unchecked")
    public static <T> Message<T> OK(T data, HttpStatus httpStatus) {
        return (Message<T>) Message.builder()
                .data(data)
                .httpStatus(httpStatus.value())
                .build();
    }

    @SuppressWarnings("unchecked")
    public static <T> Message<T> ERROR() {
        return (Message<T>) Message.builder()
                .httpStatus(400)
                .build();
    }

    public ResponseEntity<?> reponseBuild() {
        return ResponseEntity
                .status(this.httpStatus)
                .body(data);
    }
}
