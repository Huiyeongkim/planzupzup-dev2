package travel.travel.commit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import travel.travel.commit.dto.CommitCreateReqDto;
import travel.travel.commit.dto.CommitResDto;
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

}
