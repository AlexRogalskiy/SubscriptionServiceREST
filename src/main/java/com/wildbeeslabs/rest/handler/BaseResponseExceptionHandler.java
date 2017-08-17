package com.wildbeeslabs.rest.handler;

import com.wildbeeslabs.rest.exception.BadRequestException;
import com.wildbeeslabs.rest.exception.EmptyContentException;
import com.wildbeeslabs.rest.exception.ResourceAlreadyExistException;
import com.wildbeeslabs.rest.exception.ResourceNotFoundException;
import com.wildbeeslabs.rest.exception.ServiceException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class BaseResponseExceptionHandler extends ResponseEntityExceptionHandler {

    public enum ResponseStatusCode {
        ALREADY_EXIST(101),
        BAD_REQUEST(102),
        NOT_FOUND(103),
        EMPTY_CONTENT(104),
        SERVICE_ERROR(105);

        private final Integer value;

        private ResponseStatusCode(final Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    protected class ExceptionEntity {

        private String url;
        private Integer code;
        private String message;

        public ExceptionEntity(final String url, final Integer code, final String message) {
            this.url = url;
            this.code = code;
            this.message = message;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(final String url) {
            this.url = url;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(final Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(final String message) {
            this.message = message;
        }
    }

    /**
     * Default Logger instance
     */
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @ExceptionHandler({ResourceAlreadyExistException.class})
    protected ResponseEntity<?> handle(final HttpServletRequest req, final ResourceAlreadyExistException ex) {
        LOGGER.error(ex.getMessage());
        String url = req.getRequestURL().toString();
        return new ResponseEntity<>(new ExceptionEntity(url, ResponseStatusCode.ALREADY_EXIST.getValue(), ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({BadRequestException.class})
    protected ResponseEntity<?> handle(final HttpServletRequest req, final BadRequestException ex) {
        LOGGER.error(ex.getMessage());
        String url = req.getRequestURL().toString();
        return new ResponseEntity<>(new ExceptionEntity(url, ResponseStatusCode.BAD_REQUEST.getValue(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    protected ResponseEntity<?> handle(final HttpServletRequest req, final ResourceNotFoundException ex) {
        LOGGER.error(ex.getMessage());
        String url = req.getRequestURL().toString();
        return new ResponseEntity<>(new ExceptionEntity(url, ResponseStatusCode.NOT_FOUND.getValue(), ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EmptyContentException.class})
    protected ResponseEntity<?> handle(final HttpServletRequest req, final EmptyContentException ex) {
        LOGGER.error(ex.getMessage());
        String url = req.getRequestURL().toString();
        return new ResponseEntity<>(new ExceptionEntity(url, ResponseStatusCode.EMPTY_CONTENT.getValue(), ex.getMessage()), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler({ServiceException.class})
    protected ResponseEntity<?> handleConflict(RuntimeException ex, WebRequest request) {
        LOGGER.error(ex.getMessage());
        return handleExceptionInternal(ex, new ExceptionEntity(null, ResponseStatusCode.SERVICE_ERROR.getValue(), ex.getMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
