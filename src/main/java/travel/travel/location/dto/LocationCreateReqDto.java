package travel.travel.location.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import travel.travel.image.domain.Image;
import travel.travel.location.domain.Location;
import travel.travel.plan.domain.Plan;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationCreateReqDto {

    @NotEmpty(message = "locationName는 필수입니다.")
    private String locationName;

    private double latitude;
    private double longitude;
    private String address;
    private Integer day;
    private String description;
    private Long planId;

    private String placeId;
    private String googleImageUrl;
    private String types;

    public Location toEntity(Plan plan, List<Image> images, Integer newOrderNumber) {
        return Location.builder()
                .locationName(this.locationName)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .address(this.address)
                .day(this.day)
                .description(this.description)
                .placeId(this.placeId)
                .scheduleOrder(newOrderNumber)
                .plan(plan)
                .googleImageUrl(this.googleImageUrl)
                .types(this.types)
                .images(images)
                .build();
    }
}
