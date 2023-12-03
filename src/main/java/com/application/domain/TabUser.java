package com.application.domain;

import com.application.config.FbrUtils;
import com.application.config.ListRequest;
import com.application.config.exceptions.TooManyRollsException;
import com.application.config.validator.*;
import com.application.domain.enums.EVldSituacao;
import com.application.repository.TabUserDAO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.Errors;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TabUser extends FbrModel {

    @ValidatorLong(requiredOnUpdate = true)
    private Long usnCod;

    @ValidatorString(maxSize = 60)
    private String usnDesName;

    @ValidatorString(maxSize = 255)
    private String usnDesUsername;

    @ValidatorString(maxSize = 60)
    private String usnDesPassword;

    @ValidatorEnum(enumBase = EVldCliFuncionario.class)
    private Long usnVldClifun;

    @ValidatorString(maxSize = 30)
    private String usnDesPhone;

    @ValidatorEnum(enumBase = EVldSituacao.class)
    private Long usnVldSituacao;

    @ValidatorDate(required = false)
    private Date usnDtaCad;

    private byte[] usnBitPhoto;

    public static Long getCount(ListRequest listRequest) {
        return TabUserDAO.getCount(listRequest);
    }

    public static TabUser findById(Long usnCod) throws Exception {
        return TabUserDAO.findById(usnCod);
    }

    public static List<TabUser> findList(ListRequest listRequest) {
        return TabUserDAO.findList(listRequest);
    }

    public static TabUser findByUsnDesUsername(String usnDesName) throws TooManyRollsException {
        List<TabUser> list = findList(ListRequest.createListRequest().addFilter("usnDesUsername", usnDesName));

        if (list.size() > 1)
            throw new TooManyRollsException();

        if (!list.isEmpty())
            return list.get(0);

        return null;
    }

    @Override
    protected void safeInsert() throws Exception {
        TabUserDAO.insert(this);
    }

    @Override
    protected void safeUpdate() throws Exception {
        TabUserDAO.update(this);
    }

    @Override
    public void validateInsert(Errors errors) throws Exception {
        validateInsertUpdate(errors);

        var result = this.findByUsnDesUsername(this.usnDesUsername);

        if (result != null)
            FbrValidateUtils.addErros(errors, "Usuário já cadastrado!");

        if (!FbrUtils.isEmailValid(this.usnDesUsername))
            FbrValidateUtils.addErros(errors, "O E-mail '{}' é inválido!", this.usnDesUsername);
    }

    @Override
    public void validateUpdate(Errors errors) throws Exception {
        validateInsertUpdate(errors);

        TabUser tabUser = findById(this.usnCod);
    }

    private void validateInsertUpdate(Errors errors) throws Exception {
        if (!FbrUtils.isPhoneValid(this.usnDesPhone))
            FbrValidateUtils.addErros(errors, "O Número de Telefone '{}' é inválido!", this.usnDesPhone);
    }

    public enum EVldCliFuncionario implements ValidatorEnumInterface {
        CLIENTE(1L),
        FUNCIONARIO(2L),
        VIRTUAL(3L);

        private Long value;

        EVldCliFuncionario(Long value) {
            this.value = value;
        }

        @Override
        public Long getValue() {
            return value;
        }
    }
}
