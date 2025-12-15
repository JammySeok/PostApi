package study.post.golbal.exception;

public class LoginFailException extends RuntimeException {
    public LoginFailException (String msg) {
        super(msg);
    }
}
