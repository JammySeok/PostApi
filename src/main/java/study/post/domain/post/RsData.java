package study.post.domain.post;

public record RsData<T> (
        String resCode,
        String msg,
        T data
) {}
