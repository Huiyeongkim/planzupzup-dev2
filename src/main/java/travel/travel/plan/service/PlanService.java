package travel.travel.plan.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import travel.travel.location.domain.Location;
import travel.travel.location.dto.LocationResDto;
import travel.travel.location.dto.LocationThumbResDto;
import travel.travel.member.domain.Member;
import travel.travel.member.repository.MemberRepository;
import travel.travel.plan.domain.Destination;
import travel.travel.plan.dto.PlanCreateReqDto;
import travel.travel.plan.domain.Plan;
import travel.travel.plan.dto.PlanResDto;
import travel.travel.plan.dto.PlanUpdateReqDto;
import travel.travel.plan.repository.DestinationRepository;
import travel.travel.plan.repository.PlanRepository;


import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PlanService{
    private final PlanRepository planRepository;
    private final DestinationRepository destinationRepository;
    private final MemberRepository memberRepository;

    public PlanResDto planCreate(PlanCreateReqDto planCreateReqDto) {
//        String memberId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String memberId = "1";
        Member member = memberRepository.findById(Long.valueOf(memberId))
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));

        Destination destination = destinationRepository.findByDestinationName(planCreateReqDto.getDestinationName())
                .orElseThrow(()->new EntityNotFoundException("존재하지 않는 장소입니다."));

        if (planCreateReqDto.getStartDate().isAfter(planCreateReqDto.getEndDate())) {
            throw new IllegalArgumentException("시작일은 종료일보다 이전이어야 합니다.");
        }

        Plan savedPlan = planRepository.save(planCreateReqDto.toEntity(member, destination));
        return savedPlan.fromEntity();
    }


    public PlanResDto planReadDayList(Long planId, Integer day) {
        Plan existingPlan = planRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 계획입니다."));

        List<LocationThumbResDto> filteredLocations = existingPlan.getLocations().stream()
                .filter(location -> location.getDay().equals(day))
                .map(Location::fromThumbEntity)
                .toList();

        return existingPlan.fromEntityByDay(filteredLocations);
    }


    public PlanResDto planRead(Long postId) {
        Plan existingPlan = planRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 계획입니다."));

        List<LocationThumbResDto> filteredLocations = existingPlan.getLocations().stream()
                .map(Location::fromThumbEntity)
                .toList();

        return existingPlan.fromEntityByDay(filteredLocations);
    }

    public List<PlanResDto> planReadList() {
        List<PlanResDto> plans = planRepository.findAll().stream()
                .map(Plan::fromEntity)
                .collect(Collectors.toList());
        return plans;
    }

    public PlanResDto planUpdate(Long postId, PlanUpdateReqDto planUpdateReqDto) {
        //        String memberId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String memberId = "1";
        Member member = memberRepository.findById(Long.valueOf(memberId))
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));

        Plan existingPlan = planRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 계획입니다."));
        existingPlan.updatePlan(planUpdateReqDto.toEntity(member));

        if (!existingPlan.getMember().getId().equals(member.getId())) {
            throw new SecurityException("수정 권한이 없습니다.");
        }

        Plan savedPlan = planRepository.save(existingPlan);

        return savedPlan.fromEntity();

    }

    public PlanResDto planDelete(Long postId) {
        //        String memberId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String memberId = "1";
        Member member = memberRepository.findById(Long.valueOf(memberId))
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 회원입니다."));

        Plan existingPlan = planRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 계획입니다."));

        if (!existingPlan.getMember().getId().equals(member.getId())) {
            throw new SecurityException("삭제 권한이 없습니다.");
        }

        planRepository.delete(existingPlan);
        return existingPlan.fromEntity();
    }
}
