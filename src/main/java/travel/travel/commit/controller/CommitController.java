package travel.travel.commit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travel.travel.commit.dto.CommitCreateReqDto;
import travel.travel.commit.dto.CommitResDto;
import travel.travel.commit.dto.CommitUpdateReqDto;
import travel.travel.commit.service.CommitService;
import travel.travel.common.dto.CommonResDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/commit")
public class CommitController  {

    private final CommitService commitService;

    @PostMapping
    public ResponseEntity<CommonResDto> commitCreate(@RequestBody CommitCreateReqDto commitCreateReqDto) {
        CommitResDto dto = commitService.commitCreate(commitCreateReqDto);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.CREATED, "댓글저장이 성공적으로 되었습니다.", dto), HttpStatus.CREATED);
    }

    @PutMapping("/{commitId}")
    public ResponseEntity<CommonResDto> commitUpdate(@PathVariable Long commitId, @RequestBody CommitUpdateReqDto commitUpdateReqDto) {
        CommitResDto dto = commitService.commitUpdate(commitId,commitUpdateReqDto);
        return new ResponseEntity<>(new CommonResDto(HttpStatus.OK, "댓글수정이 성공적으로 되었습니다.", dto), HttpStatus.OK);
    }

}
