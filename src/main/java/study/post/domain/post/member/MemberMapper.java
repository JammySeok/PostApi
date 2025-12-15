package study.post.domain.post.member;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    @Mapping(source = "createAt", target = "createAt")
    MemberDto toDto(Member entity);
    Member toEntity(MemberDto dto);
}
