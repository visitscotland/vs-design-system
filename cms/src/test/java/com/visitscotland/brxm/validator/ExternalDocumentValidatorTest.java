package com.visitscotland.brxm.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.onehippo.cms.services.validation.api.ValidationContext;
import org.onehippo.cms.services.validation.api.Violation;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExternalDocumentValidatorTest {

    @Mock
    ValidationContext context;

    @Test
    @DisplayName("The URL is not valid if it does not contain pdf extension")
    void externalDocumentIncorrect() {
        com.visitscotland.brxm.validator.ExternalDocumentValidator validator = new com.visitscotland.brxm.validator.ExternalDocumentValidator();

        when(context.createViolation()).thenReturn(mock(Violation.class));
        String value = "https://www.visitscotland.com/ebrochures/en/what-to-see-and-do/perthshireanddundee";
        assertTrue(validator.validate(context, value).isPresent());
    }

}
