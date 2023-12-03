package com.application.domain;

import com.application.config.validator.FbrValidateUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import javax.validation.ValidationException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public abstract class FbrModel {

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface IgnoreField {
    }

    protected void safeInsert() throws Exception {
        throw new UnsupportedOperationException("Insert not implemented");
    }

    protected void safeUpdate() throws Exception {
        throw new UnsupportedOperationException("Update not implemented");
    }

    protected void safeDelete() throws Exception {
        throw new UnsupportedOperationException("Delete not implemented");
    }

    public void validateInsert(Errors errors) throws Exception {
    }

    public void validateUpdate(Errors errors) throws Exception {
    }

    public void validateDelete(Errors errors) throws Exception {
    }

    final public <T extends FbrModel> T insert() throws Exception {
        Errors errors = new BeanPropertyBindingResult(this, this.getClass().getSimpleName());
        validateInsert(errors);
        if (errors.hasErrors())
            throw new ValidationException(FbrValidateUtils.parserErros(errors));

        safeInsert();

        return (T) this;
    }

    final public <T extends FbrModel> T update() throws Exception {
        Errors errors = new BeanPropertyBindingResult(this, this.getClass().getSimpleName());
        validateUpdate(errors);
        if (errors.hasErrors())
            throw new ValidationException(FbrValidateUtils.parserErros(errors));

        safeUpdate();

        return (T) this;
    }

    final public void delete() throws Exception {
        Errors errors = new BeanPropertyBindingResult(this, this.getClass().getSimpleName());
        validateDelete(errors);
        if (errors.hasErrors())
            throw new ValidationException(FbrValidateUtils.parserErros(errors));

        safeDelete();
    }
}
