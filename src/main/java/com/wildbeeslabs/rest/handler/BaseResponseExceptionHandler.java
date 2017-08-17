package com.wildbeeslabs.rest.handler;

import com.wildbeeslabs.rest.exception.BadRequestException;
import com.wildbeeslabs.rest.exception.EmptyContentException;
import com.wildbeeslabs.rest.exception.ResourceAlreadyExistException;
import com.wildbeeslabs.rest.exception.ResourceNotFoundException;
import com.wildbeeslabs.rest.exception.ServiceException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class BaseResponseExceptionHandler {// extends ResponseEntityExceptionHandler {

    public enum ResponseStatusCode {
        ALREADY_EXIST(101),
        BAD_REQUEST(102),
        NOT_FOUND(103),
        EMPTY_CONTENT(104),
        SERVICE_ERROR(105),
        BAD_MEDIA_TYPE(106);

        private final Integer value;

        private ResponseStatusCode(final Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

    protected class ExceptionEntity {

        private String path;
        private Integer code;
        private String error;
        private String message;
        private Long timestamp;

        public ExceptionEntity() {
        }

        public ExceptionEntity(final String path, final ResponseStatusCode statusCode, final String message) {
            this(path, statusCode.getValue(), statusCode.name(), message);
        }

        public ExceptionEntity(final String path, final Integer code, final String error, final String message) {
            this.path = path;
            this.code = code;
            this.error = error;
            this.message = message;
            this.timestamp = new Date().getTime();
        }

        public String getPath() {
            return path;
        }

        public void setPath(final String path) {
            this.path = path;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(final Integer code) {
            this.code = code;
        }

        public String getError() {
            return error;
        }

        public void setError(final String error) {
            this.error = error;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(final String message) {
            this.message = message;
        }

        public Long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(final Long timestamp) {
            this.timestamp = timestamp;
        }
    }

    /**
     * Default Logger instance
     */
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @ExceptionHandler({ResourceAlreadyExistException.class})
    protected ResponseEntity<?> handle(final HttpServletRequest req, final ResourceAlreadyExistException ex) {
        LOGGER.error(ex.getMessage());
        String url = req.getRequestURI().substring(req.getContentType().length());
        return new ResponseEntity<>(new ExceptionEntity(url, ResponseStatusCode.ALREADY_EXIST, ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({BadRequestException.class})
    protected ResponseEntity<?> handle(final HttpServletRequest req, final BadRequestException ex) {
        LOGGER.error(ex.getMessage());
        String url = req.getRequestURI().substring(req.getContentType().length());
        return new ResponseEntity<>(new ExceptionEntity(url, ResponseStatusCode.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    protected ResponseEntity<?> handle(final HttpServletRequest req, final ResourceNotFoundException ex) {
        LOGGER.error(ex.getMessage());
        String url = req.getRequestURI().substring(req.getContentType().length());
        return new ResponseEntity<>(new ExceptionEntity(url, ResponseStatusCode.NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EmptyContentException.class})
    protected ResponseEntity<?> handle(final HttpServletRequest req, final EmptyContentException ex) {
        LOGGER.error(ex.getMessage());
        String url = req.getRequestURI().substring(req.getContentType().length());
        return new ResponseEntity<>(new ExceptionEntity(url, ResponseStatusCode.EMPTY_CONTENT, ex.getMessage()), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler({TypeMismatchException.class})
    protected ResponseEntity<?> handle(final HttpServletRequest req, final TypeMismatchException ex) {
        LOGGER.error(ex.getMessage());
        String url = req.getRequestURI().substring(req.getContentType().length());
        return new ResponseEntity<>(new ExceptionEntity(url, ResponseStatusCode.BAD_MEDIA_TYPE, ex.getMessage()), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<?> handle(final HttpServletRequest req, final MethodArgumentNotValidException ex) {
        LOGGER.error(ex.getMessage());
        String url = req.getRequestURI().substring(req.getContentType().length());
        return new ResponseEntity<>(new ExceptionEntity(url, ResponseStatusCode.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MissingPathVariableException.class})
    protected ResponseEntity<?> handle(final HttpServletRequest req, final MissingPathVariableException ex) {
        LOGGER.error(ex.getMessage());
        String url = req.getRequestURI().substring(req.getContentType().length());
        return new ResponseEntity<>(new ExceptionEntity(url, ResponseStatusCode.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ServiceException.class})
    protected ResponseEntity<?> handle(final HttpServletRequest req, final ServiceException ex) {
        LOGGER.error(ex.getMessage());
        String url = req.getRequestURI().substring(req.getContentType().length());
        return new ResponseEntity<>(new ExceptionEntity(url, ResponseStatusCode.SERVICE_ERROR, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
