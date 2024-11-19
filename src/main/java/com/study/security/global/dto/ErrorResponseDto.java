package com.study.security.global.dto;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

public record ErrorResponseDto(
    HttpStatus status,
    String message,
    LocalDateTime errorDateTime
) {

}
