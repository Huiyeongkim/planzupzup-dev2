package travel.travel.location.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import travel.travel.image.dto.ImageResDto;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationResDto {
    private Long locationId;
    private String locationName;

    private double latitude;
    private double longitude;
    private String address;
    private LocalDate day;
    private String description;
    private Long planId;
    private Integer scheduleOrder;

    private String placeId;
    private String googleImageUrl;
    private String types;
    private List<ImageResDto> images;
}