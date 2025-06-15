package travel.travel.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travel.travel.comment.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
