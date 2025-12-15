package study.post.domain.post.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.post.domain.post.member.Member;
import study.post.domain.post.member.MemberRepository;
import study.post.golbal.exception.AuthorizationException;
import study.post.golbal.exception.PostNotFoundException;
import study.post.golbal.exception.UserNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostMapper postMapper;

    public Long count() {
        return postRepository.count();
    }

    public List<PostDto> findAll() {
        return postRepository.findAll().stream()
                .map(postMapper :: toDto)
                .toList();
    }

    public PostDto findById(Long id) {
        return postRepository.findById(id)
                .map(postMapper :: toDto)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
    }

    // 닉네임으로 읽기
    public PostDto writeV1(String nickname, String title, String content) {

        Member member = memberRepository.findByNickname(nickname).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없음"));

        Post post = new Post();
        post.setMember(member);
        post.setTitle(title);
        post.setContent(content);

        postRepository.save(post);

        return  postMapper.toDto(post);
    }

    // apiKey로 읽기
    public PostDto writeV2(String apiKey, String title, String content) {

        Member member = memberRepository.findByApiKey(apiKey).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없음"));

        Post post = new Post();
        post.setMember(member);
        post.setTitle(title);
        post.setContent(content);

        postRepository.save(post);

        return  postMapper.toDto(post);
    }

    public PostDto modifyAll(Long postId, String nickname, String title, String content) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없음"));

        if(!post.getMember().getNickname().equals(nickname)) {
            throw new AuthorizationException("수정 권한이 없습니다.");
        }

        post.setTitle(title);
        post.setContent(content);

        postRepository.save(post);

        return postMapper.toDto(post);
    }

    public void delete(Long id, String nickname) {

        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없음"));

        if(!post.getMember().getNickname().equals(nickname)) {
            throw new AuthorizationException("삭제 권한이 없습니다.");
        }

        postRepository.deleteById(id);
    }
}
