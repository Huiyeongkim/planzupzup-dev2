package travel.travel.plan.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import travel.travel.member.domain.Member;
import travel.travel.plan.domain.Plan;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanUpdateReqDto {

    @NotEmpty(message = "title은 필수입니다.")
    private String title;
    private String content;

    private LocalDate startDate;
    private LocalDate endDate;

    public Plan toEntity(Member member) {
        return Plan.builder()
                .title(this.title)
                .content(this.content)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .member(member)
                .build();
    }
}