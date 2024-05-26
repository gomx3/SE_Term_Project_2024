package SE_team.IssueManager.payload.exception;

import SE_team.IssueManager.payload.ApiResponse;
import SE_team.IssueManager.payload.code.ErrorReasonDto;
import SE_team.IssueManager.payload.code.status.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    private final View error;

    public GlobalExceptionHandler(View error) {
        this.error = error;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<Object> validation(ConstraintViolationException e , WebRequest request) {
        String errorMessage=e.getConstraintViolations().stream()
                .map(constraintViolation -> constraintViolation.getMessage())
                .findFirst()
                .orElseThrow(()->new RuntimeException("ConstraintViolation 추출 중 에러"));
        return handleExceptionInternalConstraint(e,ErrorStatus.valueOf(errorMessage),HttpHeaders.EMPTY,request);
    }

    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, String> errors = new LinkedHashMap<>();

        e.getBindingResult().getFieldErrors().stream()
                .forEach(fieldError -> {
                    String fieldName = fieldError.getField();
                    String errorMessage = Optional.ofNullable(fieldError.getDefaultMessage()).orElse("");
                    errors.merge(fieldName, errorMessage, (existingErrorMessage, newErrorMessage) -> existingErrorMessage + ", " + newErrorMessage);
                });

        return handleExceptionInternalArgs(e,ErrorStatus.valueOf("_BAD_REQUEST"),HttpHeaders.EMPTY,request,errors);
    }

//    @org.springframework.web.bind.annotation.ExceptionHandler
//    public ResponseEntity<Object> exception(Exception e, WebRequest request) {
//        e.printStackTrace();
//
//        return handleExceptionInternalFalse(e, ErrorStatus._INTERNAL_SERVER_ERROR, HttpHeaders.EMPTY, ErrorStatus._INTERNAL_SERVER_ERROR.getHttpStatus(),request, e.getMessage());
//    }
    @ExceptionHandler(value = GeneralException.class)
    public ResponseEntity onThrowException(GeneralException generalException, HttpServletRequest request) {
        ErrorReasonDto errorReasonHttpStatus = generalException.getErrorReasonHttpStatus();
        return handleExceptionInternal(generalException,errorReasonHttpStatus,null,request);
    }
    private ResponseEntity<Object> handleExceptionInternal(Exception e, ErrorReasonDto reason,
                                                           HttpHeaders headers, HttpServletRequest request) {

        ApiResponse<Object> body = ApiResponse.onFailure(reason.getCode(),reason.getMessage(),null);

        WebRequest webRequest = new ServletWebRequest(request);
        return super.handleExceptionInternal(
                e,
                body,
                headers,
                reason.getHttpStatus(),
                webRequest
        );
    }


    private ResponseEntity<Object> handleExceptionInternalConstraint(Exception e, ErrorStatus errorStatus, HttpHeaders headers,WebRequest request) {
        ApiResponse<Object> body= ApiResponse.onFailure(errorStatus.getCode(),errorStatus.getMessage(),null);
        return super.handleExceptionInternal(e,body,headers,errorStatus.getHttpStatus(),request);
    }
    private ResponseEntity<Object> handleExceptionInternalArgs(Exception e,ErrorStatus errorStatus, HttpHeaders headers,WebRequest request, Map<String,String> errorArgs ){
        ApiResponse<Object> body = ApiResponse.onFailure(errorStatus.getCode(),errorStatus.getMessage(),errorArgs);
        return super.handleExceptionInternal(
                e,
                body,
                headers,
                errorStatus.getHttpStatus(),
                request
        );
    }
}
