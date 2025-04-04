package travel.travel.location.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import travel.travel.location.domain.Location;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class LocationUpdateReqDto {
    @NotNull(message = "locationId는 필수입니다.")
    private Long locationId;
    private Integer scheduleOrder;

    public Location toEntity() {
        return Location.builder()
                .scheduleOrder(this.scheduleOrder)
                .build();
    }
}
