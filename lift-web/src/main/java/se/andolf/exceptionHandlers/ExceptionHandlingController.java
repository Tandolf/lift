package se.andolf.exceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import se.andolf.model.MessageEntity;
import se.andolf.exceptions.NodeExistsException;
import se.andolf.exceptions.NodeNotFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Thomas on 2016-07-09.
 */
@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NodeNotFoundException.class)
    public ResponseEntity<?> nodeNotFound(HttpServletRequest request, Throwable ex) {
        return new ResponseEntity<>(new MessageEntity(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NodeExistsException.class)
    public ResponseEntity<?> nodeExists(HttpServletRequest request, Throwable ex) {
        return new ResponseEntity<>(new MessageEntity(ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> clientError(HttpServletRequest request, Throwable ex) {
        return new ResponseEntity<>(new MessageEntity(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
