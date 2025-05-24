package travel.travel.location.dto;

import lombok.Data;

@Data
public class LocationOrderUpdateReqDto {
    private Long locationId;
    private Integer day;
    private Integer scheduleOrder;
}