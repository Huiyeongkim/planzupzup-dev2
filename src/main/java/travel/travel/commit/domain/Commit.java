package travel.travel.commit.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import travel.travel.common.domain.BaseEntity;


@Entity
@Table(name= "commit")
public class Commit extends BaseEntity {

    @Id @GeneratedValue
    private Long commitId;
}
