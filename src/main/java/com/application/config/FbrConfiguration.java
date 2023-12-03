package com.application.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class FbrConfiguration {
    public static String tokenSenha;

    @Autowired
    public void setValidator(FbrConfig fbrConfig) {
        this.tokenSenha = fbrConfig.getTokenSenha();
    }
}
