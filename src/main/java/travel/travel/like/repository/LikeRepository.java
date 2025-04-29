package travel.travel.like.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import travel.travel.like.domain.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
}
