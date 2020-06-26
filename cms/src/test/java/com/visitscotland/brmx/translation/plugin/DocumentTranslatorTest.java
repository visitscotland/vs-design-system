package com.visitscotland.brmx.translation.plugin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DocumentTranslatorTest {
    private DocumentTranslator translator;
    @Mock
    private HippoTranslatedNodeFactory mockTranslatedNodeFactory;

    @BeforeEach
    public void beforeEach() {
        translator = new DocumentTranslator(mockTranslatedNodeFactory);
    }

    // Each test covers a scenario that has a source chain of folders from the document to be translated to the root
    // translation folder.
    // The target chain of folders differs in each case by the number of translated folders that already exist.

    @Test
    public void populateFolders_only_root_translated() {
        // only the root folder exists in the target, all the folders should be added as mutable folder translations
    }

    @Test
    public void populateFolders_partial_tree_translation() {
        // only half of the tree exists in the target tree. Half of the folders should be mutable, the other half
        // immutable because they already exist.
    }

    @Test
    public void populateFolders_whole_tree_translated() {
        // the whole of the target tree already exists, only immutable folder should be added to the list
    }

    @Test
    public void populateFolders_tree_with_backLink() {
        // the code suggests that a folder can be linked to BOTH languages at the same time,
        // i.e. they are the same node. All folders below this "back link" are treated as immutable folders.
        // this would also imply that the root folder of both is the same, not sure that can even be created in the CMS.
    }
}
