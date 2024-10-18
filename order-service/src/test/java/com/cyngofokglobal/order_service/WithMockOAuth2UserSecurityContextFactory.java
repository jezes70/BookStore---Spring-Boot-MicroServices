package com.cyngofokglobal.order_service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WithMockOAuth2UserSecurityContextFactory implements WithSecurityContextFactory<WithMockOAuth2User> {

    @Override
    public SecurityContext createSecurityContext(WithMockOAuth2User withUser) {
        String username = withUser.username();
        if (username == null || username.isEmpty()) {
            username = withUser.value();
        }
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username must not be null or empty.");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : withUser.role()) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        Map<String, Object> claims = Map.of(
                "preferred_username", username,
                "userId", withUser.id()
        );

        Map<String, Object> headers = Map.of("alg", "none");

        Jwt jwt = new Jwt("mock-jwt-token", Instant.now(), Instant.now().plusSeconds(3600), headers, claims);

        Authentication authentication = new JwtAuthenticationToken(jwt, authorities);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        return context;
    }
}
