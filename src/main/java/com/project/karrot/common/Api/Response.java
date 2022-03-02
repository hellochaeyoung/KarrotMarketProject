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
public class Response<T> {

    private int httpStatus;
    private T data;

    @SuppressWarnings("unchecked")
    public static <T> Response<T> success(T data, HttpStatus httpStatus) {
        return (Response<T>) Response.builder()
                .data(data)
                .httpStatus(httpStatus.value())
                .build();
    }

    @SuppressWarnings("unchecked")
    public static <T> Response<T> fail(HttpStatus httpStatus, String message) {
        return (Response<T>) Response.builder()
                .httpStatus(httpStatus.value())
                .data(message)
                .build();
    }

    public ResponseEntity<?> reponseBuild() {
        return ResponseEntity
                .status(this.httpStatus)
                .body(data);
    }

}
