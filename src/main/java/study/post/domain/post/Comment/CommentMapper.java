package study.post.domain.post.Comment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "member.nickname", target = "writer")
    @Mapping(source = "createAt", target = "date")
    CommentDto toDto(Comment entity);

    Comment toEntity(CommentDto dto);
}
