package co.istad.springdatajpa.exception;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ErrorResponse(
        String status,
        Integer code,
        String message,
        Instant timestamp,
        Object errorDetail
) {

}
