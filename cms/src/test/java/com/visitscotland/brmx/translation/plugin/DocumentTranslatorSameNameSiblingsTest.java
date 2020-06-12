package com.visitscotland.brmx.translation.plugin;

import org.hippoecm.addon.workflow.WorkflowSNSException;
import org.hippoecm.frontend.translation.components.document.FolderTranslation;
import org.hippoecm.repository.HippoStdNodeType;
import org.hippoecm.repository.api.HippoNode;
import org.hippoecm.repository.api.HippoNodeType;
import org.hippoecm.repository.translation.HippoTranslatedNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DocumentTranslatorSameNameSiblingsTest {
    private DocumentTranslator translator;
    @Mock
    private Session mockSession;
    @Mock
    private HippoTranslatedNodeFactory mockNodeFactory;
    @Mock
    private TranslatedFolderFactory mockFolderFactory;
    private List<FolderTranslation> folders;

    @BeforeEach
    public void beforeEach() {
        folders = new LinkedList<>();
        translator = new DocumentTranslator(mockNodeFactory, mockFolderFactory);
    }

    @Test
    public void avoidSameNameSiblings_deepestTranslatedTargetNode_is_null() throws Exception {
        // This tests that a missing translated folder returns without error.
        // It is replicating existing functionality and is a case that should never happen.
        FolderTranslation rootFolder = createMockFolderTranslation("root");
        FolderTranslation level1 = createMockFolderTranslation("level1");
        FolderTranslation level2 = createMockFolderTranslation("level2");
        folders.add(rootFolder);
        folders.add(level1);
        folders.add(level2);

        createDeepestTranslatedSourceNode("level1", "fr", null);

        translator.avoidSameNameSiblings(mockSession, 1, "fr", folders);
    }

    @Test
    public void avoidSameNameSiblings_deepestTranslatedTargetNode_exists() throws Exception {
        FolderTranslation rootFolder = createMockFolderTranslation("root");
        FolderTranslation level1 = createMockFolderTranslation("level1");
        FolderTranslation level2 = createHighestUntranslatedFolder("level2", "targetUrl", "targetName");
        folders.add(rootFolder);
        folders.add(level1);
        folders.add(level2);

        Node deepestTranslatedTargetNode = createDeepestTranslatedTargetNode("targetUrl", true);
        createDeepestTranslatedSourceNode("level1", "fr", deepestTranslatedTargetNode);

        assertThrows(WorkflowSNSException.class,
                () -> translator.avoidSameNameSiblings(mockSession, 1, "fr", folders));
    }

    @Test
    public void avoidSameNameSiblings_withNoSiblings() throws Exception {
        FolderTranslation rootFolder = createMockFolderTranslation("root");
        FolderTranslation level1 = createMockFolderTranslation("level1");
        FolderTranslation level2 = createHighestUntranslatedFolder("level2", "targetUrl", "targetName");
        folders.add(rootFolder);
        folders.add(level1);
        folders.add(level2);

        Node deepestTranslatedTargetNode = createDeepestTranslatedTargetNode("targetUrl", false);
        createDeepestTranslatedSourceNode("level1", "fr", deepestTranslatedTargetNode);

        NodeIterator mockIterator = mock(NodeIterator.class);
        when(deepestTranslatedTargetNode.getNodes()).thenReturn(mockIterator);
        when(mockIterator.hasNext()).thenReturn(false);

        translator.avoidSameNameSiblings(mockSession, 1, "fr", folders);
    }

    @Test
    public void avoidSameNameSiblings_allDifferentNames() throws Exception {
        FolderTranslation rootFolder = createMockFolderTranslation("root");
        FolderTranslation level1 = createMockFolderTranslation("level1");
        FolderTranslation level2 = createHighestUntranslatedFolder("level2", "targetUrl", "targetName");
        folders.add(rootFolder);
        folders.add(level1);
        folders.add(level2);

        Node deepestTranslatedTargetNode = createDeepestTranslatedTargetNode("targetUrl", false);
        createDeepestTranslatedSourceNode("level1", "fr", deepestTranslatedTargetNode);

        addSiblingsToNode(deepestTranslatedTargetNode, "targetName", false);

        translator.avoidSameNameSiblings(mockSession, 1, "fr", folders);
    }

    @Test
    public void avoidSameNameSiblings_withDuplicateName() throws Exception {
        FolderTranslation rootFolder = createMockFolderTranslation("root");
        FolderTranslation level1 = createMockFolderTranslation("level1");
        FolderTranslation level2 = createHighestUntranslatedFolder("level2", "targetUrl", "targetName");
        folders.add(rootFolder);
        folders.add(level1);
        folders.add(level2);

        Node deepestTranslatedTargetNode = createDeepestTranslatedTargetNode("targetUrl", false);
        createDeepestTranslatedSourceNode("level1", "fr", deepestTranslatedTargetNode);

        addSiblingsToNode(deepestTranslatedTargetNode, "targetName", true);

        assertThrows(WorkflowSNSException.class, () -> translator.avoidSameNameSiblings(mockSession, 1, "fr", folders));
    }

    private void addSiblingsToNode(Node targetNode, String targetName, boolean hasMatchingSibling) throws Exception {
        // Add a node with a getDisplayName equals to targetName if hasMatchingSibling is true
        NodeIterator mockNodeIterator = mock(NodeIterator.class);
        when(targetNode.getNodes()).thenReturn(mockNodeIterator);
        final int maxNumberOfCalls = 5;
        Answer<Boolean> mockNodeIteratorAnswer = new Answer<Boolean>() {
            int numberOfCalls = 0;
            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                numberOfCalls++;
                return numberOfCalls != maxNumberOfCalls;
            }
        };
        when(mockNodeIterator.hasNext()).thenAnswer(mockNodeIteratorAnswer);

        Answer<HippoNode> mockNodeIteratorNodeAnswer = new Answer<HippoNode>() {
            int numberOfCalls = 0;
            @Override
            public HippoNode answer(InvocationOnMock invocation) throws Throwable {
                numberOfCalls++;
                HippoNode mockNode = mock(HippoNode.class);
                // Splitting the nodes between folders and handles, the first will be neither
                if(numberOfCalls == 0) {
                    when(mockNode.isNodeType(eq(HippoStdNodeType.NT_FOLDER))).thenReturn(false);
                    when(mockNode.isNodeType(eq(HippoNodeType.NT_HANDLE))).thenReturn(false);
                } else {
                    if (numberOfCalls % 2 == 0) {
                        when(mockNode.isNodeType(eq(HippoStdNodeType.NT_FOLDER))).thenReturn(true);
                    } else {
                        when(mockNode.isNodeType(eq(HippoStdNodeType.NT_FOLDER))).thenReturn(false);
                        when(mockNode.isNodeType(eq(HippoNodeType.NT_HANDLE))).thenReturn(true);
                    }
                    if (numberOfCalls == maxNumberOfCalls - 1 && hasMatchingSibling) {
                        when(mockNode.getDisplayName()).thenReturn(targetName);
                    } else {
                        when(mockNode.getDisplayName()).thenReturn(targetName + "-different");
                    }
                }
                return mockNode;
            }
        };
        when(mockNodeIterator.nextNode()).thenAnswer(mockNodeIteratorNodeAnswer);
    }

    private Node createDeepestTranslatedTargetNode(String targetUrl, boolean targetExists) throws Exception {
        Node deepestTranslatedTargetNode = mock(Node.class);
        when(deepestTranslatedTargetNode.hasNode(eq(targetUrl))).thenReturn(targetExists);
        return deepestTranslatedTargetNode;
    }

    private FolderTranslation createHighestUntranslatedFolder(String nodeId, String targetUrlName, String targetLocalizedName) {
        FolderTranslation highestUntranslatedItem = createMockFolderTranslation(nodeId);
        when(highestUntranslatedItem.getUrlfr()).thenReturn(targetUrlName);
        when(highestUntranslatedItem.getNamefr()).thenReturn(targetLocalizedName);
        return highestUntranslatedItem;
    }

    private Node createDeepestTranslatedSourceNode(String sourceNodeIdentifier,
                                                   String targetLanguage,
                                                   Node translatedTargetNode) throws Exception {
        Node sourceNode = mock(Node.class);
        HippoTranslatedNode mockHippoTranslatedNode = mock(HippoTranslatedNode.class);
        when(mockSession.getNodeByIdentifier(sourceNodeIdentifier)).thenReturn(sourceNode);
        when(mockNodeFactory.createFromNode(same(sourceNode))).thenReturn(mockHippoTranslatedNode);
        when(mockHippoTranslatedNode.getTranslation(eq(targetLanguage))).thenReturn(translatedTargetNode);
        return sourceNode;
    }

    private FolderTranslation createMockFolderTranslation(String nodeId) {
        FolderTranslation mockFolder =  mock(FolderTranslation.class);
        lenient().when(mockFolder.getId()).thenReturn(nodeId);
        return mockFolder;
    }
}
