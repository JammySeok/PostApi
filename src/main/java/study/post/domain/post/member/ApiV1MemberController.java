package study.post.domain.post.member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.post.golbal.request.Rq;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class ApiV1MemberController {

    private final MemberService memberService;
    private final Rq rq;

    @GetMapping()
    public List<MemberDto> getMembers() {
        return memberService.findAll();
    }

    @GetMapping("/{id}")
    public MemberDto getMember(@PathVariable("id") Long id) {
        return memberService.findById(id);
    }

    @GetMapping("/me")
    public MemberDto getMyPage() {

        MemberDto actor = rq.getActor();
        return memberService.myPage(actor);
    }
}
