package study.post.domain.post.Comment;

import java.time.LocalDateTime;

public record CommentDto (
        Long id,
        String writer,
        String content,
        LocalDateTime date) { }
