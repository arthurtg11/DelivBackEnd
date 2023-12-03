package com.application.controller;

import com.application.controller.config.FbrControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class DashboardControllerTest extends FbrControllerTest {

    private static final String BASE = "/api/dashboard";

    public DashboardDTO findByIdAndReturn() throws Exception {
        return resultConvertClass(
                getRequest(BASE)
                        .andExpect(status().isOk()), DashboardDTO.class);
    }

    @Test
    @Rollback
    public void findById() throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("atg123"));
        createAuth();
        findByIdAndReturn();
    }
}














