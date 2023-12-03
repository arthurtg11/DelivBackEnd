package com.application.controller;

import com.application.config.FbrRequestData;
import com.application.domain.TabUser;
import com.application.domain.dto.UserInfoDTO;
import com.application.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    public static final String TOKEN_SENHA = "463408a1-54c9-4307-bb1c-6cced559f5a7";

    @Autowired
    private final UserService userService;

    @Autowired
    FbrRequestData requestData;

    @GetMapping("/me")
    public ResponseEntity<UserInfoDTO> getUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String authorizationHeader = request.getHeader(AUTHORIZATION);


        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setStrCod(requestData.getStrCod());
        userInfoDTO.setUsnCod(requestData.getUsnCod());
        TabUser user = TabUser.findById(requestData.getUsnCod());
        userInfoDTO.setUsnDesUsername(user.getUsnDesUsername());

        new ObjectMapper().writeValue(response.getOutputStream(), userInfoDTO);


        return ResponseEntity.ok().body(userInfoDTO);
    }
}


