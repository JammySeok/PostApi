package study.post.domain.post.post;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import study.post.domain.post.Comment.Comment;
import study.post.domain.post.member.Member;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String title;
    private String content;

    @Column(name = "create_at")
    @CreationTimestamp
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @OneToMany(mappedBy = "post")
    @Column(name = "create_at")
    private List<Comment> comments;
}
