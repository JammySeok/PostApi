package study.post.golbal.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import study.post.domain.post.RsData;

import java.util.Comparator;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RsData<Object>> handleValidationExceptions(MethodArgumentNotValidException e) {

        // 필드 에러처리 탬플릿
        String errorMsg = e.getBindingResult()
                .getAllErrors()  // getFiledErrors() 쓰면 FiledError만 가져옴
                .stream()
                .filter(error -> error instanceof FieldError)
                .map(error -> (FieldError) error)
                .map(error -> error.getField() + "-" + error.getCode() + "-" + error.getDefaultMessage())
                .sorted(Comparator.comparing(String::toString))
                .collect(Collectors.joining("\n"));

        RsData<Object> rsData = new RsData<>("F-valid", errorMsg, null);
        return ResponseEntity.status(400).body(rsData);
    }

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

        RsData<Object> rsData = new RsData<>("F-no-user", e.getMessage(), null);
        return ResponseEntity.status(404).body(rsData);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<RsData<Object>> handlePostNotFoundException(PostNotFoundException e) {
        log.warn("PostNotFoundException: {}", e.getMessage());

        RsData<Object> rsData = new RsData<>("F-no-post", e.getMessage(), null);
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
        log.error("알 수 없는 런타임 에러 발생: ", e);
        return ResponseEntity.status(500).body(new RsData<>("F-500", "서버 내부 오류가 발생했습니다.", null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RsData<Object>> handleException(Exception e) {
        log.error("미처 처리하지 못한 에러 발생: ", e);
        return ResponseEntity.status(500).body(new RsData<>("F-999", "시스템 오류입니다.", null));
    }
}
