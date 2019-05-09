package com.example.springsocial.security.oauth2.authorization;

import static com.example.springsocial.security.oauth2.authorization.CookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

import com.example.springsocial.config.AppProperties;
import com.example.springsocial.exception.BadRequestException;
import com.example.springsocial.security.TokenProvider;
import com.example.springsocial.util.CookieUtils;
import java.io.IOException;
import java.net.URI;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private TokenProvider tokenProvider;
    private AppProperties appProperties;
    private CookieOAuth2AuthorizationRequestRepository cookieOAuth2AuthorizationRequestRepository;


    @Autowired
    OAuth2AuthenticationSuccessHandler(
        TokenProvider tokenProvider,
        AppProperties appProperties,
        CookieOAuth2AuthorizationRequestRepository cookieOAuth2AuthorizationRequestRepository) {

        this.tokenProvider = tokenProvider;
        this.appProperties = appProperties;
        this.cookieOAuth2AuthorizationRequestRepository = cookieOAuth2AuthorizationRequestRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
        throws IOException {

        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private String determineTargetUrl(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication) {

        String redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
            .map(Cookie::getValue)
            .filter(this::isAuthorizedRedirectUri)
            .orElseThrow(() -> new BadRequestException(
                "Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication"
            ));

        return UriComponentsBuilder.fromUriString(redirectUri)
            .queryParam("token", tokenProvider.createToken(authentication))
            .build()
            .toUriString();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        cookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uriString) {
        URI clientRedirectUri = URI.create(uriString);

        return appProperties.getOauth2().getAuthorizedRedirectUris()
            .stream()
            .anyMatch(authorizedRedirectUri -> {
                // Only validate host and port. Let the clients use different paths if they want to
                URI uri = URI.create(authorizedRedirectUri);
                return uri.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                    && uri.getPort() == clientRedirectUri.getPort();
            });
    }
}
