package travel.travel.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travel.travel.location.domain.Location;
import travel.travel.plan.domain.Plan;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByPlan(Plan plan);
    List<Location> findByPlanAndDayOrderByScheduleOrderAsc(Plan plan, Integer day);
    Location findTopByPlanAndDayOrderByScheduleOrderDesc(Plan plan, Integer day);
}
