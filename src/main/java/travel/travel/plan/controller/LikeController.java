package travel.travel.plan.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travel.travel.common.dto.CommonResDto;
import travel.travel.plan.service.LikeService;


@RestController
@RequiredArgsConstructor
@Slf4j
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/api/{postId}/like")
    public ResponseEntity<CommonResDto> toggleLike(@PathVariable Long postId) {
        boolean like = likeService.toggleLike(postId);
        if (like) {
            return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "좋아요가 성공적으로 되었습니다.", null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "좋아요취소가 성공적으로 되었습니다.", null), HttpStatus.OK);
    }

    @GetMapping("/api/{postId}/count")
    public ResponseEntity<CommonResDto> getLikeCount(@PathVariable Long postId) {
        Long likeCount = likeService.getLikeCount(postId);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "좋아요개수조회가 성공적으로 되었습니다.", likeCount), HttpStatus.OK);
    }
}
