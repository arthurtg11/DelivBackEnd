package com.application.controller.config;

import com.application.config.FbrUtils;
import com.application.domain.FbrModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@SpringBootTest
@Transactional
public abstract class FbrControllerTest {
    public static final String TOKEN_SENHA = "463408a1-54c9-4307-bb1c-6cced559f5a7";
    private String username;
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders//
                .webAppContextSetup(context)//
                .build();
    }

    public MockMvc getMvc() {
        return mockMvc;
    }

    protected void createAuth() {
        this.username = "admin";
    }

    protected void createAuth(String username) {
        this.username = username;
    }

    protected String createAuthReq() {
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SENHA.getBytes());
        String access_token = JWT.create().withSubject(this.username) //
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 1000)) //
                .withIssuer("teste") //
                .withClaim("usnCod", 1L) //
                .sign(algorithm);

        return "Bearer " + access_token;
    }

    protected static <T> T resultConvertClass(ResultActions rt, Class<T> t) throws UnsupportedEncodingException, JsonProcessingException {
        var json = rt.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        return mapper.readValue(json, t);
    }

    public ResultActions perform(MockHttpServletRequestBuilder requestBuilder) throws Exception {
        return perform(requestBuilder, null);
    }

    public ResultActions perform(MockHttpServletRequestBuilder requestBuilder, Object body) throws Exception {
        if (this.username != null)
            requestBuilder.header("Authorization", createAuthReq());
        requestBuilder.contentType(MediaType.APPLICATION_JSON);
        requestBuilder.accept(MediaType.APPLICATION_JSON);
        if (body != null) requestBuilder.content(FbrUtils.asJsonString(body));
        return getMvc().perform(requestBuilder);
    }

    public ResultActions getRequest(String url) throws Exception {
        var requestBuilder = MockMvcRequestBuilders //
                .get(url);

        return perform(requestBuilder);
    }

    public ResultActions postRequest(String url, Object body) throws Exception {
        var requestBuilder = MockMvcRequestBuilders //
                .post(url);

        return perform(requestBuilder, body);
    }

    public ResultActions putRequest(String url) throws Exception {
        return putRequest(url, null);
    }

    public ResultActions putRequest(String url, Object body) throws Exception {
        var requestBuilder = MockMvcRequestBuilders //
                .put(url);

        return perform(requestBuilder, body);
    }

    public ResultActions deleteRequest(String url) throws Exception {
        var requestBuilder = MockMvcRequestBuilders //
                .delete(url);

        return perform(requestBuilder);
    }
}
