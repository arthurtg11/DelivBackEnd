package com.application.domain;

import com.application.config.ListRequest;
import com.application.config.exceptions.TooManyRollsException;
import com.application.config.validator.*;
import com.application.repository.TabUrnaDAO;
import com.application.repository.TabUrnaDAO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.Errors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TabUrna extends FbrModel {

    @ValidatorLong(requiredOnUpdate = true)
    private Long urnCod;

    @ValidatorString(maxSize = 60)
    private String urnDesName;

    @ValidatorEnum(enumBase = ESimNao.class)
    private Long urnVldAtiva;


    public static TabUrna findById(Long urnCod) throws Exception {
        return TabUrnaDAO.findById(urnCod);
    }

    @Override
    protected void safeUpdate() throws Exception {
        TabUrnaDAO.update(this);
    }

    public enum ESimNao implements ValidatorEnumInterface {
        NAO(1L),
        SIM(2L);

        private Long value;

        ESimNao(Long value) {
            this.value = value;
        }

        @Override
        public Long getValue() {
            return value;
        }
    }
}
