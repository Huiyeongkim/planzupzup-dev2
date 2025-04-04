package travel.travel.location.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import travel.travel.common.dto.CommonResDto;
import travel.travel.location.dto.LocationCreateReqDto;
import travel.travel.location.dto.LocationResDto;
import travel.travel.location.dto.LocationUpdateReqDto;
import travel.travel.location.service.LocationService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/location")
public class LocationController {
    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<CommonResDto> LocationCreate(
            @Valid @RequestPart LocationCreateReqDto locationCreateReqDto,
            @RequestPart(required = false) MultipartFile file) throws IOException {
        LocationResDto dto = locationService.LocationCreate(locationCreateReqDto, file);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.CREATED, "지역저장이 성공적으로 되었습니다.", dto), HttpStatus.CREATED);
    }

    @GetMapping("/{planId}")
    public ResponseEntity<CommonResDto> LocationReadList(@PathVariable Long planId) {
        List<LocationResDto> dto = locationService.LocationReadList(planId);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "지역목록조회가 성공적으로 되었습니다.", dto), HttpStatus.OK);
    }

    @GetMapping("/{planId}/{day}")
    public ResponseEntity<CommonResDto> LocationReadDayList(@PathVariable Long planId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {
        List<LocationResDto> dto = locationService.LocationReadDayList(planId, day);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "날짜별 지역목록조회가 성공적으로 되었습니다.", dto), HttpStatus.OK);
    }

    @PutMapping("/{planId}/{day}")
    public ResponseEntity<CommonResDto> LocationUpdate(@RequestBody List<@Valid LocationUpdateReqDto> locationUpdateReqDtos, @PathVariable Long planId, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {
        List<LocationResDto> dto = locationService.LocationUpdate(locationUpdateReqDtos, planId, day);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "지역수정이 성공적으로 되었습니다.", dto), HttpStatus.OK);
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<CommonResDto> locationDelete(@PathVariable Long locationId) {
        LocationResDto dto = locationService.locationDelete(locationId);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "지역삭제가 성공적으로 되었습니다.", dto), HttpStatus.OK);
    }

}