package travel.travel.location.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import travel.travel.common.dto.CommonResDto;
import travel.travel.location.dto.LocationCreateReqDto;
import travel.travel.location.dto.LocationOrderUpdateReqDto;
import travel.travel.location.dto.LocationResDto;
import travel.travel.location.dto.LocationUpdateReqDto;
import travel.travel.location.service.LocationService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/location")
public class LocationController {
    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<CommonResDto> LocationCreate(
            @Valid @RequestPart LocationCreateReqDto locationCreateReqDto,
            @RequestPart(required = false) List<MultipartFile> files) throws IOException {
        LocationResDto dto = locationService.LocationCreate(locationCreateReqDto, files);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.CREATED, "지역저장이 성공적으로 되었습니다.", dto), HttpStatus.CREATED);
    }

    @GetMapping("/{planId}")
    public ResponseEntity<CommonResDto> LocationReadList(@PathVariable Long planId) {
        List<LocationResDto> dto = locationService.LocationReadList(planId);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "지역목록조회가 성공적으로 되었습니다.", dto), HttpStatus.OK);
    }

    @GetMapping("/{planId}/{day}")
    public ResponseEntity<CommonResDto> LocationReadDayList(@PathVariable Long planId, @PathVariable Integer day) {
        List<LocationResDto> dto = locationService.LocationReadDayList(planId, day);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "날짜별 지역목록조회가 성공적으로 되었습니다.", dto), HttpStatus.OK);
    }

    @PutMapping("/{locationId}")
    public ResponseEntity<CommonResDto> LocationUpdate(
            @PathVariable Long locationId,
            @Valid @RequestPart LocationUpdateReqDto locationUpdateReqDto,
            @RequestPart(required = false) List<MultipartFile> files) throws IOException {
        LocationResDto dto = locationService.LocationUpdate(locationId, locationUpdateReqDto, files);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "지역변경이 성공적으로 되었습니다.", dto), HttpStatus.OK);
    }

    @PutMapping("/{planId}/order")
    public ResponseEntity<CommonResDto> updateScheduleOrder(
            @PathVariable Long planId,
            @RequestBody List<LocationOrderUpdateReqDto> locationOrderUpdateReqDtos) {

        List<LocationResDto> dto = locationService.updateScheduleOrder(planId, locationOrderUpdateReqDtos);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "지역날짜, 순서 변경이 성공적으로 되었습니다.", dto), HttpStatus.OK);
    }

    @DeleteMapping("/{locationId}")
    public ResponseEntity<CommonResDto> locationDelete(@PathVariable Long locationId) {
        LocationResDto dto = locationService.locationDelete(locationId);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "지역삭제가 성공적으로 되었습니다.", dto), HttpStatus.OK);
    }

}