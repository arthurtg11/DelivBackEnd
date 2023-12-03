package com.application.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.CaseFormat;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FbrUtils {
    public static String convertJavaNamesToTable(String name) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, name);
    }

    public static String convertTableNamesToJava(String name) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, name);
    }

    public static String extractOperator(String key) {
        Pattern pattern = Pattern.compile("#(.*)");
        Matcher matcher = pattern.matcher(key);

        String result = "EQ";
        if (matcher.find())
            result = matcher.group(1);

        return EOperadorSql.valueOf(result).getValue();
    }

    public static Long getParamJWT(String field) {
        final String TOKEN_SENHA = "463408a1-54c9-4307-bb1c-6cced559f5a7";
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String token = request.getHeader("Authorization");
        if (token == null)
            return null;

        token = token.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SENHA.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getClaims().get(field).asLong();
    }

    public static boolean isPhoneValid(String s) {
        if (s == null)
            return true;

        Pattern p = Pattern.compile("(\\([\\d]{2}\\)|[\\d]{2}) ?[\\d]? ?[\\d]{4}-? ?[\\d]{4}$");
        Matcher m = p.matcher(s);
        return (m.matches());
    }

    public static boolean isEmailValid(String s) {
        if (s == null)
            return true;

        Pattern p = Pattern.compile("^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(s);
        return (m.matches());
    }

    public static String format(String message, Object... params) {
        return ParameterizedMessage.format(message, params);
    }

    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, String> createConfigCoudinary(String cloudName, String apiKey, String secretKey) {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", secretKey);
        return config;
    }
}
