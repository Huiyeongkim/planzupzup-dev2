package travel.travel.member.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travel.travel.common.domain.BaseEntity;


@Getter
@Entity
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id
    private Long id;

    private String nickName;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public Member(Long kakaoId) {
        this.id = kakaoId;
        this.role = Role.USER;
    }
}
