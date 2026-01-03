package study.post.domain.post.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import study.post.domain.post.member.Member;
import study.post.domain.post.member.MemberRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class ApiV1PostControllerTest {

    @Autowired MockMvc mvc;
    @Autowired PostRepository postRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired ObjectMapper objectMapper;

    private String apiKey;

    @BeforeEach
    void setup() {
        apiKey = createInit();
    }


    @Test
    @DisplayName("글 다건 조회")
    void t1() throws Exception {

        ResultActions resultActions = mvc
                .perform(
                        get("/api/v1/posts")
//                                .contentType()
//                                .content()
                )
                .andDo(print());

        List<Post> post = postRepository.findAll();

        // 필수 검증
        resultActions
                .andExpect(status().isOk());

        // 선택적 검증
//        Assertions.assertThat(~~).equals(~~);

    }

    @Test
    @DisplayName("글 단건조회")
    void 게시판_글_단건조회() throws Exception {

        long targetId = 1;

        ResultActions resultActions = mvc
                .perform(
                        get("/api/v1/posts/%d".formatted(targetId))
                )
                .andDo(print());

        Post post = postRepository.findById(targetId).get();

        resultActions
                // 응답 상태 체크
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(ApiV1PostController.class))
                .andExpect(handler().methodName("getPost"))
                // json 응답 데이터 체크
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.writer").value("유저1"))
                .andExpect(jsonPath("$.title").value("제목1"))
                .andExpect(jsonPath("$.content").value("내용1"))
                .andExpect(jsonPath("$.date").value(post.getUpdateAt().toString()));
    }

    @Test
    @DisplayName("글 단건조회 - 존재하지 않는 글")
    void 게시판_글_단건조회_존재하지_않는() throws Exception {

        long targetId = Integer.MAX_VALUE;

        ResultActions resultActions = mvc
                .perform(
                        get("/api/v1/posts/%d".formatted(targetId))
                )
                .andDo(print());

        resultActions
                // 응답 상태 체크
                .andExpect(status().isNotFound())
                .andExpect(handler().handlerType(ApiV1PostController.class))
                .andExpect(handler().methodName("getPost"));
    }

    @Test
    @DisplayName("글 작성")
    void 게시판_글_작성() throws Exception {

        Map<String, Object> testBody = Map.of(
                "title",  "제목",
                "content", "내용"
        );

        String jsonType = objectMapper.writeValueAsString(testBody);

        ResultActions resultActions = mvc
                .perform(
                        post("/api/v1/posts")
                                .header("Authorization", "Bearer " + apiKey)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonType)
                )
                .andDo(print());

        resultActions.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("글 작성, 제목이 입력되지 않음")
    void 게시판_글_작성_제목입력안함() throws Exception {

        Map<String, Object> testBody = Map.of(
                "title",  "",
                "content", "내용"
        );

        String jsonType = objectMapper.writeValueAsString(testBody);

        ResultActions resultActions = mvc
                .perform(
                        post("/api/v1/posts")
                                .header("Authorization", "Bearer " + apiKey)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonType)
                )
                .andDo(print());

        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(handler().handlerType(ApiV1PostController.class))
                .andExpect(handler().methodName("writePost"))
                .andExpect(jsonPath("$.resCode").value("F-valid"));
    }


    @Test
    @DisplayName("글 작성 - 인증안됨")
    void 게시판_글_작성_인증안됨() throws Exception {

        Map<String, Object> testBody = Map.of(
                "title",  "제목",
                "content", "내용"
        );
        String jsonType = objectMapper.writeValueAsString(testBody);

        ResultActions resultActions = mvc
                .perform(
                        post("/api/v1/posts")
                                .header("Authorization", "Bearer ")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonType)
                )
                .andDo(print());

        resultActions.andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("글 작성 - 인증안됨(beare)")
    void 게시판_글_작성_인증안됨_Beare() throws Exception {

        Map<String, Object> testBody = Map.of(
                "title",  "제목",
                "content", "내용"
        );

        String jsonType = objectMapper.writeValueAsString(testBody);

        ResultActions resultActions = mvc
                .perform(
                        post("/api/v1/posts")
                                .header("Authorization", "", apiKey)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonType)
                )
                .andDo(print());

        resultActions.andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("글 작성 - 인증안됨-auth")
    void 게시판_글_작성_인증안됨_auth() throws Exception {

        Map<String, Object> testBody = Map.of(
                "title",  "제목",
                "content", "내용"
        );

        String jsonType = objectMapper.writeValueAsString(testBody);

        ResultActions resultActions = mvc
                .perform(
                        post("/api/v1/posts")
                                .header("Auth", "Bearer ", apiKey)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonType)
                )
                .andDo(print());

        resultActions.andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("글 삭제")
    void 게시판_글_삭제() throws Exception {

        Member member = memberRepository.findById(1L).get();

        ResultActions resultActions = mvc
                .perform(
                        delete("/api/v1/posts/%d".formatted(1L))
                                .header("Authorization", "Bearer " + member.getApiKey())
                )
                .andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(ApiV1PostController.class))
                .andExpect(handler().methodName("delete"))
                .andExpect(jsonPath("resCode").value("S-delete-post"))
                .andExpect(jsonPath("msg").value("게시글 삭제 성공"));

        Optional<Post> post = postRepository.findById(1L);
        Assertions.assertThat(post).isEmpty();
    }



    // 유저 생성 메서드
    String createInit() {
        String apiKey = UUID.randomUUID().toString();

        Member testMem = new Member();
        testMem.setApiKey(apiKey);
        testMem.setUserid("test");
        testMem.setPassword("test");
        testMem.setNickname("test");
        memberRepository.save(testMem);

        return apiKey;
    }

}