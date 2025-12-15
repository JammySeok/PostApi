package study.post.domain.post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RsData<T> {

    private String resCode;
    private String msg;
    private T data;
}
