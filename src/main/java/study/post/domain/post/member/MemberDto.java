package study.post.domain.post.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberDto {

    private Long id;
    private String userid;
    private String nickname;
    private LocalDateTime createAt;

    @JsonIgnore
    private String apiKey;
}
