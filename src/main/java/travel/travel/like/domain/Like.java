package travel.travel.like.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import travel.travel.member.domain.Member;
import travel.travel.plan.domain.Plan;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "`like`")
@Builder
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne
    private Member member;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;
}
