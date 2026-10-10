package org.example.segundoapinosql.adapters.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import java.util.Map;
import java.util.NoSuchElementException;
import org.example.segundoapinosql.infrastructure.exception.EntidadeNaoEncontradaException;
import org.example.segundoapinosql.infrastructure.exception.RegraProblemaException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalHandlerException {

    private final MessageSource messages;

    public GlobalHandlerException(MessageSource messages) {
        this.messages = messages;
    }

    private String text(String key) {
        return messages.getMessage(
                key,
                null,
                key,
                LocaleContextHolder.getLocale()
        );
    }

    private ResponseEntity<Map<String, String>> response(
            HttpStatus status,
            String message
    ) {
        return ResponseEntity.status(status).body(Map.of("message", message));
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Map<String, String>> notFound(
            EntidadeNaoEncontradaException exception
    ) {
        return response(HttpStatus.NOT_FOUND, text(exception.getMessage()));
    }

    @ExceptionHandler(RegraProblemaException.class)
    public ResponseEntity<Map<String, String>> rule(
            RegraProblemaException exception
    ) {
        return response(HttpStatus.FORBIDDEN, text(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> validation(
            MethodArgumentNotValidException exception
    ) {
        return ResponseEntity.badRequest().body(Map.of(
                        "message",
                exception.getBindingResult().getFieldErrors().stream()
                        .map(error -> text(error.getDefaultMessage()))
                        .toList()
        ));
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            ConstraintViolationException.class,
            MethodArgumentTypeMismatchException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<Map<String, String>> invalidRequest(Exception exception) {
        return response(HttpStatus.BAD_REQUEST, text(exception.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> integrity() {
        return response(HttpStatus.CONFLICT, text("exception.database.integrity"));
    }

    @ExceptionHandler({
            EntityNotFoundException.class,
            NoSuchElementException.class
    })
    public ResponseEntity<Map<String, String>> missingEntity() {
        return response(HttpStatus.NOT_FOUND, text("exception.entity.notFound"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> generic(Exception e) {
        return response(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
