package study.post.domain.post.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.post.golbal.exception.UserNotFoundException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    public Long count() {
        return memberRepository.count();
    }

    public MemberDto findById (Long id) {
        return memberRepository.findById(id)
                .map(memberMapper :: toDto)
                .orElseThrow(() -> new UserNotFoundException("유저를 찾을 수 없음"));
    }

    public List<MemberDto> findAll() {
        return memberRepository.findAll().stream()
                .map(memberMapper :: toDto)
                .toList();
    }

    public MemberDto myPage(MemberDto dto) {
        Member actor = memberRepository.findById(dto.getId()).orElseThrow(() -> new UserNotFoundException("유저를 찾을 수 없음"));
        return memberMapper.toDto(actor);
    }
}
