package travel.travel.commit.dto;

import lombok.Getter;
import lombok.Setter;
import travel.travel.commit.domain.Commit;
import travel.travel.member.domain.Member;
import travel.travel.plan.domain.Plan;

@Setter
@Getter
public class CommitCreateReqDto {

    private String content;
    private Long parentId;
    private Long planId;

    public Commit toEntity(Member member, Commit parent, Plan plan) {
        return Commit.builder()
                .content(this.content)
                .parent(parent)
                .member(member)
                .plan(plan)
                .build();
    }
}
