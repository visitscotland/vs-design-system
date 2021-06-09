package com.visitscotland.brxm.factory;

import com.visitscotland.brxm.hippobeans.*;
import com.visitscotland.brxm.hippobeans.capabilities.Linkable;
import com.visitscotland.brxm.mock.MegalinksMockBuilder;
import com.visitscotland.brxm.model.FlatLink;
import com.visitscotland.brxm.model.Module;
import com.visitscotland.brxm.model.megalinks.EnhancedLink;
import com.visitscotland.brxm.model.megalinks.HorizontalListLinksModule;
import com.visitscotland.brxm.model.megalinks.LinksModule;
import com.visitscotland.brxm.services.LinkService;
import com.visitscotland.brxm.services.ResourceBundleService;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class MegalinkFactoryTest {

    public static final String DMS_ID = "0123456798";
    public static final String EXTERNAL_URL = "http://www.fake.site";
    public static final String PLAIN_LINK = "http://www.plain-link.site";
    public static final String PSR_URL = "http://psr.visitscotland.com/info/search?value1&value2";

    public enum LinkType {CMS, DMS, EXTERNAL, PRODUCT_SEARCH}


    @Mock
    ResourceBundleService resourceBundleService;

    @Mock(lenient = true)
    LinkService linkService;

    @Resource
    @InjectMocks
    MegalinkFactory factory;

    @BeforeEach
    public void beforeEach() {
        when(linkService.getPlainLink(any(SharedLink.class), any())).thenReturn(PLAIN_LINK);
    }


    private Megalinks mockMultiImage() {
        Megalinks mega = mock(Megalinks.class, withSettings().lenient());
        MegalinkItem item = mockItem();

        when(mega.getMegalinkItems()).thenReturn(Collections.singletonList(item));

        return mega;
    }

    public MegalinkItem mockItem() {
        return mockItem(false, LinkType.CMS);
    }

    public MegalinkItem mockItem(boolean featured, LinkType type) {
        MegalinkItem item = mock(MegalinkItem.class, withSettings().lenient());

        when(item.getFeature()).thenReturn(featured);
        if (type == LinkType.CMS) {
            when(item.getLink()).thenReturn(mockPage());
        } else {
            SharedLink link = mockSharedLink(type);
            when(item.getLink()).thenReturn(link);
        }

        return item;
    }


    private SharedLink mockSharedLink(LinkType linkType) {
        SharedLink link = mock(SharedLink.class, withSettings().lenient());
        when(link.getImage()).thenReturn(mock(Image.class, withSettings().lenient()));

        switch (linkType) {
            case DMS:
                DMSLink type = mock(DMSLink.class, withSettings().lenient());
                when(type.getProduct()).thenReturn(DMS_ID);
                when(link.getLinkType()).thenReturn(type);
                break;
            case PRODUCT_SEARCH:
                ProductsSearch ps = mock(ProductsSearch.class, withSettings().lenient());
                when(link.getLinkType()).thenReturn(ps);
                break;
            case EXTERNAL:
                ExternalLink external = mock(ExternalLink.class, withSettings().lenient());
                when(external.getLink()).thenReturn(EXTERNAL_URL);
                when(link.getLinkType()).thenReturn(external);
                break;
            default:

        }
        return link;
    }

    private Page mockPage() {
        return mock(Page.class);
    }

    @Test
    @DisplayName("Allowed items are processed and included to the list")
    void megalinkItem_allowedLinkTypes() {
        List<MegalinkItem> items = new MegalinksMockBuilder().addPageLink().addSharedLink().build().getMegalinkItems();
        when(linkService.createEnhancedLink(any(),any(), any(), anyBoolean())).thenReturn(new EnhancedLink());

        assertEquals(2, factory.convertToEnhancedLinks(null, items, Locale.UK, false).size());
    }

    @Test
    @DisplayName("Non Allowed items are removed from the list")
    void megalinkItem_notAllowedLinkTypes() {
        //Test that not allowed types gets skipped without throwing exception
        List<MegalinkItem> items =  new MegalinksMockBuilder().addLink(null).addLink(mock(MegalinkItem.class)).build().getMegalinkItems();
        Module<Megalinks> module = new Module<>();

        assertEquals(0, factory.convertToEnhancedLinks(module, items, Locale.UK, false).size());

        verify(linkService, never()).createEnhancedLink(any(),any(), any(), anyBoolean());
        assertEquals(2, module.getErrorMessages().size());
    }

    @Test
    @DisplayName("CTA is populated when the ProductItem field is populated")
    void multiImageLayout_optionCTA() {
        //Verifies that when the field ProductItem is populated
        ExternalLink mockLink = mock(ExternalLink.class);
        Megalinks mega = mockMultiImage();
        when(mega.getProductItem()).thenReturn(mockLink);
        when(linkService.createLink(any(Locale.class), eq(mockLink))).thenReturn(new FlatLink(null, "cta-link", null));

        LinksModule<?> layout = factory.multiImageLayout(mega, Locale.UK);

        assertEquals("cta-link", layout.getCta().getLink());
    }
    @Test
    @DisplayName("Get a horizontal layout")
    void getMegalinkModule_horizontalListLayout() {
        Megalinks mega = new MegalinksMockBuilder().horizontalLayout().build();

        LinksModule<?> linkModule = factory.getMegalinkModule(mega,Locale.UK);
        assertEquals("HorizontalListLinksModule", linkModule.getType());
    }

    @Test
    @DisplayName("VS-1696 get OTYML module overriding the default title")
    void horizontalListLayout_OTYML() {
        OTYML otyml = mock (OTYML.class);

        when(otyml.getTitle()).thenReturn("Other things");
        when(otyml.getIntroduction()).thenReturn(mock(HippoHtml.class));

        HorizontalListLinksModule module= factory.horizontalListLayout(otyml,Locale.UK);
        assertEquals("Other things", module.getTitle());
        assertNotNull(module.getIntroduction());
    }

    @Test
    @DisplayName("VS-1696 get OTYML module, default title")
    void horizontalListLayout_OTYML_defaultLabel() {
        OTYML otyml = mock (OTYML.class);

        when (resourceBundleService.getResourceBundle("otyml", "otyml.title.default", Locale.UK ,true)).thenReturn("otyml");

        HorizontalListLinksModule module= factory.horizontalListLayout(otyml,Locale.UK);
        assertEquals("otyml", module.getTitle());
    }

    @Test
    void unexpectedTypeGetsSkipped() {
        //Tests that the addition of a new type will not introduce an exception
        HippoBean link = mock(HippoBean.class, withSettings().extraInterfaces(Linkable.class));
        MegalinkItem item = mock(MegalinkItem.class);
        when(item.getLink()).thenReturn(link);
        Module<Megalinks> module = new Module<>();

        List<?> lis = factory.convertToEnhancedLinks(module, Collections.singletonList(item), Locale.UK,false);
        //TODO Review
        assertEquals(0, lis.size());
        assertEquals(1, module.getErrorMessages().size());
    }
}
