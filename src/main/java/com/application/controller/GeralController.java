package com.application.controller;

import com.application.config.FbrRequestData;
import com.application.domain.TabUrna;
import com.application.domain.TabUser;
import com.application.domain.dto.UserInfoDTO;
import com.application.service.UrnaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api/configNoAuth")
@RequiredArgsConstructor
public class GeralController {


    @Autowired
    private final UrnaService userService;

    @Autowired
    FbrRequestData requestData;

    @GetMapping("/me")
    public ResponseEntity<UserInfoDTO> getUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUsnCod(requestData.getUsnCod());
        TabUser user = TabUser.findById(requestData.getUsnCod());
        userInfoDTO.setUsnDesUsername(user.getUsnDesUsername());

        new ObjectMapper().writeValue(response.getOutputStream(), userInfoDTO);


        return ResponseEntity.ok().body(userInfoDTO);
    }

    @GetMapping("/urna/stats")
    public ResponseEntity<TabUrna> getUrnaStats() throws Exception {
        return ResponseEntity.ok().body(TabUrna.findById(1L));
    }
}


