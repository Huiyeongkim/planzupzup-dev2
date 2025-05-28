package travel.travel.plan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class PlanThumbResDto {
    private Long planId;
    private String title;
    private String destinationName;
}
