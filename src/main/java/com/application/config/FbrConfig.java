package com.application.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
public class FbrConfig {
    /* Novas variaveis aqui, adicionar na classe FbrConfiguration */

    @Value("${fbr.config.token.senha}")
    private String tokenSenha;

    @Value("${cloudinary.cloud.name}")
    private String cloudinaryName;

    @Value("${cloudinary.apiKey}")
    private String cloudinaryApiKey;

    @Value("${cloudinary.secretKey}")
    private String cloudinarySecretKey;

    @Value("${cloudinary.url}")
    private String cloudinaryUrl;

}
