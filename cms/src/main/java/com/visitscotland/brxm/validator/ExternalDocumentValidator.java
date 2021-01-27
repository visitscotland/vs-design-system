package com.visitscotland.brxm.validator;

import com.visitscotland.brxm.utils.CommonUtils;
import org.onehippo.cms.services.validation.api.ValidationContext;
import org.onehippo.cms.services.validation.api.Validator;
import org.onehippo.cms.services.validation.api.Violation;

import java.util.Optional;

/**
 * jcr:Name = visitscotland:external-document-validator

 */
public class ExternalDocumentValidator implements Validator<String>  {



    public Optional<Violation> validate(ValidationContext context, String value) {
        return CommonUtils.getExtenalDocumentSize(value) == null?Optional.of(context.createViolation()) : Optional.empty();
    }
}