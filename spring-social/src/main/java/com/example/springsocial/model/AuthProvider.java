package com.example.springsocial.model;

public enum AuthProvider {
    LOCAL,
    FACEBOOK,
    GOOGLE,
    GITHUB;

    public boolean isSame(String provider) {
        return this == valueOf(provider.toUpperCase());
    }
}
