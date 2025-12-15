package study.post.golbal.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException (String msg) {
        super(msg);
    }
}
