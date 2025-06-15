package travel.travel.commit.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import travel.travel.commit.domain.Commit;
import travel.travel.commit.dto.CommitCreateReqDto;
import travel.travel.commit.dto.CommitResDto;
import travel.travel.commit.dto.CommitUpdateReqDto;
import travel.travel.commit.repository.CommitRepository;
import travel.travel.member.domain.Member;
import travel.travel.member.repository.MemberRepository;
import travel.travel.plan.domain.Plan;
import travel.travel.plan.repository.PlanRepository;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CommitService {

    private final CommitRepository commitRepository;
    private final MemberRepository memberRepository;
    private final PlanRepository planRepository;

    public CommitResDto commitCreate(CommitCreateReqDto commitCreateReqDto) {
        //        String memberId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String memberId = "1";
        Member member = memberRepository.findById(Long.valueOf(memberId))
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));

//        Plan plan = planRepository.findById(commitCreateReqDto.getPlanId())
//                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 계획입니다."));
        Plan plan = null;

        Commit parent = null;
        if (commitCreateReqDto.getParentId() != null) {
            parent = commitRepository.findById(commitCreateReqDto.getParentId())
                    .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 댓글입니다."));
        }

        Commit savedCommit = commitRepository.save(commitCreateReqDto.toEntity(member, parent, plan));
        return savedCommit.fromEntity();
    }

    public CommitResDto commitUpdate(Long commitId, CommitUpdateReqDto commitUpdateReqDto) {
        //        String memberId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String memberId = "1";
        Member member = memberRepository.findById(Long.valueOf(memberId))
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));

        Commit findCommit = commitRepository.findById(commitId)
                        .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 댓글입니다."));

        if (!member.equals(findCommit.getMember())) {
            throw new IllegalStateException("본인 댓글만 수정할 수 있습니다.");
        }

        findCommit.updateCommit(commitUpdateReqDto.getContent());
        return findCommit.fromEntity();
    }

    public CommitResDto commitDelete(Long commitId) {
        //        String memberId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String memberId = "1";
        Member member = memberRepository.findById(Long.valueOf(memberId))
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));

        Commit findCommit = commitRepository.findById(commitId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 댓글입니다."));

        if (!member.equals(findCommit.getMember())) {
            throw new IllegalStateException("본인 댓글만 수정할 수 있습니다.");
        }

        commitRepository.delete(findCommit);
        return findCommit.fromEntity();
    }
}
