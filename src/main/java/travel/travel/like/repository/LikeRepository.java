package travel.travel.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travel.travel.like.domain.Like;
import travel.travel.member.domain.Member;
import travel.travel.plan.domain.Plan;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByMemberAndPlan(Member member, Plan plan);
    long countByPlan(Plan plan);
}
