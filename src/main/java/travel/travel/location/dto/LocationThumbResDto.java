package travel.travel.location.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationThumbResDto {

    private Long locationId;
    private String locationName;

    private double latitude;
    private double longitude;
    private String address;
    private LocalDate day;

    private Integer scheduleOrder;

    private String placeId;
    private String googleImageUrl;
    private String types;
}