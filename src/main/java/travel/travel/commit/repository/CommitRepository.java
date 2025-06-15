package travel.travel.commit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travel.travel.commit.domain.Commit;

public interface CommitRepository extends JpaRepository<Commit, Long> {
}
