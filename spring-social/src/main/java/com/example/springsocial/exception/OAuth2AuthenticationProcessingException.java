package com.example.springsocial.exception;

import com.example.springsocial.model.AuthProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

public class OAuth2AuthenticationProcessingException extends InternalAuthenticationServiceException {

    public OAuth2AuthenticationProcessingException(String msg) {
        super(msg);
    }

    public OAuth2AuthenticationProcessingException(AuthProvider provider) {
        super(
            String.format(
                "Looks like you're signed up with %s account. Please use your %s account to login.",
                provider, provider
            )
        );
    }
}
