package study.post.domain.post.member;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-28T16:27:37+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.17 (Oracle Corporation)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public MemberDto toDto(Member entity) {
        if ( entity == null ) {
            return null;
        }

        MemberDto memberDto = new MemberDto();

        memberDto.setCreateAt( entity.getCreateAt() );
        memberDto.setId( entity.getId() );
        memberDto.setUserid( entity.getUserid() );
        memberDto.setNickname( entity.getNickname() );
        memberDto.setApiKey( entity.getApiKey() );

        return memberDto;
    }

    @Override
    public Member toEntity(MemberDto dto) {
        if ( dto == null ) {
            return null;
        }

        Member member = new Member();

        member.setId( dto.getId() );
        member.setUserid( dto.getUserid() );
        member.setNickname( dto.getNickname() );
        member.setCreateAt( dto.getCreateAt() );
        member.setApiKey( dto.getApiKey() );

        return member;
    }
}
