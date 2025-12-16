package study.post.domain.post.post;

import jakarta.validation.constraints.NotBlank;
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

    record WriteResBody(String title, String content, String apiKey) {}
    record ModifyResBody(String title, String content, String apiKey) {}

    @GetMapping()
    public List<PostDto> getPosts() {
        return postService.findAll();
    }

    @GetMapping("/{id}")
    public PostDto getPost(@PathVariable("id") Long id) {
        return postService.findById(id);
    }


    @PostMapping()
    public ResponseEntity<RsData<PostDto>> writePost(@RequestBody WriteResBody writeResBody) {

        // Rq 클래스 도입, service에서 알아서 header의 apiKey 가져옴
        PostDto rsDto = postService.write(writeResBody.title, writeResBody.content);

        RsData<PostDto> rsData = new RsData<>("S-write-post","게시글 작성 성공", rsDto);
        return ResponseEntity.status(200).body(rsData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RsData<PostDto>> modifyAll(@PathVariable("id") Long id,
                                                     @RequestBody ModifyResBody modifyResBody) {

        PostDto rsDto = postService.modify(id, modifyResBody.title, modifyResBody.content);

        RsData<PostDto> rsData = new RsData<>("S-modify-post", "게시글 수정 성공", rsDto);
        return ResponseEntity.status(200).body(rsData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RsData<Void>> delete(@PathVariable("id") Long id) {

        postService.delete(id);

        RsData<Void> rsData = new RsData<>("S-delete-post", "게시글 삭제 성공", null);
        return ResponseEntity.status(200).body(rsData);
    }


    /**
     * 이전 버전들 관리
     * 아래는 사용 X
     *
     * V1 : nickname으로 인증
     * V2 : apiKey body로 넘겨 인증
     * V3 : apiKey header로 넘겨 인증
     */

    //    @PostMapping()
    public ResponseEntity<RsData<PostDto>> writePostV1(@RequestBody WriteResBody writeResBody,
                                                       @RequestParam String nickname) {

        PostDto rsDto = postService.writeByNickname(nickname, writeResBody.title, writeResBody.content);

        RsData<PostDto> rsData = new RsData<>("S-write-post","게시글 작성 성공", rsDto);
        return ResponseEntity.status(200).body(rsData);
    }

    //    @PostMapping()
    public ResponseEntity<RsData<PostDto>> writePostV2(@RequestBody WriteResBody writeResBody) {

        PostDto rsDto = postService.writeByKey(writeResBody.apiKey, writeResBody.title, writeResBody.content);

        RsData<PostDto> rsData = new RsData<>("S-write-post","게시글 작성 성공", rsDto);
        return ResponseEntity.status(200).body(rsData);
    }


    //    @PostMapping()
    public ResponseEntity<RsData<PostDto>> writePostV3(@RequestBody WriteResBody writeResBody,
                                                       @RequestHeader("Authorization") @NotBlank String apiKey) {

        String authorization = apiKey.replace("Bearer ", "");

        PostDto rsDto = postService.writeByKey(authorization, writeResBody.title, writeResBody.content);

        RsData<PostDto> rsData = new RsData<>("S-write-post","게시글 작성 성공", rsDto);
        return ResponseEntity.status(200).body(rsData);
    }

    //    @PutMapping("/{id}")
    public ResponseEntity<RsData<PostDto>> modifyAllV1(@PathVariable("id") Long id,
                                                       @RequestBody ModifyResBody modifyResBody) {

        PostDto rsDto = postService.modifyAllByKey(id, modifyResBody.apiKey, modifyResBody.title, modifyResBody.content);

        RsData<PostDto> rsData = new RsData<>("S-modify-post", "게시글 수정 성공", rsDto);
        return ResponseEntity.status(200).body(rsData);
    }

    //    @PutMapping("/{id}")
    public ResponseEntity<RsData<PostDto>> modifyAllV2(@PathVariable("id") Long id,
                                                       @RequestBody ModifyResBody modifyResBody,
                                                       @RequestParam String nickname) {

        PostDto rsDto = postService.modifyAllByNickname(id, nickname, modifyResBody.title, modifyResBody.content);

        RsData<PostDto> rsData = new RsData<>("S-modify-post", "게시글 수정 성공", rsDto);
        return ResponseEntity.status(200).body(rsData);
    }


    //    @PutMapping("/{id}")
    public ResponseEntity<RsData<PostDto>> modifyAllV3(@PathVariable("id") Long id,
                                                       @RequestBody ModifyResBody modifyResBody,
                                                       @RequestHeader("Authorization") @NotBlank String apiKey) {

        String authorization = apiKey.replace("Bearer ", "");
        PostDto rsDto = postService.modifyAllByKey(id, authorization, modifyResBody.title, modifyResBody.content);

        RsData<PostDto> rsData = new RsData<>("S-modify-post", "게시글 수정 성공", rsDto);
        return ResponseEntity.status(200).body(rsData);
    }

    //    @DeleteMapping("/{id}")
    public ResponseEntity<RsData<Void>> deleteV1(@PathVariable("id") Long id,
                                                 @RequestParam String nickname) {

        postService.deleteByNickname(id, nickname);

        RsData<Void> rsData = new RsData<>("S-delete-post", "게시글 삭제 성공", null);
        return ResponseEntity.status(200).body(rsData);
    }

    //    @DeleteMapping("/{id}")
    public ResponseEntity<RsData<Void>> deleteV2(@PathVariable("id") Long id,
                                                 @RequestBody String apiKey) {

        postService.deleteByKey(id, apiKey);

        RsData<Void> rsData = new RsData<>("S-delete-post", "게시글 삭제 성공", null);
        return ResponseEntity.status(200).body(rsData);
    }

//    @DeleteMapping("/{id}")
    public ResponseEntity<RsData<Void>> deleteV3(@PathVariable("id") Long id,
                                               @RequestHeader("Authorization") @NotBlank String apiKey) {

        String authorization = apiKey.replace("Bearer ", "");
        postService.deleteByKey(id, authorization);

        RsData<Void> rsData = new RsData<>("S-delete-post", "게시글 삭제 성공", null);
        return ResponseEntity.status(200).body(rsData);
    }

}
