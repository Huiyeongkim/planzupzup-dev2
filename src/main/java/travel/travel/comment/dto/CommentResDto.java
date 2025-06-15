package travel.travel.comment.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class CommentResDto {
    private Long commentId;
    private String content;
    private Long parentId;
    private String nickName;
    private Long planId;
    private List<CommentResDto> children;
}
