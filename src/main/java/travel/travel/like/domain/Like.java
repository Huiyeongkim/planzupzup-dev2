package travel.travel.like.domain;

import jakarta.persistence.*;
import travel.travel.member.domain.Member;
import travel.travel.plan.domain.Plan;

@Entity
@Table(name= "like")
public class Like {

    @Id @GeneratedValue
    private Long likeId;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Plan plan;
}
