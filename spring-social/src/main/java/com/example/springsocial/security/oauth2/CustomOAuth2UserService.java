package com.example.springsocial.security.oauth2;

import com.example.springsocial.model.User;
import com.example.springsocial.security.UserPrincipal;
import com.example.springsocial.security.oauth2.user.OAuth2UserInfo;
import com.example.springsocial.security.oauth2.user.OAuth2UserInfoFactory;
import com.example.springsocial.exception.OAuth2AuthenticationProcessingException;
import com.example.springsocial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;

    @Autowired
    public CustomOAuth2UserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest)
        throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        String oauthProvider = oAuth2UserRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo
            = OAuth2UserInfoFactory.getOAuth2UserInfo(oauthProvider, oAuth2User.getAttributes());

        if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        User user = userService.findByEmail(oAuth2UserInfo.getEmail())
            .orElseGet(() -> userService.registerOauthUser(oAuth2UserRequest, oAuth2UserInfo));

        if (!user.getProvider().isSame(oauthProvider)) {
            throw new OAuth2AuthenticationProcessingException(user.getProvider());
        }
        user = userService.updateExistingUser(user, oAuth2UserInfo);

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }
}
