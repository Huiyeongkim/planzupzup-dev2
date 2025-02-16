package travel.travel.member.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import travel.travel.common.domain.BaseEntity;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public Member(Long kakaoId) {
        this.id = kakaoId;
        this.role = Role.USER;
    }
}
