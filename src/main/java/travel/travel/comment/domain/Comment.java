package travel.travel.comment.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travel.travel.comment.dto.CommentResDto;
import travel.travel.common.domain.BaseEntity;
import travel.travel.member.domain.Member;
import travel.travel.plan.domain.Plan;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Comment parent;

    private String content;

    @ManyToOne
    private Plan plan;

    public CommentResDto fromEntity() {
        return CommentResDto.builder()
                .commentId(commentId)
                .nickName(member.getNickName())
                .parentId(parent != null ? parent.getCommentId() : null)
                .content(content)
                .planId(plan != null ? plan.getPlanId() : null)
                .build();
    }

    public void updateComment(String content) {
        this.content = content;
    }
}
