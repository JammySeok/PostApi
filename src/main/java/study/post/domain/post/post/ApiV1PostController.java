package study.post.domain.post.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.post.domain.post.RsData;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class ApiV1PostController {

    private final PostService postService;

    record WriteResBody(String title, String content) {}
    record ModifyResBody(String title, String content) {}

    @GetMapping()
    public List<PostDto> getPosts() {
        return postService.findAll();
    }

    @GetMapping("/{id}")
    public PostDto getPost(@PathVariable("id") Long id) {
        return postService.findById(id);
    }

    @PostMapping()
    public ResponseEntity<RsData<PostDto>> writePost(@RequestBody WriteResBody writeResBody,
                                                  @RequestParam String nickname) {

        PostDto rsDto = postService.writeV1(nickname, writeResBody.title, writeResBody.content);

        RsData<PostDto> rsData = new RsData<>("S-write-post","게시글 작성 성공", rsDto);
        return ResponseEntity.status(200).body(rsData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RsData<PostDto>> modifyAll(@PathVariable("id") Long id,
                                                  @RequestBody ModifyResBody modifyResBody,
                                                  @RequestParam String nickname) {

        PostDto rsDto = postService.modifyAll(id, nickname, modifyResBody.title, modifyResBody.content);

        RsData<PostDto> rsData = new RsData<>("S-modify-post", "게시글 수정 성공", rsDto);
        return ResponseEntity.status(200).body(rsData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RsData<Void>> delete(@PathVariable("id") Long id,
                                                  @RequestParam String nickname) {

        postService.delete(id, nickname);

        RsData<Void> rsData = new RsData<>("S-delete-post", "게시글 삭제 성공", null);
        return ResponseEntity.status(200).body(rsData);
    }
}
