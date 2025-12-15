package study.post.domain.post.post;

import lombok.Data;
import study.post.domain.post.Comment.CommentDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDto {

    private Long id;
    private String writer;
    private String title;
    private String content;

    private LocalDateTime date;

    private List<CommentDto> comments;
}
