package travel.travel.location.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travel.travel.image.domain.Image;
import travel.travel.image.dto.ImageResDto;
import travel.travel.location.dto.LocationResDto;
import travel.travel.location.dto.LocationThumbResDto;
import travel.travel.location.dto.LocationUpdateReqDto;
import travel.travel.plan.domain.Plan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name= "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    private String locationName;

    private double latitude;
    private double longitude;

    private String address;

    private Integer day;
    private String description;
    private Integer scheduleOrder;

    private String placeId;
    private String googleImageUrl;
    private String types;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();

    public LocationResDto fromEntity() {
        LocalDate startDate = plan.getStartDate();
        List<ImageResDto> imageResDtos = images.stream()
                .map(img -> ImageResDto.builder()
                        .imageId(img.getImageId())
                        .imageUrl(img.getImageUrl())
                        .build())
                .toList();

        return LocationResDto.builder()
                .locationId(this.locationId)
                .locationName(this.locationName)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .address(this.address)
                .day(startDate.plusDays(this.day-1))
                .description(this.description)
                .scheduleOrder(this.scheduleOrder)
                .placeId(this.placeId)
                .googleImageUrl(this.googleImageUrl)
                .types(this.types)
                .planId(this.plan.getPlanId())
                .images(imageResDtos)
                .build();
    }

    public LocationThumbResDto fromThumbEntity() {
        LocalDate startDate = plan.getStartDate();

        return LocationThumbResDto.builder()
                .locationId(this.locationId)
                .locationName(this.locationName)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .address(this.address)
                .day(startDate.plusDays(this.day-1))
                .scheduleOrder(this.scheduleOrder)
                .placeId(this.placeId)
                .googleImageUrl(this.googleImageUrl)
                .types(this.types)
                .build();
    }


    public void updateInfo(LocationUpdateReqDto locationUpdateReqDto, List<Image> image) {
        this.locationName = locationUpdateReqDto.getLocationName();
        this.latitude = locationUpdateReqDto.getLatitude();
        this.longitude = locationUpdateReqDto.getLongitude();
        this.address = locationUpdateReqDto.getAddress();
        this.description = locationUpdateReqDto.getDescription();
        this.googleImageUrl = locationUpdateReqDto.getGoogleImageUrl();
        this.types = locationUpdateReqDto.getTypes();
        this.placeId = locationUpdateReqDto.getPlaceId();
        this.images = image;
    }

    public void updateScheduleOrder(int scheduleOrder) {
        this.scheduleOrder = scheduleOrder;
    }

    public void updateDay(int day) {
        this.day = day;
    }
}
