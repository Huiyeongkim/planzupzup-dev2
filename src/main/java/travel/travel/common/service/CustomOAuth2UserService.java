package travel.travel.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import travel.travel.member.domain.Member;
import travel.travel.member.repository.MemberRepository;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oauth2User = delegate.loadUser(userRequest);

        // 카카오 사용자 정보 가져오기
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        Map<String, Object> attributes = oauth2User.getAttributes();
        Long kakaoId = (Long) attributes.get("id");
        log.info("kakao {}" , kakaoId);
        Member member = save(kakaoId);


        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().toString())),
                attributes,
                userNameAttributeName
        );
    }

    private Member save(Long kakaoId) {
        Optional<Member> member = memberRepository
                .findByKakaoId(kakaoId);

        if (member.isPresent()) {
            log.info("member 이미 존재 : {}", member);
            return member.get();
        } else {
            Member newMember = new Member(kakaoId);
            memberRepository.save(newMember);
            log.info("member 새로 존재 : {}", newMember);
            return newMember;
        }
    }
}
