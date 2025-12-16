package study.post.golbal.InitData;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.post.domain.post.Auth.AuthService;
import study.post.domain.post.Comment.CommentService;
import study.post.domain.post.member.MemberService;
import study.post.domain.post.post.PostService;

@RequiredArgsConstructor
@Configuration
public class PostInit {

    private final MemberService memberService;
    private final AuthService authService;
    private final PostService postService;
    private final CommentService commentService;

    @Bean
    public ApplicationRunner initData() {

        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {

                memberWrite();
                postWrite();
                commentWrite();
            }
        };
    }

    private void memberWrite() {
        if (memberService.count() < 1L) {
            authService.signup("user1", "1234", "유저1");
            authService.signup("user2", "1234", "유저2");
            authService.signup("user3", "1234", "유저3");
        }
    }

    private void postWrite() {
        if (postService.count() < 1L) {
            postService.writeByNickname("유저1", "제목1", "내용1");
            postService.writeByNickname("유저1", "제목2", "내용2");
            postService.writeByNickname("유저2", "제목3", "내용3");
        }
    }

    private void commentWrite() {
        if(commentService.count() < 1L) {
            commentService.write(1L, "유저1", "댓글1");
            commentService.write(2L, "유저1", "댓글2");
            commentService.write(3L, "유저2", "댓글3");
        }
    }

}
