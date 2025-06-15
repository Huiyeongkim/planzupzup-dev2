package travel.travel.commit.domain;

import jakarta.persistence.*;
import travel.travel.common.domain.BaseEntity;
import travel.travel.member.domain.Member;


@Entity
@Table(name= "commit")
public class Commit extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commitId;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Commit parentId;
}
