package travel.travel.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travel.travel.comment.dto.CommentCreateReqDto;
import travel.travel.comment.dto.CommentResDto;
import travel.travel.comment.dto.CommentUpdateReqDto;
import travel.travel.comment.service.CommentService;
import travel.travel.common.dto.CommonResDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController  {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommonResDto> commentCreate(@RequestBody CommentCreateReqDto commentCreateReqDto) {
        CommentResDto dto = commentService.commentCreate(commentCreateReqDto);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.CREATED, "댓글저장이 성공적으로 되었습니다.", dto), HttpStatus.CREATED);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommonResDto> commentUpdate(@PathVariable Long commentId, @RequestBody CommentUpdateReqDto commentUpdateReqDto) {
        CommentResDto dto = commentService.commentUpdate(commentId,commentUpdateReqDto);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "댓글수정이 성공적으로 되었습니다.", dto), HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommonResDto> commentDelete(@PathVariable Long commentId) {
        CommentResDto dto = commentService.commentDelete(commentId);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "댓글삭제가 성공적으로 되었습니다.", dto), HttpStatus.OK);
    }
}
