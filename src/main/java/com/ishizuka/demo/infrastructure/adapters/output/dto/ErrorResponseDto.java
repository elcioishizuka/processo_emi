package com.ishizuka.demo.infrastructure.adapters.output.dto;

public record ErrorResponseDto(String message, long timestamp, String path, String status, int httpStatus) {
}
