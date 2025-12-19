package study.post.domain.post.Comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.post.domain.post.member.Member;
import study.post.domain.post.member.MemberDto;
import study.post.domain.post.member.MemberRepository;
import study.post.domain.post.post.Post;
import study.post.domain.post.post.PostRepository;
import study.post.golbal.exception.AuthorizationException;
import study.post.golbal.exception.CommentNotFoundException;
import study.post.golbal.exception.PostNotFoundException;
import study.post.golbal.exception.UserNotFoundException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public Long count() {
        return commentRepository.count();
    }

    public List<CommentDto> findAll() {
        return commentRepository.findAll().stream()
                .map(commentMapper :: toDto)
                .toList();
    }

    public CommentDto findById(Long id) {
        return commentRepository.findById(id)
                .map(commentMapper :: toDto)
                .orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다."));
    }

    public List<CommentDto> findAllByPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없습니다."));

        return commentRepository.findByPost(post).stream()
                .map(commentMapper :: toDto)
                .toList();
    }

    public CommentDto write(Long postId, MemberDto dto, String content) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없음"));
        Member actor = memberRepository.findById(dto.getId()).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없음"));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setMember(actor);
        comment.setContent(content);

        commentRepository.save(comment);

        return commentMapper.toDto(comment);
    }

    public CommentDto modify(Long postId, MemberDto dto, Long commentId, String content) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다."));
        Member actor = memberRepository.findById(dto.getId()).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없음"));

        if(!comment.getPost().getId().equals(postId)) {
            throw new AuthorizationException("게시글에 댓글이 속하지 않습니다.");
        }

        if(!comment.getMember().equals(actor)) {
            throw new AuthorizationException("댓글 수정 권한이 없습니다.");
        }


        comment.setContent(content);

        commentRepository.save(comment);

        return commentMapper.toDto(comment);
    }

    public void delete(Long postId, Long commentId, MemberDto dto) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다."));
        Member actor = memberRepository.findById(dto.getId()).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없음"));

        if(!comment.getPost().getId().equals(postId)) {
            throw new AuthorizationException("게시글에 댓글이 속하지 않습니다.");
        }

        if(!comment.getMember().equals(actor)) {
            throw new AuthorizationException("댓글 삭제 권한이 없습니다.");
        }

        commentRepository.deleteById(commentId);
    }


    /**
     * 이전 버전들 관리
     * 아래는 사용 X
     */

    public CommentDto writeByNickname(Long postId, String nickname, String content) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없음"));
        Member member = memberRepository.findByNickname(nickname).orElseThrow(() -> new UserNotFoundException("회원을 찾을 수 없음"));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setMember(member);
        comment.setContent(content);

        commentRepository.save(comment);

        return commentMapper.toDto(comment);
    }

    public CommentDto modifyByNickname(Long postId, String nickname, Long commentId, String content) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다."));

        if(!comment.getPost().getId().equals(postId)) {
            throw new AuthorizationException("게시글에 댓글이 속하지 않습니다.");
        }

        if(!comment.getMember().getNickname().equals(nickname)) {
            throw new AuthorizationException("댓글 수정 권한이 없습니다.");
        }

        comment.setContent(content);

        commentRepository.save(comment);

        return commentMapper.toDto(comment);
    }

    public void deleteByNickname(Long postId, String nickname, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("댓글을 찾을 수 없습니다."));

        if(!comment.getPost().getId().equals(postId)) {
            throw new AuthorizationException("게시글에 댓글이 속하지 않습니다.");
        }

        if(!comment.getMember().getNickname().equals(nickname)) {
            throw new AuthorizationException("댓글 삭제 권한이 없습니다.");
        }

        commentRepository.deleteById(commentId);
    }
}
