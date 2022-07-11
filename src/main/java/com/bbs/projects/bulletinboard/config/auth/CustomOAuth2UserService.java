package com.bbs.projects.bulletinboard.config.auth;

import com.bbs.projects.bulletinboard.config.auth.dto.OAuthAttributes;
import com.bbs.projects.bulletinboard.config.auth.dto.SessionUser;
import com.bbs.projects.bulletinboard.domain.user.User;
import com.bbs.projects.bulletinboard.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    //생성자 주입
    private final UserRepository userRepository;
    //세션을 생성하여 사용자를 식별하고 저장
    private final HttpSession httpSession;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        //OAuth2UserService의 구현체인 DefaultOAuth2UserService를 받음
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        //구현체를 통해 userRequest에 있는 정보를 빼낼 수 있음
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        //로그인 진행 중인 서비스 구분
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        //로그인 진행 시 키가 되는 값
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        //OAuth2UserService를 통해 가져온 OAuth2User의 attributes를 담음
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
                oAuth2User.getAttributes());

        //가입 여부를 확인 후, 저장하거나 갱신
        User user = saveOrUpdate(attributes);

        //로그인 성공 시, 세션에 사용자 정보 저장
        httpSession.setAttribute("user", new SessionUser(user));

        //인증된 사용자를 반환
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(), attributes.getNameAttributeKey());

    }

    //가입 및 정보 수정
    private User saveOrUpdate(OAuthAttributes attributes) {

        //email을 통해 가입자 여부 구분
        User user = userRepository.findByEmail(attributes.getEmail())
                //미가입자는 toEntity로 User 엔티티를 생성, 기가입자는 name과 picture 갱신
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        //처리가 끝난 User를 DB에 저장
        return userRepository.save(user);

    }

}
