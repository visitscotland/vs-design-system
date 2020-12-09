package com.visitscotland.brmx.validator;

import com.visitscotland.brmx.beans.ExternalDocument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.onehippo.cms.services.validation.api.ValidationContext;
import org.onehippo.cms.services.validation.api.Violation;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExternalDocumentValidatorTest {

    @Mock
    ValidationContext context;

    @Test
    @DisplayName("If the length of the field is bigger than the max allowed, an error should be displayed")
    void externalDocumentIncorrect() throws RepositoryException {
        Node node = Mockito.mock(Node.class, RETURNS_DEEP_STUBS);
        ExternalDocumentValidator validator = new ExternalDocumentValidator();

        when(context.createViolation()).thenReturn(mock(Violation.class));
        String value = "https://www.visitscotland.com/ebrochures/en/what-to-see-and-do/perthshireanddundee";
        assertTrue(validator.validate(context, value).isPresent());
    }

}
