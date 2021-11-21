package com.cuberreality.error;

import com.cuberreality.constant.ErrorStatus;
import com.cuberreality.response.BaseResponse;
import com.cuberreality.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<BaseResponse<?>> handleUserNotFoundException(RecordNotFoundException ex,
                                                                             WebRequest request) {
        return new ResponseEntity<>(
                new BaseResponse<>(new ErrorResponse(ErrorStatus.INCORRECT_REQUEST, ex.getMessage()), ""),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OtpException.class)
    public final ResponseEntity<BaseResponse<?>> otpException(OtpException ex) {
        return new ResponseEntity<>(
                new BaseResponse<>(new ErrorResponse(ErrorStatus.BAD_REQUEST, ex.getMessage()), ""),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecordAlreadyExistException.class)
    public final ResponseEntity<BaseResponse<?>> handleAlreadyExistException(RecordAlreadyExistException ex,
                                                                             WebRequest request) {
        return new ResponseEntity<>(
                new BaseResponse<>(new ErrorResponse(ErrorStatus.RECORD_EXIST, ex.getMessage()), ""),
                HttpStatus.FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public final ResponseEntity<BaseResponse<?>> handleUserUnauthorized(UnauthorizedException ex, WebRequest request) {
        return new ResponseEntity<>(
                new BaseResponse<>(new ErrorResponse(ErrorStatus.INCORRECT_REQUEST, ex.getMessage()), ""),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MissingHeaderInfoException.class)
    public final ResponseEntity<BaseResponse<?>> handleInvalidTraceIdException(MissingHeaderInfoException ex,
                                                                               WebRequest request) {

        return new ResponseEntity<>(
                new BaseResponse<>(new ErrorResponse(ErrorStatus.BAD_REQUEST, ex.getMessage()), ""),
                HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<BaseResponse<?>> handleConstraintViolationException(ConstraintViolationException ex,
                                                                                 HttpServletRequest request) {

        return new ResponseEntity<>(
                new BaseResponse<>(new ErrorResponse(ErrorStatus.BAD_REQUEST, ex.getMessage()), ""),
                HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<BaseResponse<?>> handleServerException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
//
//        return new ResponseEntity<>(
//                new BaseResponse<>(new ErrorResponse(ErrorStatus.INTERNAL_SERVER_ERROR, ex.getMessage()), ""),
//                HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
