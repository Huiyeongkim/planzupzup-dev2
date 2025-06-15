package travel.travel.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travel.travel.comment.domain.Comment;
import travel.travel.plan.domain.Plan;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPlanAndParentIsNull(Plan plan);
}
