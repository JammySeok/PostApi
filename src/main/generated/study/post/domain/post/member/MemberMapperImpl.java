package study.post.domain.post.member;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-16T12:29:05+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
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
        memberDto.setPassword( entity.getPassword() );
        memberDto.setNickname( entity.getNickname() );

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
        member.setPassword( dto.getPassword() );
        member.setNickname( dto.getNickname() );
        member.setCreateAt( dto.getCreateAt() );

        return member;
    }
}
