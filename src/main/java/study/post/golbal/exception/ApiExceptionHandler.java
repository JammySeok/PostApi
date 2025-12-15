package study.post.golbal.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import study.post.domain.post.RsData;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(LoginFailException.class)
    public ResponseEntity<RsData<Object>> handleLoginFailedException(LoginFailException e) {
        log.warn("Login Failed: {}", e.getMessage());

        RsData<Object> rsData = new RsData<>("F-auth-login", e.getMessage(), null);
        return ResponseEntity.status(401).body(rsData);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<RsData<Object>> handleDuplicateUserException(DuplicateUserException e) {
        log.warn("Duplicate User: {}", e.getMessage());

        RsData<Object> rsData = new RsData<>("F-auth-signup", e.getMessage(), null);
        return ResponseEntity.status(409).body(rsData);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<RsData<Object>> handleUserNotFoundException(UserNotFoundException e) {
        log.warn("UserNotFoundException: {}", e.getMessage());

        RsData<Object> rsData = new RsData<>("F-write", e.getMessage(), null);
        return ResponseEntity.status(404).body(rsData);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<RsData<Object>> handleAuthorizationException(AuthorizationException e) {
        log.warn("Authentication Exception: {}", e.getMessage());

        RsData<Object> rsData = new RsData<>("F-auth", e.getMessage(), null);
        return ResponseEntity.status(403).body(rsData);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<RsData<Object>> handleRuntimeException(RuntimeException e) {
        log.warn("Runtime Exception: {}", e.getMessage());
        RsData<Object> rsData = new RsData<>("F-SERVER", "알수 없는 오류가 발생함", null);

        return ResponseEntity.status(500).body(rsData);
    }
}
