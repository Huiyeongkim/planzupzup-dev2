package travel.travel.plan.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travel.travel.common.dto.CommonResDto;
import travel.travel.location.domain.Location;
import travel.travel.location.dto.LocationOrderUpdateReqDto;
import travel.travel.plan.domain.Plan;
import travel.travel.plan.dto.PlanCreateReqDto;
import travel.travel.plan.dto.PlanResDto;
import travel.travel.plan.dto.PlanUpdateReqDto;
import travel.travel.plan.service.PlanService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/plan")
public class PlanController {
    private final PlanService planService;

    @PostMapping
    public ResponseEntity<CommonResDto> planCreate(@Valid @RequestBody PlanCreateReqDto planCreateReqDto) {
        PlanResDto dto = planService.planCreate(planCreateReqDto);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.CREATED, "계획생성이 성공적으로 되었습니다.", dto), HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<CommonResDto> planRead(@PathVariable Long postId) {
        PlanResDto dto = planService.planRead(postId);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "계획상세조회가 성공적으로 되었습니다.", dto), HttpStatus.OK);
    }

    @GetMapping("/{planId}/{day}")
    public ResponseEntity<CommonResDto> planReadByDay(@PathVariable Long planId, @PathVariable Integer day) {
        PlanResDto dto = planService.planReadByDay(planId, day);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "날짜별 지역목록조회가 성공적으로 되었습니다.", dto), HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<CommonResDto> planReadList() {
        List<PlanResDto> dto = planService.planReadList();
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "계획목록조회가 성공적으로 되었습니다.", dto), HttpStatus.OK);
    }

    @PutMapping("/{planId}/order")
    public ResponseEntity<CommonResDto> updateScheduleOrder(
            @PathVariable Long planId,
            @RequestBody List<LocationOrderUpdateReqDto> locationOrderUpdateReqDtos) {

        PlanResDto dto = planService.updateScheduleOrder(planId, locationOrderUpdateReqDtos);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "지역날짜, 순서 변경이 성공적으로 되었습니다.", dto), HttpStatus.OK);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<CommonResDto> planUpdate(@PathVariable Long postId,@Valid @RequestBody PlanUpdateReqDto planUpdateReqDto) {
        PlanResDto dto = planService.planUpdate(postId, planUpdateReqDto);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "계획수정이 성공적으로 되었습니다.", dto), HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<CommonResDto> planDelete(@PathVariable Long postId) {
        PlanResDto dto = planService.planDelete(postId);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "계획삭제가 성공적으로 되었습니다.", dto), HttpStatus.OK);
    }

}
