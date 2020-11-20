package com.visitscotland.brmx.validator;

import com.visitscotland.brmx.beans.Coordinates;
import com.visitscotland.brmx.beans.Image;
import com.visitscotland.brmx.translation.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.onehippo.cms.services.validation.api.ValidationContext;
import org.onehippo.cms.services.validation.api.ValidationContextException;
import org.onehippo.cms.services.validation.api.Violation;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import java.util.Locale;
import java.util.Optional;

import static org.hippoecm.repository.api.HippoNodeType.HIPPO_DOCBASE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageValidatorTest {

    @Mock
    ValidationContext context;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    SessionFactory mockSessionFactory;

    @Test
    @DisplayName("Validates that the image has credit and alt-text added")
    void correctValues() {
        //Validates that correct values are accepted
        ImageValidator validator = new ImageValidator(mockSessionFactory);

        Node node = Mockito.mock(Node.class, RETURNS_DEEP_STUBS);
//        Node galleryNode = Mockito.mock(Node.class, RETURNS_DEEP_STUBS);
        Node galleryNode = Mockito.mock(Node.class, RETURNS_DEEP_STUBS);
        Node childNode = Mockito.mock(Node.class, RETURNS_DEEP_STUBS);

        try {
            when(node.getProperty(HIPPO_DOCBASE).getValue().getString()).thenReturn("imageName");
//            when(mockSessionFactory.getJcrSession().getNodeByIdentifier("imageName")).thenReturn(galleryNode);
//            when(galleryNode.getNode(galleryNode.getName())).thenReturn(childNode);

//            when(mockSessionFactory.getJcrSession().getNodeByIdentifier("imageName").getNode("imageName")).thenReturn(childNode);

            when(mockSessionFactory.getHippoNodeByIdentifier("imageName")).thenReturn(childNode);
            when(childNode.hasProperty(Image.CREDIT)).thenReturn(true);
            when(childNode.hasProperty(Image.ALT_TEXT)).thenReturn(true);
            assertFalse(validator.validate(context,node).isPresent());
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    private Node mockImage(String credit, String altText) {
        Node node = Mockito.mock(Node.class, RETURNS_DEEP_STUBS);
        try {

            when(node.hasProperty(Image.CREDIT)).thenReturn(true);
            when(node.hasProperty(Image.ALT_TEXT)).thenReturn(true);
            when(node.getProperty(Image.CREDIT).getString()).thenReturn(credit);
            when(node.getProperty(Image.ALT_TEXT).getString()).thenReturn(altText);
        } catch (RepositoryException e) {
            //This cannot happen
            e.printStackTrace();
        }

        return node;
    }
}
