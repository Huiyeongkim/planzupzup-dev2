package travel.travel.common.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import travel.travel.common.service.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        log.info("onAuthenticationSuccess");

        OAuth2User oAuth2User =  (OAuth2User)authentication.getPrincipal();
        String id = String.valueOf(oAuth2User.getAttributes().get("id"));

        log.info("id = {}", id);

        String accessToken = jwtTokenProvider.generateAccessToken(id);
        ResponseCookie accessTokenCookie = jwtTokenProvider.generateAccessTokenCookie(accessToken);

        String refreshToken = jwtTokenProvider.generateRefreshToken();
        log.info("refreshToken = {}", refreshToken);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());

        Map<String, String> responseData = new HashMap<>();
        responseData.put("accessToken", accessToken);
        responseData.put("refreshToken", refreshToken);

        response.getWriter().write(objectMapper.writeValueAsString(responseData));
    }

}
