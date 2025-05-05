package travel.travel.like.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travel.travel.common.dto.CommonResDto;
import travel.travel.like.service.LikeService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/{planId}/like")
    public ResponseEntity<CommonResDto> addLike (@PathVariable Long planId) {
        likeService.addLike(planId);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "좋아요가 성공적으로 되었습니다.", null), HttpStatus.OK);
    }

    @DeleteMapping("/{planId}/like")
    public ResponseEntity<CommonResDto> removeLike(@PathVariable Long planId) {
        likeService.removeLike(planId);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "좋아요취소가 성공적으로 되었습니다.", null), HttpStatus.OK);
    }

    @GetMapping("/{planId}/count")
    public ResponseEntity<CommonResDto> getLikeCount(@PathVariable Long planId) {
        Long likeCount = likeService.getLikeCount(planId);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "좋아요개수조회가 성공적으로 되었습니다.", likeCount), HttpStatus.OK);
    }
}