package study.post.domain.post.member;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberDto {

    private Long id;
    private String userid;
    private String password;
    private String nickname;
    private LocalDateTime createAt;
}
