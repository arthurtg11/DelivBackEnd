package com.application.domain;

import com.application.config.ListRequest;
import com.application.config.exceptions.TooManyRollsException;
import com.application.config.validator.*;
import com.application.domain.dto.VotesDTO;
import com.application.repository.TabCandidatosDAO;
import com.application.repository.TabUserDAO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TabCandidatos extends FbrModel {

    @ValidatorLong(requiredOnUpdate = true)
    private Long canCod;

    @ValidatorString(maxSize = 60)
    private String canDesName;

    @ValidatorEnum(enumBase = EVldTipo.class)
    private Long canVldTipo;

    @ValidatorLong
    private Long canNumVotos;

    @ValidatorString(maxSize = 60)
    private String canDesNameVice;

    @ValidatorString(maxSize = 10)
    private String canDesPartido;

    @ValidatorLong
    private Long canNumNumero;

    @ValidatorString(maxSize = 255)
    private String canDesPhoto;

    @ValidatorLong
    private Long urnCod;


    public static Long getCount(ListRequest listRequest) {
        return TabUserDAO.getCount(listRequest);
    }

    public static TabCandidatos findById(Long canCod) throws Exception {
        return TabCandidatosDAO.findById(canCod);
    }

    public static List<TabCandidatos> findList(ListRequest listRequest) {
        return TabCandidatosDAO.findList(listRequest);
    }

    public static TabCandidatos findByUsnDesUsername(String usnDesName) throws TooManyRollsException {
        List<TabCandidatos> list = findList(ListRequest.createListRequest().addFilter("usnDesUsername", usnDesName));

        if (list.size() > 1)
            throw new TooManyRollsException();

        if (!list.isEmpty())
            return list.get(0);

        return null;
    }


    @Override
    protected void safeUpdate() throws Exception {
        TabCandidatosDAO.update(this);
    }

    public enum EVldTipo implements ValidatorEnumInterface {
        SENADOR(1L),
        PRESIDENTE(2L);

        private Long value;

        EVldTipo(Long value) {
            this.value = value;
        }

        @Override
        public Long getValue() {
            return value;
        }
    }
}
