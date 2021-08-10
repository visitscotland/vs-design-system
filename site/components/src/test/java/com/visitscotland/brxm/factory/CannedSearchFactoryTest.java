package com.visitscotland.brxm.factory;

import com.visitscotland.brxm.config.VsComponentManager;
import com.visitscotland.brxm.dms.DMSProxy;
import com.visitscotland.brxm.dms.ProductSearchBuilder;
import com.visitscotland.brxm.hippobeans.CannedSearch;
import com.visitscotland.brxm.hippobeans.IknowCommunity;
import com.visitscotland.brxm.hippobeans.ProductSearchLink;
import com.visitscotland.brxm.hippobeans.ProductsSearch;
import com.visitscotland.brxm.hippobeans.capabilities.Linkable;
import com.visitscotland.brxm.mock.CannedSearchMockBuilder;
import com.visitscotland.brxm.mock.IKnowCommunityMockBuilder;
import com.visitscotland.brxm.model.CannedSearchModule;
import com.visitscotland.brxm.model.FlatLink;
import com.visitscotland.brxm.model.IKnowCommunityModule;
import com.visitscotland.brxm.model.LinkType;
import com.visitscotland.brxm.services.LinkService;
import com.visitscotland.brxm.services.ResourceBundleService;
import com.visitscotland.brxm.utils.HippoUtilsService;
import com.visitscotland.brxm.utils.Properties;
import com.visitscotland.utils.Contract;
import org.hippoecm.hst.core.container.ComponentManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vs.ase.dms.ProductTypes;

import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CannedSearchFactoryTest {

    @Mock
    LinkService linkService;

    @Mock
    ResourceBundleService bundle;

    @Mock
    DMSProxy proxy;

    @InjectMocks
    CannedSearchFactory factory;

    private static final String BUNDLE_ID = "canned-search";
    private static final String PSR_URL = "canned-search";
    static ProductSearchBuilder psBuilder;

    @BeforeAll
    public static void initContext(){
        ComponentManager context = mock(ComponentManager.class, withSettings().lenient());
        psBuilder = mock(ProductSearchBuilder.class, Answers.RETURNS_SELF);
        when(psBuilder.buildCannedSearch()).thenReturn(PSR_URL);
        when(context.getComponent(ProductSearchBuilder.class)).thenReturn(psBuilder);

        new VsComponentManager().setComponentManager(context);
    }

    @DisplayName("When no cta for list view is provided then default cta should be used")
    @Test
    void whenNoCtaProvided_thenDefaultCtaUsed() {
        when(bundle.getResourceBundle(BUNDLE_ID, "canned-search.listview", Locale.UK))
                .thenReturn("default cta");

        FlatLink flatLink = new FlatLink();
        CannedSearch cannedSearch =  new CannedSearchMockBuilder().criteria(ProductTypes.ACCOMMODATION.getId()).build();

        when((linkService).createLink(Locale.UK,cannedSearch.getCriteria())).thenReturn(flatLink);

        CannedSearchModule module = factory.getCannedSearchModule(cannedSearch, Locale.UK);

        Assertions.assertEquals("default cta", module.getViewAllLink().getLabel());
        Assertions.assertNull(module.getCredit());
        Assertions.assertEquals(PSR_URL, module.getCannedSearchEndpoint());
    }

    @DisplayName("When cta for list view is provided then default cta should be used")
    @Test
    void whenCtaProvided_thenDefaultCtaUsed() {
        FlatLink flatLink = new FlatLink();
        flatLink.setLabel("View all castles");
        CannedSearch cannedSearch =  new CannedSearchMockBuilder().criteria(ProductTypes.ACCOMMODATION.getId()).build();

        when((linkService).createLink(Locale.UK,cannedSearch.getCriteria())).thenReturn(flatLink);

        CannedSearchModule module = factory.getCannedSearchModule(cannedSearch, Locale.UK);

        Assertions.assertEquals("View all castles", module.getViewAllLink().getLabel());
        Assertions.assertEquals(PSR_URL, module.getCannedSearchEndpoint());
    }

    @DisplayName("When the product type is an even the credit must be displayed")
    @Test
    void whenCreditProvided() {
        when(bundle.getResourceBundle(BUNDLE_ID, "canned-search.listview", Locale.UK))
                .thenReturn("default cta");

        when(bundle.getResourceBundle(BUNDLE_ID, "canned-search.credit-events", Locale.UK))
                .thenReturn("credit");

        FlatLink flatLink = new FlatLink();
        CannedSearch cannedSearch =  new CannedSearchMockBuilder().criteria(ProductTypes.EVENT.getId()).build();

        when((linkService).createLink(Locale.UK,cannedSearch.getCriteria())).thenReturn(flatLink);

        CannedSearchModule module = factory.getCannedSearchModule(cannedSearch, Locale.UK);

        Assertions.assertEquals("default cta", module.getViewAllLink().getLabel());
        Assertions.assertEquals("credit", module.getCredit());
        Assertions.assertEquals(PSR_URL, module.getCannedSearchEndpoint());
    }

}
