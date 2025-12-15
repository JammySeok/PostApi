package study.post.domain.post.Comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.post.domain.post.RsData;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class ApiV1CommentController {

    private final CommentService commentService;

    record WriteReqBody(String content) {}
    record ModifyReqBody(String content) {}

    @GetMapping("/comments")
    public List<CommentDto> getComments() {
        return commentService.findAll();
    }

    @GetMapping("/comments/{id}")
    public CommentDto getComment(@PathVariable("id") Long id) {
        return commentService.findById(id);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPost(@PathVariable("postId") Long postId) {
        return commentService.findAllByPost(postId);
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<RsData<CommentDto>> writeComment(@PathVariable("postId") Long postId,
                                                     @RequestBody WriteReqBody writeReqBody,
                                                     @RequestParam String nickname) {

        CommentDto rsDto = commentService.write(postId, nickname, writeReqBody.content);

        RsData<CommentDto> rsData = new RsData<>("S-write-comment", "댓글 작성 완료", rsDto);
        return ResponseEntity.status(200).body(rsData);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<RsData<CommentDto>> modify(@PathVariable("postId") Long postId,
                                               @PathVariable("commentId") Long commentId,
                                               @RequestBody ModifyReqBody modifyReqBody,
                                               @RequestParam String nickname) {

        CommentDto rsDto = commentService.modify(postId, nickname, commentId, modifyReqBody.content);

        RsData<CommentDto> rsData = new RsData<>("S-modify-comment", "댓글 수정 완료", rsDto);
        return ResponseEntity.status(200).body(rsData);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<RsData<Void>> delete(@PathVariable("postId") Long postId,
                                               @PathVariable("commentId") Long commentId,
                                               @RequestParam String nickname) {

        commentService.delete(postId, nickname, commentId);

        RsData<Void> rsData = new RsData<>("S-delete-comment", "댓글 삭제 완료", null);
        return ResponseEntity.status(200).body(rsData);
    }
}
