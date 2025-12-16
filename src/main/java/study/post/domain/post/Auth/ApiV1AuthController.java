package study.post.domain.post.Auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.post.domain.post.RsData;
import study.post.domain.post.member.MemberService;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ApiV1AuthController {

    private final AuthService authService;
    private final MemberService memberService;

    record LoginReqBody(String userid, String password) {}
    record SignReqBody(String userid, String password, String nickname) {}

    @PostMapping("/login")
    public ResponseEntity<RsData<Map<String, String>>> login(@RequestBody LoginReqBody reqBody) {

        authService.login(reqBody.userid, reqBody.password);

        Map<String, String> apiKey = new HashMap<>();
        String apiValue = memberService.findApiKey(reqBody.userid);
        apiKey.put("apiKey", apiValue);

        RsData<Map<String, String>> rsData = new RsData<>("S-login", "로그인 성공", apiKey);
        return ResponseEntity.status(200).body(rsData);
    }

    @PostMapping("/signup")
    public ResponseEntity<RsData<Void>> signup(@RequestBody SignReqBody reqBody) {

        authService.signup(reqBody.userid,reqBody.password, reqBody.nickname);

        RsData<Void> rsData = new RsData<>("S-signup", "회원가입 완료", null);
        return ResponseEntity.status(200).body(rsData);
    }



    //    @PostMapping("/login")
    public ResponseEntity<RsData<Void>> loginV1(@RequestBody LoginReqBody reqBody) {

        authService.login(reqBody.userid, reqBody.password);

        RsData<Void> rsData = new RsData<>("S-login", "로그인 성공", null);
        return ResponseEntity.status(200).body(rsData);
    }
}
