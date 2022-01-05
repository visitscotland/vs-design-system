package com.visitscotland.brxm.validator;

import com.visitscotland.brxm.translation.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.AdditionalMatchers;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.onehippo.cms.services.validation.api.ValidationContext;
import org.onehippo.cms.services.validation.api.Violation;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import static org.hippoecm.repository.api.HippoNodeType.HIPPO_DOCBASE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LinkValidatorTest {

    @Mock
    ValidationContext context;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    SessionFactory mockSessionFactory;

    LinkValidator validator;

    @BeforeEach
    void init(){
        validator = new LinkValidator(mockSessionFactory);
    }

    @Test
    @DisplayName("Validates that links have been selected")
    void LinksEmptyValues() throws RepositoryException {
        Node node = Mockito.mock(Node.class, RETURNS_DEEP_STUBS);

        when(node.getProperty(HIPPO_DOCBASE).getValue().getString()).thenReturn(LinkValidator.EMPTY_DOCUMENT);
        when(context.createViolation("EmptyLink")).thenReturn(mock(Violation.class));

        assertTrue(validator.validate(context, node).isPresent());
    }

    @ParameterizedTest
    @CsvSource({
            "visitscotland:MegalinkItem,visitscotland:Page",
            "visitscotland:OTYML,visitscotland:SharedLink",
            "visitscotland:MadeUpDocument,visitscotland:SharedLink",
            "visitscotland:Day,visitscotland:Stop",
            "visitscotland:VideoLink,visitscotland:Video"
    })
    @DisplayName("VS-2905 - Validates that links are correctly validated depending on the parent")
    void correctValues(String parentType, String childType) throws RepositoryException {
        assertFalse(validator.validate(context, mockLink(parentType, childType, true)).isPresent());
    }

    @ParameterizedTest
    @CsvSource({
            "visitscotland:MegalinkItem,visitscotland:MegalinkItem",
            "visitscotland:MegalinkItem,visitscotland:Video",
            "visitscotland:MegalinkItem,visitscotland:Stop",
            "visitscotland:Day,visitscotland:SharedLink",
            "visitscotland:Day,visitscotland:Video",
            "visitscotland:VideoLink,visitscotland:Stop",
            "visitscotland:VideoLink,visitscotland:Page"
    })
    @DisplayName("VS-2905 - Invalid documents cause a validation exception")
    void incorrectValues(String parentType, String childType) throws RepositoryException {
        assertTrue(validator.validate(context, mockLink(parentType, childType, false)).isPresent());
    }

    /**
     * Mocks a Document that cotains a link, the linked document and stubs any expected behaviour during the validation
     *
     * @param parentType JCR Type that act as a container (i.e. visitscotland:VideoLink)
     * @param childType JCR Type that represent the linked document (i.e. visitscotland:Video)
     * @param expected true when a validation exception is not expected
     *
     * @return Node to be validated against the validator
     */
    private Node mockLink(String parentType, String childType, boolean expected) throws  RepositoryException{
        Node parentNode = Mockito.mock(Node.class, withSettings().lenient().defaultAnswer(RETURNS_DEEP_STUBS));
        Node childNode = Mockito.mock(Node.class, withSettings().lenient());
        boolean isDefault = !LinkValidator.DAY.equals(parentType) && !LinkValidator.VIDEO.equals(parentType);

        when(parentNode.getProperty(HIPPO_DOCBASE).getValue().getString()).thenReturn("NODE-ID");
        lenient().when(parentNode.getParent().isNodeType(AdditionalMatchers.not(eq(parentType)))).thenReturn(false);
        if (!isDefault){
            when(parentNode.getParent().isNodeType(parentType)).thenReturn(true);
        }

        when(childNode.isNodeType(childType)).thenReturn(true);
        when(childNode.isNodeType(AdditionalMatchers.not(eq(childType)))).thenReturn(false);

        if (expected) {
            when(mockSessionFactory.getHippoNodeByIdentifier("NODE-ID")).thenReturn(childNode);
        } else if (isDefault) {
            when(context.createViolation()).thenReturn(mock(Violation.class));
        } else {
            when(context.createViolation(any(String.class))).thenReturn(mock(Violation.class));
        }

        return parentNode;
    }
}
