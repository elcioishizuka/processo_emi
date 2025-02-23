package com.ishizuka.demo.infrastructure.adapters.output;

import com.ishizuka.demo.domain.exception.DebitCancelAlreadyInQueueOrProcessedException;
import com.ishizuka.demo.domain.exception.FailedToSendMessageToQueueException;
import com.ishizuka.demo.domain.model.DebitStatus;
import com.ishizuka.demo.domain.model.ResponseWrapper;
import com.ishizuka.demo.infrastructure.adapters.output.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(DebitCancelAlreadyInQueueOrProcessedException.class)
    public ResponseWrapper<ErrorResponseDto> handleDebitCancelAlreadyInQueueOrProcessedException(DebitCancelAlreadyInQueueOrProcessedException e,
                                                                                                 ServerHttpRequest request) {

        ErrorResponseDto error = new ErrorResponseDto(e.getMessage(), Instant.now().toEpochMilli(),
                request.getPath().toString(), DebitStatus.FAILED.toString(), HttpStatus.UNPROCESSABLE_ENTITY.value());

        return new ResponseWrapper<>(error);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {FailedToSendMessageToQueueException.class, IllegalArgumentException.class, Exception.class})
    public ResponseWrapper<ErrorResponseDto> handleOtherException(Exception e, ServerHttpRequest request) {

        ErrorResponseDto error = new ErrorResponseDto(e.getMessage(), Instant.now().toEpochMilli(),
                request.getPath().toString(), DebitStatus.FAILED.name(), HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseWrapper<>(error);
    }
}
