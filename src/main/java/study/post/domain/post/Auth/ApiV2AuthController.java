package study.post.domain.post.Auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.post.domain.post.RsData;
import study.post.domain.post.member.MemberService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2")
public class ApiV2AuthController {

    private final AuthService authService;
    private final MemberService memberService;

    record LoginReqBody(String userid, String password) {}
    record SignReqBody(String userid, String password, String nickname) {}

    @PostMapping("/login")
    public ResponseEntity<RsData<String>> login(@RequestBody LoginReqBody reqBody) {

        authService.login(reqBody.userid, reqBody.password);
        String apiKey = memberService.findApiKey(reqBody.userid);

        RsData<String> rsData = new RsData<>("S-login", "로그인 성공", "apiKey: " + apiKey);
        return ResponseEntity.status(200).body(rsData);
    }

    @PostMapping("/signup")
    public ResponseEntity<RsData<Void>> signup(@RequestBody SignReqBody reqBody) {

        authService.signup(reqBody.userid,reqBody.password, reqBody.nickname);

        RsData<Void> rsData = new RsData<>("S-signup", "회원가입 완료", null);
        return ResponseEntity.status(200).body(rsData);
    }
}
