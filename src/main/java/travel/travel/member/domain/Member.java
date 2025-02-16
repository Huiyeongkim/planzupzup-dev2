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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long kakaoId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public Member(Long kakaoId) {
        this.kakaoId = kakaoId;
        this.role = Role.USER;
    }
}
