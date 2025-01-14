
package za.co.medici.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import za.co.medici.dto.GenericResponseDetail;
import za.co.medici.uitls.Constants;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponseDetail<?>> handleException(Exception e) {
        logger.warn("[{}] [{}] [handleException()] an exception occurred with this message {}", Constants.SERVICE_NAME, Constants.ERROR, e.getMessage());
        GenericResponseDetail<?> responseDetail = this.buildResponseDetail(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDetail);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GenericResponseDetail<?>> handleIllegalArgumentException(IllegalArgumentException e) {
        logger.warn("[{}] [{}] [handleIllegalArgumentException()] an exception occurred with this message {}", Constants.SERVICE_NAME, Constants.WARNING, e.getMessage());
        GenericResponseDetail<?> responseDetail = this.buildResponseDetail(String.valueOf(HttpStatus.BAD_REQUEST.value()), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDetail);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<GenericResponseDetail<?>> handleInvalidIdException(IllegalStateException e) {
        logger.warn("[{}] [{}] [handleInvalidIdException()] an Illegal State Exception occurred with this message {}", Constants.SERVICE_NAME, Constants.WARNING, e.getMessage());
        GenericResponseDetail<?> responseDetail = this.buildResponseDetail(String.valueOf(HttpStatus.BAD_REQUEST.value()), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDetail);
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<GenericResponseDetail<?>> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.warn("[{}] [{}] [handleMissingServletRequestParameterException()] An Exception occurred with this message {}", Constants.SERVICE_NAME, Constants.WARNING, e.getMessage());
        GenericResponseDetail<?> responseDetail = this.buildResponseDetail(String.valueOf(400), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDetail);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<GenericResponseDetail<?>> handleUserException(UserException e) {
        logger.warn("[{}] [{}] [handleUserException()] An User Exception occurred with this message {}", Constants.SERVICE_NAME, Constants.WARNING, e.getMessage());
        GenericResponseDetail<?> responseDetail = this.buildResponseDetail(String.valueOf(400), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDetail);
    }

    private GenericResponseDetail buildResponseDetail(String status, String message) {
        return GenericResponseDetail.builder()
                .error(message)
                .status(status)
                .data("")
                .build();
    }
}
