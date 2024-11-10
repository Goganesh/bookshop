package com.goganesh.bookshop.webapi.client.exception;

import com.goganesh.bookshop.common.exception.NoSuchBookActionException;
import com.goganesh.bookshop.webapi.client.dto.ResponseDto;
import com.goganesh.security.exception.NoJwtTokenException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NoJwtTokenException.class, NoSuchBookActionException.class, NoSuchBookException.class,
            NoSuchReviewException.class, NoSuchAuthorException.class, NoSuchGenreException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {

        ResponseDto cardActionResponse = ResponseDto.builder()
                .result(false)
                .error(ex.getMessage())
                .build();

        return handleExceptionInternal(
                ex,
                cardActionResponse,
                new HttpHeaders(),
                HttpStatus.OK,
                request);
    }

    /*
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        Map<String, List<String>> body = new HashMap<>();
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

     */
}
