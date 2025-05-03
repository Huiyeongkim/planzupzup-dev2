package travel.travel.like.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import travel.travel.like.domain.Like;
import travel.travel.like.repository.LikeRepository;
import travel.travel.member.domain.Member;
import travel.travel.member.repository.MemberRepository;
import travel.travel.plan.domain.Plan;
import travel.travel.plan.repository.PlanRepository;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final PlanRepository planRepository;
    private final MemberRepository memberRepository;

    public void addLike(Long planId) {
        String memberId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = memberRepository.findById(Long.valueOf(memberId))
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));
        Optional<Like> existingLike = likeRepository.findByMemberAndPlan(member, plan);
        if (existingLike.isEmpty()) {
            Like like = Like.builder()
                    .member(member)
                    .plan(plan)
                    .build();
            likeRepository.save(like);
        } else {
            throw new IllegalStateException("이미 좋아요를 눌렀습니다.");
        }
    }

    public void removeLike(Long planId) {
        String memberId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = memberRepository.findById(Long.valueOf(memberId))
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));
        Optional<Like> existingLike = likeRepository.findByMemberAndPlan(member, plan);
        existingLike.ifPresent(likeRepository::delete);
    }

    public long getLikeCount(Long planId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));
        return likeRepository.countByPlan(plan);
    }
}