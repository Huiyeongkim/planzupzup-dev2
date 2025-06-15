package travel.travel.commit.dto;

import lombok.Getter;
import lombok.Setter;
import travel.travel.commit.domain.Commit;
import travel.travel.member.domain.Member;

@Setter
@Getter
public class CommitCreateReqDto {

    private String content;
    private Long parentId;

    public Commit toEntity(Member member, Commit parent) {
        return Commit.builder()
                .content(this.content)
                .parent(parent)
                .member(member)
                .build();
    }
}
