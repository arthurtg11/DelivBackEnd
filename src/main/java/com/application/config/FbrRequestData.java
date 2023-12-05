package com.application.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor
@Log4j2
public class FbrRequestData {

    public Long getUsnCod() {
        return FbrUtils.getParamJWT("usnCod");
    }

}
