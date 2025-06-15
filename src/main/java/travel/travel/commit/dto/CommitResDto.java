package travel.travel.commit.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class CommitResDto {
    private Long commitId;
    private String content;
    private Long parentId;
    private String nickName;
}
