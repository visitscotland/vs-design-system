package com.visitscotland.brmx.translation.plugin;

import com.visitscotland.brmx.beans.TranslationLinkContainer;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import javax.jcr.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DocumentTranslatorGetChildTranslatableLinkNodesTest {
    private DocumentTranslator documentTranslator;
    @Mock
    private ExampleTranslationLinkContainer mockSourceDocument;
    @Mock
    private Node mockSourceNode;
    @Mock
    private SessionFactory mockSessionFactory;
    @Mock
    private HippoTranslatedNodeFactory mockHippoTranslatedNodeFactory;
    @Mock
    private JcrDocumentFactory mockJcrDocumentFactory;
    @Mock
    private ChangeSetFactory mockChangeSetFactory;

    @BeforeEach
    public void beforeEach() {
        documentTranslator = new DocumentTranslator(mockHippoTranslatedNodeFactory,
                                                    mockSessionFactory,
                                                    mockJcrDocumentFactory,
                                                    mockChangeSetFactory);
        lenient().when(mockSourceDocument.getNode()).thenReturn(mockSourceNode);
    }

    @Test
    public void getChildTranslatableLinkNodes_emptyNameList() throws RepositoryException {
        // When the TranslationLinkContainer returns no child types it does not cause an error,
        // just returns an empty list.
        when(mockSourceDocument.getTranslatableLinkNames()).thenReturn(new String[] {});

        List<Node> nodeList = documentTranslator.getChildTranslatableLinkNodes(mockSourceDocument);

        assertNotNull(nodeList);
        assertTrue(nodeList.isEmpty());
    }

    @Test
    public void getChildTranslatableLinkNodes_noneMatch() throws RepositoryException {
        // When the TranslationLinkContainer contains a type, but none of the children match it.
        when(mockSourceDocument.getTranslatableLinkNames()).thenReturn(new String[] {"visitscotland:stops"});
        NodeIterator emptyIterator = createNodeIterator();
        when(mockSourceNode.getNodes(eq("visitscotland:stops"))).thenReturn(emptyIterator);
        List<Node> nodeList = documentTranslator.getChildTranslatableLinkNodes(mockSourceDocument);

        assertNotNull(nodeList);
        assertTrue(nodeList.isEmpty());
    }

    @Test
    public void getChildTranslatableLinkNodes_matching() throws Exception {
        // When the TranslationLinkContainer contains a type, and there are matching children.
        when(mockSourceDocument.getTranslatableLinkNames()).thenReturn(new String[] {"visitscotland:stops"});
        Node mockChildNode = mock(Node.class);
        Property mockChildProperty = mock(Property.class);
        when(mockChildNode.getProperty(eq("hippo:docbase"))).thenReturn(mockChildProperty);
        when(mockChildProperty.getString()).thenReturn("LINK_ID");
        NodeIterator childIterator = createNodeIterator(mockChildNode);
        when(mockSourceNode.getNodes(eq("visitscotland:stops"))).thenReturn(childIterator);

        List<Node> nodeList = documentTranslator.getChildTranslatableLinkNodes(mockSourceDocument);

        assertNotNull(nodeList);
        assertFalse(nodeList.isEmpty());
        assertSame(mockChildNode, nodeList.get(0));
    }

    private NodeIterator createNodeIterator(Node... nodes) {
        List<Node> nodeList = Arrays.asList(nodes);
        final Iterator<Node> nodeIterator = nodeList.iterator();
        NodeIterator mockNodeIterator = mock(NodeIterator.class);
        when(mockNodeIterator.hasNext()).thenAnswer(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                return nodeIterator.hasNext();
            }
        });
        lenient().when(mockNodeIterator.nextNode()).thenAnswer(new Answer<Node>() {
            @Override
            public Node answer(InvocationOnMock invocation) throws Throwable {
                return nodeIterator.next();
            }
        });
        return mockNodeIterator;
    }

    public abstract class ExampleTranslationLinkContainer implements HippoBean, TranslationLinkContainer {

    }
}
