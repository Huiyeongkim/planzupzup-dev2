package travel.travel.commit.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travel.travel.commit.dto.CommitResDto;
import travel.travel.common.domain.BaseEntity;
import travel.travel.member.domain.Member;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "commit")
public class Commit extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commitId;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Commit parent;

    private String content;

    public CommitResDto fromEntity() {
        return CommitResDto.builder()
                .commitId(commitId)
                .nickName(member.getNickName())
                .parentId(parent.getCommitId())
                .content(content)
                .build();
    }
}
