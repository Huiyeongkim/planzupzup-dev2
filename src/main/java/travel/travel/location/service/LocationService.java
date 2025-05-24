package travel.travel.location.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import travel.travel.image.domain.Image;
import travel.travel.image.dto.ImageResDto;
import travel.travel.image.repository.ImageRepository;
import travel.travel.image.service.ImageService;
import travel.travel.location.domain.Location;
import travel.travel.location.dto.LocationCreateReqDto;
import travel.travel.location.dto.LocationOrderUpdateReqDto;
import travel.travel.location.dto.LocationResDto;
import travel.travel.location.dto.LocationUpdateReqDto;
import travel.travel.location.repository.LocationRepository;
import travel.travel.plan.domain.Plan;
import travel.travel.plan.repository.PlanRepository;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class LocationService {
    private final LocationRepository locationRepository;
    private final PlanRepository planRepository;
    private final ImageService imageService;
    private final ImageRepository imageRepository;

    public LocationResDto LocationCreate(LocationCreateReqDto locationCreateReqDto, List<MultipartFile> files) throws IOException {
        Plan plan = planRepository.findById(locationCreateReqDto.getPlanId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 계획입니다."));

        long total = ChronoUnit.DAYS.between(plan.getStartDate(), plan.getEndDate()) + 1;
        if (locationCreateReqDto.getDay() < 1 || locationCreateReqDto.getDay() > total) {
            throw new IllegalArgumentException("요청하신 day 값이 계획 범위를 벗어났습니다.");
        }

        List<ImageResDto> imageResDtos = imageService.uploadFiles(files);
        List<Image> image =  imageResDtos.stream()
                .map(img -> Image.builder()
                        .imageId(img.getImageId())
                        .imageUrl(img.getImageUrl())
                        .build())
                .toList();

        Location location = locationRepository.findTopByPlanAndDayOrderByScheduleOrderDesc(plan, locationCreateReqDto.getDay());
        Integer lastOrderNumber = 0;
        if (location != null) {
            lastOrderNumber = location.getScheduleOrder();
        }
        Integer newOrderNumber = lastOrderNumber + 1;

        Location savedLocation = locationRepository.save(locationCreateReqDto.toEntity(plan, image, newOrderNumber));
        return savedLocation.fromEntity();
    }

    public List<LocationResDto> LocationReadList(Long planId) {
        Plan plan =  planRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 계획입니다."));
        return locationRepository.findByPlan(plan).stream()
                .map(Location::fromEntity)
                .collect(Collectors.toList());
    }

    public List<LocationResDto> LocationReadDayList(Long planId, Integer day) {
        Plan plan =  planRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 계획입니다."));
        return locationRepository.findByPlanAndDayOrderByScheduleOrderAsc(plan, day).stream()
                .map(Location::fromEntity)
                .collect(Collectors.toList());
    }

    public LocationResDto LocationRead(Long locationId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 지역입니다."));

        return location.fromEntity();
    }

    public LocationResDto LocationUpdate(Long locationId, LocationUpdateReqDto locationUpdateReqDto, List<MultipartFile> files) throws IOException {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new EntityNotFoundException("해당 지역이 존재하지 않습니다."));

        imageRepository.deleteAll(location.getImages());

        List<ImageResDto> imageResDtos = imageService.uploadFiles(files);
        List<Image> image =  imageResDtos.stream()
                .map(img -> Image.builder()
                        .imageId(img.getImageId())
                        .imageUrl(img.getImageUrl())
                        .build())
                .toList();

        location.updateInfo(locationUpdateReqDto, image);
        return location.fromEntity();
    }


    public List<LocationResDto> updateScheduleOrder(Long planId, List<LocationOrderUpdateReqDto> locationOrderUpdateReqDtos) {
        Plan plan =  planRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 계획입니다."));

        Map<Integer, List<LocationOrderUpdateReqDto>> groupedByDay = locationOrderUpdateReqDtos.stream()
                .collect(Collectors.groupingBy(LocationOrderUpdateReqDto::getDay));

        List<LocationResDto> result = new ArrayList<>();

        for (Map.Entry<Integer, List<LocationOrderUpdateReqDto>> entry : groupedByDay.entrySet()) {
            Integer day = entry.getKey();

            List<LocationResDto> dayResult = new ArrayList<>();

            for (LocationOrderUpdateReqDto dto :  entry.getValue()) {
                Location existingLocation =  locationRepository.findById(dto.getLocationId())
                        .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 지역입니다."));

                if (!existingLocation.getPlan().equals(plan)) {
                    throw new IllegalArgumentException("요청 정보와 일치하지 않는 지역입니다.");
                }
                if (!existingLocation.getDay().equals(day)) {
                    existingLocation.updateDay(day);
                }

                if (!existingLocation.getScheduleOrder().equals(dto.getScheduleOrder())) {
                    existingLocation.updateScheduleOrder(dto.getScheduleOrder());
                }

                dayResult.add(existingLocation.fromEntity());
            }
            checkForDuplicateScheduleOrder(dayResult);
            List<Location> reorderLocation = locationRepository.findByPlanAndDayOrderByScheduleOrderAsc(plan, day);
            autoScheduleOrder(reorderLocation);

            List<LocationResDto> refreshed = reorderLocation.stream()
                    .map(Location::fromEntity)
                    .toList();

            result.addAll(refreshed);
        }

        return result;
    }

    public LocationResDto locationDelete(Long locationId) {
        Location existingLocation = locationRepository.findById(locationId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 위치입니다."));
        Integer day = existingLocation.getDay();
        locationRepository.delete(existingLocation);
        Plan plan =  planRepository.findById(existingLocation.getPlan().getPlanId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 계획입니다."));

        autoScheduleOrder(locationRepository.findByPlanAndDayOrderByScheduleOrderAsc(plan, day));

        return existingLocation.fromEntity();
    }

    private void checkForDuplicateScheduleOrder(List<LocationResDto> dtos) {
        List<Integer> orderList = dtos.stream()
                .map(LocationResDto::getScheduleOrder).toList();

        long distinctCount = orderList.stream().distinct().count();
        if (distinctCount != orderList.size()) {
            throw new IllegalArgumentException("중복된 scheduleOrder 값이 있습니다. 순서를 다시 확인해주세요.");
        }
    }

    private void autoScheduleOrder(List<Location> locations) {
        int newOrderNumber = 1;
        for (Location location : locations) {
            location.updateScheduleOrder(newOrderNumber++);
            locationRepository.save(location);
        }
    }
}