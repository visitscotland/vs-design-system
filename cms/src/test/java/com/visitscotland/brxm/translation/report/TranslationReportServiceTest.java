package com.visitscotland.brxm.translation.report;

import com.visitscotland.brxm.translation.MockJcrDocumentBuilder;
import com.visitscotland.brxm.translation.MockNodeBuilder;
import com.visitscotland.brxm.translation.SessionFactory;
import com.visitscotland.brxm.translation.plugin.JcrDocument;
import com.visitscotland.brxm.translation.plugin.JcrDocumentFactory;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import javax.jcr.Node;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TranslationReportServiceTest {
    private TranslationReportService service;
    @Mock
    private SessionFactory mockSessionFactory;
    @Mock
    private JcrDocumentFactory mockJcrDocumentFactory;
    @Mock
    private JcrQueryService mockJcrQueryService;


    @BeforeEach
    public void beforeEach() {
        service = new TranslationReportService(mockSessionFactory, mockJcrDocumentFactory, mockJcrQueryService);
    }

    @Test
    void whenTranslationFlagTrue_thenItemStatusSendForTranslation() throws Exception {
        Node englishHandle = new MockNodeBuilder().withProperty("hippotranslation:locale", "en").build();
        Node frenchHandle = new MockNodeBuilder().withProperty("hippotranslation:locale", "fr")
                .withProperty("visitscotland:translationFlag", true).build();
        Node englishUnpublishedVariant =  createMockVariant("unpublished", "visitscotland:Page", "", Calendar.getInstance(), "live");
        JcrDocument frenchJcrDoc = new MockJcrDocumentBuilder().withHandle(frenchHandle).withLocaleName("fr").build();
        JcrDocument englishJcrDoc = new MockJcrDocumentBuilder().withHandle(englishHandle)
                .withVariantNode("unpublished", englishUnpublishedVariant)
                .withTranslations(frenchJcrDoc).build();
        doReturn(Collections.singletonList(englishJcrDoc))
                .when(mockJcrQueryService).getAllEnglishDocuments();

        List<TranslationModel> models = service.getUntranslatedDocuments("fr");

        TranslationModel model = models.get(0);
        Assertions.assertEquals("Sent for translation", model.getTranslationStatus());
        MatcherAssert.assertThat(model.getTranslatedLocales(), is(Collections.singletonList("en")));
        MatcherAssert.assertThat(model.getSentForTranslationLocales(), is(Collections.singletonList("fr")));
    }

    @Test
    void whenTranslationFlagNotSet_thenItemStatusUntranslated() throws Exception {
        Node englishHandle = new MockNodeBuilder().withProperty("hippotranslation:locale", "en").build();
        Node frenchHandle = new MockNodeBuilder().withProperty("hippotranslation:locale", "fr").build();
        Node englishUnpublishedVariant =  createMockVariant("unpublished", "visitscotland:Page", "", Calendar.getInstance(), "live");
        JcrDocument frenchJcrDoc = new MockJcrDocumentBuilder().withHandle(frenchHandle).withLocaleName("fr").build();
        JcrDocument englishJcrDoc = new MockJcrDocumentBuilder().withHandle(englishHandle)
                .withVariantNode("unpublished", englishUnpublishedVariant)
                .withTranslations(frenchJcrDoc).build();
        doReturn(Collections.singletonList(englishJcrDoc))
                .when(mockJcrQueryService).getAllEnglishDocuments();

        List<TranslationModel> models = service.getUntranslatedDocuments("fr");

        TranslationModel model = models.get(0);
        Assertions.assertEquals("Untranslated", model.getTranslationStatus());
        MatcherAssert.assertThat(model.getTranslatedLocales(), is(Collections.singletonList("en")));
        MatcherAssert.assertThat(model.getSentForTranslationLocales(), is(Collections.emptyList()));
    }

    @Test
    void whenDocumentTranslated_doesNotShowWhenLanguageQueried() throws Exception {
        Node englishHandle = new MockNodeBuilder().withProperty("hippotranslation:locale", "en").build();
        Node frenchHandle = new MockNodeBuilder().withProperty("hippotranslation:locale", "fr")
                .withProperty("visitscotland:translationFlag", false).build();
        Node englishUnpublishedVariant =  createMockVariant("unpublished", "visitscotland:Page", "", Calendar.getInstance(), "live");
        JcrDocument frenchJcrDoc = new MockJcrDocumentBuilder().withHandle(frenchHandle).withLocaleName("fr").build();
        JcrDocument englishJcrDoc = new MockJcrDocumentBuilder().withHandle(englishHandle)
                .withVariantNode("unpublished", englishUnpublishedVariant)
                .withTranslations(frenchJcrDoc).build();
        doReturn(Collections.singletonList(englishJcrDoc))
                .when(mockJcrQueryService).getAllEnglishDocuments();

        List<TranslationModel> models = service.getUntranslatedDocuments("fr");

        Assertions.assertEquals(0, models.size());
    }



    private static Node createMockVariant(String state, String type, String lastModifiedBy, Calendar lastModifiedAt, String stateSummary) throws Exception {
        return new MockNodeBuilder()
                .withNodeType("hippostd:publishable").withNodeType("hippotranslation:translated")
                .withProperty("hippostd:state", state)
                .withProperty("jcr:primaryType", type)
                .withProperty("hippostdpubwf:lastModifiedBy", lastModifiedBy)
                .withProperty("hippostdpubwf:lastModificationDate", lastModifiedAt)
                .withProperty("hippostd:stateSummary", stateSummary)
                .build();
    }


}
