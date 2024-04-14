package com.example.course_project_2023.web.exceptionHandling;

import com.example.course_project_2023.service.exception.likedislike.LikeDislikeException;
import com.example.course_project_2023.service.exception.notFound.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, request, ex, List.of(ex.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleImageResolutionValidationException(ConstraintViolationException ex, WebRequest request) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, request, ex, List.of(ex.getMessage()));
    }

    @ExceptionHandler(LikeDislikeException.class)
    public ResponseEntity<Object> handleLikeDislikeException(LikeDislikeException ex, WebRequest request) {
        return buildResponseEntity(HttpStatus.CONFLICT, request, ex, List.of(ex.getMessage()));
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               HttpHeaders headers, HttpStatusCode status,
                                                               WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return buildResponseEntity(status, headers, errors);

    }

    private ResponseEntity<Object> buildResponseEntity(HttpStatusCode status, WebRequest request, Exception ex, List<String> errors) {
        Map<String, Object> body = new LinkedHashMap<>();

        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("errors", errors);
        return handleExceptionInternal(ex, body,
                new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> buildResponseEntity(HttpStatusCode status, HttpHeaders headers, List<String> errors) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("errors", errors);
        return new ResponseEntity<>(body, headers, status);
    }
}
