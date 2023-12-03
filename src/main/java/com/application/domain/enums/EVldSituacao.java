package com.application.domain.enums;

import com.application.config.validator.ValidatorEnumInterface;

public enum EVldSituacao implements ValidatorEnumInterface {
    ATIVO(1L),
    INATIVO(2L);

    private Long value;

    EVldSituacao(Long value) {
        this.value = value;
    }

    @Override
    public Long getValue() {
        return value;
    }
}