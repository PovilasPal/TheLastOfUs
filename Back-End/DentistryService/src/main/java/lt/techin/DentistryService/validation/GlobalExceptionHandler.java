package lt.techin.DentistryService.validation;

import lt.techin.DentistryService.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.nio.file.ProviderNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
    );

    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler(ProviderNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleProviderNotFound(ProviderNotFoundException ex) {
    return buildErrorResponse(ex, HttpStatus.NOT_FOUND, "Provider not found");
  }

  @ExceptionHandler(EmployeeService.EmployeeLicenceConflictException.class)
  public ResponseEntity<ErrorResponse> handleLicenceConflict(EmployeeService.EmployeeLicenceConflictException ex) {
    return buildErrorResponse(ex, HttpStatus.CONFLICT, "Licence number conflict");
  }


  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
    return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(new ErrorResponse(
                    HttpStatus.FORBIDDEN.value(),
                    ex.getMessage(),
                    "Authorization failed"
            ));
  }

  private ResponseEntity<ErrorResponse> buildErrorResponse(
          RuntimeException ex,
          HttpStatus status,
          String errorType) {
    return ResponseEntity
            .status(status)
            .body(new ErrorResponse(
                    status.value(),
                    ex.getMessage(),
                    errorType
            ));
  }

  // Add this record inside the same file
  public record ErrorResponse(
          int status,
          String message,
          String errorType
  ) {
  }
}
