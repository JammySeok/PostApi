package study.post.domain.post.post;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import study.post.domain.post.Comment.Comment;
import study.post.domain.post.Comment.CommentDto;
import study.post.domain.post.Comment.CommentMapper;
import study.post.domain.post.member.Member;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-28T16:27:37+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.17 (Oracle Corporation)"
)
@Component
public class PostMapperImpl implements PostMapper {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public PostDto toDto(Post entity) {
        if ( entity == null ) {
            return null;
        }

        PostDto postDto = new PostDto();

        postDto.setWriter( entityMemberNickname( entity ) );
        postDto.setDate( entity.getUpdateAt() );
        postDto.setId( entity.getId() );
        postDto.setTitle( entity.getTitle() );
        postDto.setContent( entity.getContent() );
        postDto.setComments( commentListToCommentDtoList( entity.getComments() ) );

        return postDto;
    }

    @Override
    public Post toEntity(PostDto dto) {
        if ( dto == null ) {
            return null;
        }

        Post post = new Post();

        post.setId( dto.getId() );
        post.setTitle( dto.getTitle() );
        post.setContent( dto.getContent() );
        post.setComments( commentDtoListToCommentList( dto.getComments() ) );

        return post;
    }

    private String entityMemberNickname(Post post) {
        if ( post == null ) {
            return null;
        }
        Member member = post.getMember();
        if ( member == null ) {
            return null;
        }
        String nickname = member.getNickname();
        if ( nickname == null ) {
            return null;
        }
        return nickname;
    }

    protected List<CommentDto> commentListToCommentDtoList(List<Comment> list) {
        if ( list == null ) {
            return null;
        }

        List<CommentDto> list1 = new ArrayList<CommentDto>( list.size() );
        for ( Comment comment : list ) {
            list1.add( commentMapper.toDto( comment ) );
        }

        return list1;
    }

    protected List<Comment> commentDtoListToCommentList(List<CommentDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Comment> list1 = new ArrayList<Comment>( list.size() );
        for ( CommentDto commentDto : list ) {
            list1.add( commentMapper.toEntity( commentDto ) );
        }

        return list1;
    }
}
