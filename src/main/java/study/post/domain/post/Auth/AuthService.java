package study.post.domain.post.Auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.post.domain.post.member.Member;
import study.post.domain.post.member.MemberDto;
import study.post.domain.post.member.MemberMapper;
import study.post.domain.post.member.MemberRepository;
import study.post.golbal.exception.DuplicateUserException;
import study.post.golbal.exception.LoginFailException;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    public MemberDto login(String userid, String password) {

        Member member = memberRepository.findByUserid(userid).orElseThrow(() -> new LoginFailException("아이디를 찾을 수 없거나 비밀번호가 일치하지 않습니다."));

        if (!member.getPassword().equals(password)) {
            throw new LoginFailException("아이디를 찾을 수 없거나 비밀번호가 일치하지 않습니다.");
        }

        return memberMapper.toDto(member);
    }

    public MemberDto signup (String userid, String password, String nickname) {

        if(memberRepository.findByUserid(userid).isPresent()) {
            throw new DuplicateUserException("이미 사용중인 아이디입니다.");
        }

        if(memberRepository.findByNickname(nickname).isPresent()) {
            throw new DuplicateUserException("이미 사용중인 닉네임입니다.");
        }

        Member member = new Member();
        member.setUserid(userid);
        member.setPassword(password);
        member.setNickname(nickname);
        member.setApiKey(UUID.randomUUID().toString());

        memberRepository.save(member);

        return memberMapper.toDto(member);
    }
}
