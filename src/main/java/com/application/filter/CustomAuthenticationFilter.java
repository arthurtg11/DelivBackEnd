package com.application.filter;

import com.application.domain.TabUser;
import com.application.domain.dto.UserLoginDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    public static final String TOKEN_SENHA = "463408a1-54c9-4307-bb1c-6cced559f5a7";

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserLoginDTO userLoginDto = new ObjectMapper().readValue(request.getInputStream(), UserLoginDTO.class);
            log.info("{}", userLoginDto);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(), userLoginDto.getPassword()));

        } catch (IOException e) {
            throw new RuntimeException("Login Failed: " + e.getMessage());
        }
    }

    @SneakyThrows
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();
        TabUser tabUser = TabUser.findByUsnDesUsername(user.getUsername());
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SENHA.getBytes());
        String access_token = JWT.create() //
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 1000)) //
                .withIssuer(request.getRequestURL().toString()) //
                .withClaim("usnCod", tabUser.getUsnCod()) //
                .withClaim("usnDesUsername", tabUser.getUsnDesUsername()) //
                .sign(algorithm);
        String refresh_token = JWT.create().withSubject(user.getUsername()) //
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000)) //
                .withIssuer(request.getRequestURL().toString()).sign(algorithm);
        Map<String, Object> tokens = new HashMap<>();
        tokens.put("usnCod", tabUser.getUsnCod());
        tokens.put("usnDesUsername", tabUser.getUsnDesUsername());
        tokens.put("token", access_token);
        tokens.put("refreshToken", refresh_token);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Map<String, String> tokens = new HashMap<>();
        tokens.put("message_error", failed.getMessage());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(UNAUTHORIZED.value());
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }


}
