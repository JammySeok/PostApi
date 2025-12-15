package study.post.domain.post.member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class ApiV1MemberController {

    private final MemberService memberService;

    @GetMapping()
    public List<MemberDto> getMembers() {
        return memberService.findAll();
    }

    @GetMapping("/{id}")
    public MemberDto getMember(@PathVariable("id") Long id) {
        return memberService.findById(id);
    }
}
