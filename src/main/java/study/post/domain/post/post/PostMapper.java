package study.post.domain.post.post;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import study.post.domain.post.Comment.CommentMapper;

@Mapper(componentModel = "spring", uses = {CommentMapper.class})
public interface PostMapper {

    @Mapping(source = "member.nickname", target = "writer")
    @Mapping(source = "updateAt", target = "date")
    PostDto toDto(Post entity);

    Post toEntity(PostDto dto);
}
