package com.visitscotland.brmx.translation.plugin;

import org.hippoecm.addon.workflow.WorkflowSNSException;
import org.hippoecm.frontend.translation.ILocaleProvider;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChangeSetCheckForSameNameSiblings {
    private ChangeSet changeSet;
    @Mock
    private Session mockSession;
    @Mock
    private ILocaleProvider.HippoLocale mockLocale;

    @BeforeEach
    public void beforeEach() {
        when(mockLocale.getName()).thenReturn("fr");
        changeSet = spy(new ChangeSet(mockLocale));
    }

    @Test
    public void checkForSameNameSiblings_deepestTranslatedTargetNode_is_null() throws Exception {
        // This tests that a missing translated folder returns without error.
        // It is replicating existing functionality and is a case that should never happen.
        FolderTranslation rootFolder = createMockFolderTranslation("root", false);
        FolderTranslation level1 = createMockFolderTranslation("level1", false);
        FolderTranslation level2 = createMockFolderTranslation("level2", true);
        changeSet.getFolders().add(rootFolder);
        changeSet.getFolders().add(level1);
        changeSet.getFolders().add(level2);

        createDeepestTranslatedSourceNode("level1");

        assertThrows(WorkflowSNSException.class, () -> changeSet.checkForSameNameSiblings(mockSession));
    }

    @Test
    public void checkForSameNameSiblings_same_name_folder_sibling_matches_url() throws Exception {
        // Tests that an exception is thrown when a node with the same url name already exists
        FolderTranslation rootFolder = createMockFolderTranslation("root", false);
        FolderTranslation level1 = createMockFolderTranslation("level1", false);
        FolderTranslation level2 = createHighestUntranslatedFolder("level2", "targetUrl", "targetName");
        changeSet.getFolders().add(rootFolder);
        changeSet.getFolders().add(level1);
        changeSet.getFolders().add(level2);

        Node translatedTargetFolder = createDeepestTranslatedTargetNode();
        addSiblingMatchingUrl(translatedTargetFolder, "targetUrl");
        HippoTranslatedNode sourceFolderNode = createDeepestTranslatedSourceNode("level1");
        addNodeTranslation(sourceFolderNode, "fr", translatedTargetFolder);

        assertThrows(WorkflowSNSException.class, () -> changeSet.checkForSameNameSiblings(mockSession));
    }

    @Test
    public void checkForSameNameSiblings_same_name_folder_sibling_matches_display_name() throws Exception {
        // Tests that an exception is thrown when a node with the same display name already exists
        // This is a little more complex, the addSiblingsToNode has to mimic a NodeIterator that the
        // SameNameSiblingsUtil uses
        FolderTranslation rootFolder = createMockFolderTranslation("root", false);
        FolderTranslation level1 = createMockFolderTranslation("level1", false);
        FolderTranslation level2 = createHighestUntranslatedFolder("level2", "targetUrl", "targetName");
        changeSet.getFolders().add(rootFolder);
        changeSet.getFolders().add(level1);
        changeSet.getFolders().add(level2);

        Node sameNameSibling = createDeepestTranslatedTargetNode();
        HippoTranslatedNode sourceFolderNode = createDeepestTranslatedSourceNode("level1");
        addNodeTranslation(sourceFolderNode, "fr", sameNameSibling);

        addSiblingsToNode(sameNameSibling, "targetName", true);

        assertThrows(WorkflowSNSException.class, () -> changeSet.checkForSameNameSiblings(mockSession));
    }

    @Test
    public void checkForSameNameSiblings_folder_with_no_siblings() throws Exception {
        FolderTranslation rootFolder = createMockFolderTranslation("root", false);
        FolderTranslation level1 = createMockFolderTranslation("level1", false);
        FolderTranslation level2 = createHighestUntranslatedFolder("level2", "targetUrl", "targetName");
        changeSet.getFolders().add(rootFolder);
        changeSet.getFolders().add(level1);
        changeSet.getFolders().add(level2);

        Node deepestTranslatedTargetNode = createDeepestTranslatedTargetNode();
        HippoTranslatedNode sourceFolderNode = createDeepestTranslatedSourceNode("level1");
        addNodeTranslation(sourceFolderNode,"fr", deepestTranslatedTargetNode);

        NodeIterator mockIterator = mock(NodeIterator.class);
        when(deepestTranslatedTargetNode.getNodes()).thenReturn(mockIterator);
        when(mockIterator.hasNext()).thenReturn(false);

        changeSet.checkForSameNameSiblings(mockSession);
    }

    @Test
    public void checkForSameNameSiblings_with_siblings_but_all_different_names_and_urls() throws Exception {
        FolderTranslation rootFolder = createMockFolderTranslation("root", false);
        FolderTranslation level1 = createMockFolderTranslation("level1", false);
        FolderTranslation level2 = createHighestUntranslatedFolder("level2", "targetUrl", "targetName");
        changeSet.getFolders().add(rootFolder);
        changeSet.getFolders().add(level1);
        changeSet.getFolders().add(level2);

        Node deepestTranslatedTargetNode = createDeepestTranslatedTargetNode();
        HippoTranslatedNode sourceFolderNode = createDeepestTranslatedSourceNode("level1");
        addNodeTranslation(sourceFolderNode, "fr", deepestTranslatedTargetNode);

        addSiblingsToNode(deepestTranslatedTargetNode, "targetName", false);

        changeSet.checkForSameNameSiblings(mockSession);
    }

    @Test
    public void checkForSameNameSiblings_document_with_sibling_all_different() throws Exception {
        // If there are no same name siblings in the folders it moves on to check all of the documents
        // This test will have a document with siblings, but no same name sibling
        Node documentParentFolder = initialiseFoldersAllTranslated();

        FolderTranslation document1 = createHighestUntranslatedFolder("doc1", "doc1url", "doc1name");
        FolderTranslation document2 = createHighestUntranslatedFolder("doc2", "doc2url", "doc2name");
        FolderTranslation document3 = createHighestUntranslatedFolder("doc3", "doc3url", "doc3name");
        changeSet.getDocuments().add(document1);
        changeSet.getDocuments().add(document2);
        changeSet.getDocuments().add(document3);

        addSiblingsToNode(documentParentFolder, "doc1", false);

        changeSet.checkForSameNameSiblings(mockSession);
    }

    @Test
    public void checkForSameNameSiblings_document_with_same_url_sibling() throws Exception {
        // If there are no same name siblings in the folders it moves on to check all of the documents
        // This test will have a document with siblings, sibling with same url
        Node documentParentFolder = initialiseFoldersAllTranslated();

        FolderTranslation document1 = createHighestUntranslatedFolder("doc1", "doc1url", "doc1name");
        FolderTranslation document2 = createHighestUntranslatedFolder("doc2", "doc2url", "doc2name");
        FolderTranslation document3 = createHighestUntranslatedFolder("doc3", "doc3url", "doc3name");
        changeSet.getDocuments().add(document1);
        changeSet.getDocuments().add(document2);
        changeSet.getDocuments().add(document3);

        addSiblingsToNode(documentParentFolder, "doc1name", false);
        addSiblingMatchingUrl(documentParentFolder,"doc3url");

        assertThrows(WorkflowSNSException.class, () -> changeSet.checkForSameNameSiblings(mockSession));
    }

    @Test
    public void checkForSameNameSiblings_document_with_same_name_sibling() throws Exception {
        // If there are no same name siblings in the folders it moves on to check all of the documents
        // This test will have a document with siblings, sibling with same display name
        Node documentParentFolder = initialiseFoldersAllTranslated();

        FolderTranslation document1 = createHighestUntranslatedFolder("doc1", "doc1url", "doc1name");
        FolderTranslation document2 = createHighestUntranslatedFolder("doc2", "doc2url", "doc2name");
        FolderTranslation document3 = createHighestUntranslatedFolder("doc3", "doc3url", "doc3name");
        changeSet.getDocuments().add(document1);
        changeSet.getDocuments().add(document2);
        changeSet.getDocuments().add(document3);

        addSiblingsToNode(documentParentFolder, "doc3name", true);

        assertThrows(WorkflowSNSException.class, () -> changeSet.checkForSameNameSiblings(mockSession));
    }

    @Test
    public void checkForSameNameSiblings_document_with_no_siblings() throws Exception {
        // If there are no same name siblings in the folders it moves on to check all of the documents
        // This test will have a document with no siblings.
        Node documentParentFolder = initialiseFoldersAllTranslated();

        FolderTranslation document1 = createHighestUntranslatedFolder("doc1", "doc1url", "doc1name");
        FolderTranslation document2 = createHighestUntranslatedFolder("doc2", "doc2url", "doc2name");
        FolderTranslation document3 = createHighestUntranslatedFolder("doc3", "doc3url", "doc3name");
        changeSet.getDocuments().add(document1);
        changeSet.getDocuments().add(document2);
        changeSet.getDocuments().add(document3);

        NodeIterator mockIterator = mock(NodeIterator.class);
        when(documentParentFolder.getNodes()).thenReturn(mockIterator);
        when(mockIterator.hasNext()).thenReturn(false);

        changeSet.checkForSameNameSiblings(mockSession);
    }

    private Node initialiseFoldersAllTranslated() throws Exception {
        FolderTranslation rootFolder = createMockFolderTranslation("root", false);
        FolderTranslation level1 = createMockFolderTranslation("level1", false);
        FolderTranslation level2 = createMockFolderTranslation("level2", false);
        changeSet.getFolders().add(rootFolder);
        changeSet.getFolders().add(level1);
        changeSet.getFolders().add(level2);

        Node deepestTranslatedTargetNode = createDeepestTranslatedTargetNode();
        HippoTranslatedNode sourceFolderNode = createDeepestTranslatedSourceNode("level2");
        addNodeTranslation(sourceFolderNode,"fr", deepestTranslatedTargetNode);

        return deepestTranslatedTargetNode;
    }

    private void addSiblingMatchingUrl(Node folderNode, String relPath) throws Exception {
        when(folderNode.hasNode(eq(relPath))).thenReturn(true);
    }

    private void addSiblingsToNode(Node targetNode, String targetName, boolean hasMatchingSibling) throws Exception {
        // Add a node with a getDisplayName equals to targetName if hasMatchingSibling is true
        when(targetNode.getNodes()).thenAnswer(new Answer<NodeIterator>() {
            final int maxNumberOfCalls = 5;

            @Override
            public NodeIterator answer(InvocationOnMock invocation) throws Throwable {
                Answer<Boolean> mockHasNextAnswer = new Answer<Boolean>() {
                    int numberOfCalls = 0;

                    @Override
                    public Boolean answer(InvocationOnMock invocation) throws Throwable {
                        boolean hasNext = numberOfCalls < maxNumberOfCalls;
                        numberOfCalls++;
                        return hasNext;
                    }
                };

                Answer<HippoNode> mockNodeIteratorNodeAnswer = new Answer<HippoNode>() {
                    int numberOfCalls = 0;

                    @Override
                    public HippoNode answer(InvocationOnMock invocation) throws Throwable {
                        HippoNode mockNode = mock(HippoNode.class);
                        // Splitting the nodes between folders and handles, the first will be neither
                        if (numberOfCalls == 0) {
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
                        numberOfCalls++;
                        return mockNode;
                    }
                };

                NodeIterator mockNodeIterator = mock(NodeIterator.class);
                when(mockNodeIterator.hasNext()).thenAnswer(mockHasNextAnswer);
                when(mockNodeIterator.nextNode()).thenAnswer(mockNodeIteratorNodeAnswer);
                return mockNodeIterator;
            }
        });
    }

    private Node createDeepestTranslatedTargetNode() throws Exception {
        Node deepestTranslatedTargetNode = mock(Node.class);
        lenient().when(deepestTranslatedTargetNode.hasNode(anyString())).thenReturn(false);
        return deepestTranslatedTargetNode;
    }

    private FolderTranslation createHighestUntranslatedFolder(String nodeId, String targetUrlName, String targetLocalizedName) {
        FolderTranslation highestUntranslatedItem = createMockFolderTranslation(nodeId, true);
        when(highestUntranslatedItem.getUrlfr()).thenReturn(targetUrlName);
        when(highestUntranslatedItem.getNamefr()).thenReturn(targetLocalizedName);
        return highestUntranslatedItem;
    }

    private HippoTranslatedNode createDeepestTranslatedSourceNode(String sourceNodeIdentifier) throws Exception {
        Node sourceNode = mock(Node.class);
        HippoTranslatedNode mockHippoTranslatedNode = mock(HippoTranslatedNode.class);
        when(mockSession.getNodeByIdentifier(sourceNodeIdentifier)).thenReturn(sourceNode);
        doReturn(mockHippoTranslatedNode).when(changeSet).createFromNode(same(sourceNode));
        return mockHippoTranslatedNode;
    }

    private void addNodeTranslation(HippoTranslatedNode sourceNode, String language, Node translatedNode) throws Exception {
        when(sourceNode.getTranslation(eq(language))).thenReturn(translatedNode);
    }

    private FolderTranslation createMockFolderTranslation(String nodeId, boolean isEditable) {
        FolderTranslation mockFolder = mock(FolderTranslation.class);
        lenient().when(mockFolder.getId()).thenReturn(nodeId);
        lenient().when(mockFolder.isEditable()).thenReturn(isEditable);
        return mockFolder;
    }
}
