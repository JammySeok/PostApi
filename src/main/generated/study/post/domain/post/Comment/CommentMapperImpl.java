package study.post.domain.post.Comment;

import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import study.post.domain.post.member.Member;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-19T19:41:26+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentDto toDto(Comment entity) {
        if ( entity == null ) {
            return null;
        }

        String writer = null;
        LocalDateTime date = null;
        Long id = null;
        String content = null;

        writer = entityMemberNickname( entity );
        date = entity.getCreateAt();
        id = entity.getId();
        content = entity.getContent();

        CommentDto commentDto = new CommentDto( id, writer, content, date );

        return commentDto;
    }

    @Override
    public Comment toEntity(CommentDto dto) {
        if ( dto == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setId( dto.id() );
        comment.setContent( dto.content() );

        return comment;
    }

    private String entityMemberNickname(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        Member member = comment.getMember();
        if ( member == null ) {
            return null;
        }
        String nickname = member.getNickname();
        if ( nickname == null ) {
            return null;
        }
        return nickname;
    }
}
