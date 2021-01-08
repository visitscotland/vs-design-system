package com.visitscotland.brxm.validator;

import com.visitscotland.brxm.beans.*;
import com.visitscotland.brxm.translation.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
class LinkImageValidatorTest {

    @Mock
    ValidationContext context;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    SessionFactory mockSessionFactory;

    @Test
    @DisplayName("Shared links, if the product is not a DMS and no image is provided, error")
    void sharedLinkNoDms_noImageProvided() throws RepositoryException {
        LinkImageValidator validator = new LinkImageValidator(mockSessionFactory);

        Node node = Mockito.mock(Node.class,withSettings().lenient());
        when(node.hasNode("visitscotland:image")).thenReturn(true);

        Node imageNode = mockChildNode  (node,true, false,false);
        when(node.getNode("visitscotland:image")).thenReturn(imageNode);
        when(imageNode.getProperty(HIPPO_DOCBASE).getValue().getString()).thenReturn(LinkImageValidator.EMPTY_IMAGE);

        when(context.createViolation()).thenReturn(mock(Violation.class));

        assertTrue(validator.validate(context, node).isPresent());
    }

    @Test
    @DisplayName("Shared links, valid image")
    void sharedLinkNoDms_validImage() throws RepositoryException {
        LinkImageValidator validator = new LinkImageValidator(mockSessionFactory);

        Node node = Mockito.mock(Node.class,withSettings().lenient());
        when(node.hasNode("visitscotland:image")).thenReturn(true);

        Node imageNode = mockChildNode  (node,true, false,false);
        when(node.getNode("visitscotland:image")).thenReturn(imageNode);
        when(imageNode.getProperty(HIPPO_DOCBASE).getValue().getString()).thenReturn("ImageName");

        assertFalse(validator.validate(context, node).isPresent());
    }

    @Test
    @DisplayName("Stop, if the product is not a DMS and no image is provided, error")
    void stopNoDms_noImageProvided() throws RepositoryException {
        LinkImageValidator validator = new LinkImageValidator(mockSessionFactory);

        Node node = Mockito.mock(Node.class,withSettings().lenient());
        when(node.hasNode("visitscotland:image")).thenReturn(true);

        Node imageNode = mockChildNode  (node,false, true,false);
        when(node.getNode("visitscotland:image")).thenReturn(imageNode);
        when(imageNode.getProperty(HIPPO_DOCBASE).getValue().getString()).thenReturn(LinkImageValidator.EMPTY_IMAGE);

        when(context.createViolation()).thenReturn(mock(Violation.class));

        assertTrue(validator.validate(context, node).isPresent());
    }

    @Test
    @DisplayName("Stops, valid image")
    void stopNoDms_validImage() throws RepositoryException {
        LinkImageValidator validator = new LinkImageValidator(mockSessionFactory);

        Node node = Mockito.mock(Node.class,withSettings().lenient());
        when(node.hasNode("visitscotland:image")).thenReturn(true);

        Node imageNode = mockChildNode  (node,false, true,false);
        when(node.getNode("visitscotland:image")).thenReturn(imageNode);
        when(imageNode.getProperty(HIPPO_DOCBASE).getValue().getString()).thenReturn("ImageName");

        assertFalse(validator.validate(context, node).isPresent());
    }

    @Test
    @DisplayName("Listicle, if the product is not a DMS and no image is provided, error")
    void ListicleNoDms_noImageProvided() throws RepositoryException {
        LinkImageValidator validator = new LinkImageValidator(mockSessionFactory);

        Node node = Mockito.mock(Node.class,withSettings().lenient());
        when(node.hasNode("visitscotland:image")).thenReturn(false);
        Node childNode = mockChildNode  (node,false, false,true);

        when(node.hasNode(ListicleItem.IMAGES)).thenReturn(true);
        Node imageNode = mockListicleNode(node);

        when(context.createViolation()).thenReturn(mock(Violation.class));

        assertTrue(validator.validate(context, node).isPresent());
    }

    @Test
    @DisplayName("Listicle, if the product is not a DMS and no image conten block is added, error")
    void ListicleNoDms_noContentblockImagesProvided() throws RepositoryException {
        LinkImageValidator validator = new LinkImageValidator(mockSessionFactory);

        Node node = Mockito.mock(Node.class,withSettings().lenient());
        when(node.hasNode("visitscotland:image")).thenReturn(false);
        Node childNode = mockChildNode  (node,false, false,true);

        when(node.hasNode(ListicleItem.IMAGES)).thenReturn(false);

        when(context.createViolation()).thenReturn(mock(Violation.class));

        assertTrue(validator.validate(context, node).isPresent());
    }

    @Test
    @DisplayName("Image is valid oif Instagram is provided")
    void ListicleNoDms_InstagramProvided() throws RepositoryException {
        LinkImageValidator validator = new LinkImageValidator(mockSessionFactory);


        Node node = Mockito.mock(Node.class,withSettings().lenient());
        when(node.hasNode("visitscotland:image")).thenReturn(false);
        Node childNode = mockChildNode  (node,false, false,true);

        when(node.hasNode(ListicleItem.IMAGES)).thenReturn(true);
        Node imageNode = mockListicleNode(node);

        when(imageNode.hasProperty(InstagramImage.CAPTION)).thenReturn(true);

        assertFalse(validator.validate(context, node).isPresent());
    }

    private Node mockChildNode (Node node , boolean sharedLink, boolean stop, boolean listicle) {
        Node imageNode = Mockito.mock(Node.class, RETURNS_DEEP_STUBS);
        try {
            when(node.hasNode(SharedLink.LINK_TYPES)).thenReturn(sharedLink);
            when(node.hasNode(Stop.PRODUCTS)).thenReturn(stop);
            when(node.hasNode(ListicleItem.PRODUCT)).thenReturn(listicle);

            Node childNode = Mockito.mock(Node.class, RETURNS_DEEP_STUBS);
            Node auxNode = Mockito.mock(Node.class, RETURNS_DEEP_STUBS);

            when(node.getNode(SharedLink.LINK_TYPES)).thenReturn(auxNode);
            when(node.getNode(Stop.PRODUCTS)).thenReturn(auxNode);
            when(node.getNode(ListicleItem.PRODUCT)).thenReturn(auxNode);

            when(auxNode.getIdentifier()).thenReturn("imageName");

            when(mockSessionFactory.getJcrSession().getNodeByIdentifier("imageName")).thenReturn(childNode);


        } catch (RepositoryException e) {
            //This cannot happen
            e.printStackTrace();
        }

        return imageNode;
    }

    private Node mockListicleNode (Node node) {
        Node imageNode = Mockito.mock(Node.class,withSettings().lenient());
        try {
            when(node.hasNode(ListicleItem.IMAGES)).thenReturn(true);

            Node auxImageNode = Mockito.mock(Node.class);
            when (node.getNode(ListicleItem.IMAGES)).thenReturn(auxImageNode);
            when(auxImageNode.getIdentifier()).thenReturn("imageName");
            when(mockSessionFactory.getJcrSession().getNodeByIdentifier("imageName")).thenReturn(imageNode);

        } catch (RepositoryException e) {
            //This cannot happen
            e.printStackTrace();
        }

        return imageNode;
    }

}
