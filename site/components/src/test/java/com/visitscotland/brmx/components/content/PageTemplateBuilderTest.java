package com.visitscotland.brmx.components.content;

import com.visitscotland.brmx.beans.BaseDocument;
import com.visitscotland.brmx.beans.Megalinks;
import com.visitscotland.brmx.beans.Page;
import com.visitscotland.brmx.beans.TourismInformation;
import com.visitscotland.brmx.beans.mapping.ICentreModule;
import com.visitscotland.brmx.beans.mapping.IKnowModule;
import com.visitscotland.brmx.beans.mapping.Module;
import com.visitscotland.brmx.beans.mapping.megalinks.LinksModule;
import com.visitscotland.brmx.beans.mapping.megalinks.MultiImageLinksModule;
import com.visitscotland.brmx.beans.mapping.megalinks.SingleImageLinksModule;
import com.visitscotland.brmx.components.content.factory.ICentreFactory;
import com.visitscotland.brmx.components.content.factory.IKnowFactory;
import com.visitscotland.brmx.components.content.factory.LinkModulesFactory;
import com.visitscotland.brmx.mock.MegalinksMockBuilder;
import com.visitscotland.brmx.mock.TouristInformationMockBuilder;
import org.hippoecm.hst.mock.core.component.MockHstRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PageTemplateBuilderTest {

    MockHstRequest request;

    @Mock
    Page page;

    @Mock
    ICentreFactory iCentreFactory;

    @Mock
    IKnowFactory iKnowFactory;

    @Mock
    LinkModulesFactory linksFactory;

    PageTemplateBuilder builder;

    @BeforeEach
    void init() {
        request = new MockHstRequest();
        request.setLocale(Locale.UK);

        //Adds a mock document to the Request
        request.setAttribute("document", page);

        builder = new PageTemplateBuilder(linksFactory, iCentreFactory, iKnowFactory);
    }

    /**
     * Builds a Page with no documents associated
     */
    @Test
    void pageWithoutElements() {
        when(page.getModules()).thenReturn(new ArrayList<>());

        builder.addModules(request);

        List items = (List) request.getAttribute(PageTemplateBuilder.PAGE_ITEMS);

        assertEquals(0, items.size());
    }

    /**
     * Build a page with one Megalinks document associated
     */
    @Test
    void addMegalinksModule_basic() {
        Megalinks megalinks = new MegalinksMockBuilder().build();
        MultiImageLinksModule module = new MultiImageLinksModule();

        when(page.getModules()).thenReturn(Collections.singletonList(megalinks));
        when(linksFactory.getMegalinkModule(megalinks, Locale.UK)).thenReturn(module);

        builder.addModules(request);
        List items = (List) request.getAttribute(PageTemplateBuilder.PAGE_ITEMS);

        assertEquals(1, items.size());
    }

    /**
     * Styles alternate and the last repeats the first colour
     */
    @Test
    void addMegalinksModule_alternateStyles() {
        List<BaseDocument> list = Arrays.asList(
                new MegalinksMockBuilder().build(),
                new MegalinksMockBuilder().build(),
                new MegalinksMockBuilder().build(),
                new MegalinksMockBuilder().build());

        when(page.getModules()).thenReturn(list);
        when(linksFactory.getMegalinkModule((Megalinks) list.get(0), Locale.UK)).thenReturn(new MultiImageLinksModule("h2"));
        when(linksFactory.getMegalinkModule((Megalinks) list.get(1), Locale.UK)).thenReturn(new MultiImageLinksModule("h2"));
        when(linksFactory.getMegalinkModule((Megalinks) list.get(2), Locale.UK)).thenReturn(new MultiImageLinksModule("h2"));
        when(linksFactory.getMegalinkModule((Megalinks) list.get(3), Locale.UK)).thenReturn(new MultiImageLinksModule("h2"));

        builder.addModules(request);
        List<LinksModule> items = (List<LinksModule>) request.getAttribute(PageTemplateBuilder.PAGE_ITEMS);

        assertEquals(4, items.size());

        for (int i = 0; i < 4; i++) {
            assertEquals(PageTemplateBuilder.styles[i % 3], items.get(i).getTheme());
        }
    }

    /**
     * 3 first items share colour because their title is null, 4th is different
     */
    @Test
    void addMegalinksModule_skipAlternateStyles_whenNoH2() {
        List<BaseDocument> list = Arrays.asList(
                new MegalinksMockBuilder().build(),
                new MegalinksMockBuilder().build(),
                new MegalinksMockBuilder().build(),
                new MegalinksMockBuilder().build());

        when(page.getModules()).thenReturn(list);
        when(linksFactory.getMegalinkModule((Megalinks) list.get(0), Locale.UK)).thenReturn(new MultiImageLinksModule("h2"));
        when(linksFactory.getMegalinkModule((Megalinks) list.get(1), Locale.UK)).thenReturn(new MultiImageLinksModule());
        when(linksFactory.getMegalinkModule((Megalinks) list.get(2), Locale.UK)).thenReturn(new MultiImageLinksModule());
        when(linksFactory.getMegalinkModule((Megalinks) list.get(3), Locale.UK)).thenReturn(new MultiImageLinksModule("h2"));

        builder.addModules(request);
        List<LinksModule> items = (List<LinksModule>) request.getAttribute(PageTemplateBuilder.PAGE_ITEMS);

        assertEquals(4, items.size());

        for (int i = 0; i < 4; i++) {
            assertEquals(PageTemplateBuilder.styles[i != 3 ? 0 : 1], items.get(i).getTheme());
        }
    }

    /**
     * First item always have the same style independently of if the section title is defined
     */
    @Test
    void addMegalinksModule_firstItemColourIsStyle3_whenNoH2() {
        Megalinks mega = new MegalinksMockBuilder().build();
        when(page.getModules()).thenReturn(Collections.singletonList(mega));

        // Build the first case where the first element has no title
        when(linksFactory.getMegalinkModule(mega, Locale.UK)).thenReturn(new MultiImageLinksModule());
        builder.addModules(request);
        LinksModule firstModuleWithoutTitle = ((List<LinksModule>) request.getAttribute(PageTemplateBuilder.PAGE_ITEMS)).get(0);

        // Build the second case where the first element has a title
        when(linksFactory.getMegalinkModule(mega, Locale.UK)).thenReturn(new MultiImageLinksModule("h2"));
        builder.addModules(request);
        LinksModule firstModuleWithTitle = ((List<LinksModule>) request.getAttribute(PageTemplateBuilder.PAGE_ITEMS)).get(0);

        //Compare that the result is identical
        assertEquals(firstModuleWithoutTitle.getTheme(), firstModuleWithTitle.getTheme());
    }

    /**
     * Verifies that the alignment for Single Image modules alternates
     */
    @Test
    void addMegalinksModule_alternateAlignment() {
        List<BaseDocument> list = Arrays.asList(
                new MegalinksMockBuilder().build(),
                new MegalinksMockBuilder().build(),
                new MegalinksMockBuilder().build(),
                new MegalinksMockBuilder().build());

        when(page.getModules()).thenReturn(list);
        when(linksFactory.getMegalinkModule((Megalinks) list.get(0), Locale.UK)).thenReturn(new SingleImageLinksModule());
        when(linksFactory.getMegalinkModule((Megalinks) list.get(1), Locale.UK)).thenReturn(new SingleImageLinksModule());
        when(linksFactory.getMegalinkModule((Megalinks) list.get(2), Locale.UK)).thenReturn(new SingleImageLinksModule());
        when(linksFactory.getMegalinkModule((Megalinks) list.get(3), Locale.UK)).thenReturn(new SingleImageLinksModule());

        builder.addModules(request);
        List<LinksModule> items = (List<LinksModule>) request.getAttribute(PageTemplateBuilder.PAGE_ITEMS);

        assertEquals(4, items.size());

        for (int i = 0; i < 4; i++) {
            assertEquals(PageTemplateBuilder.alignment[i % 2], items.get(i).getAlignment());
        }
    }

    /**
     * Verifies that is able to add an iKnowModule when the minimum amount of information has been provided
     * Verifies that is able to set the Hippo bean for only Iknow configuration
     */
    @Test
    void addTouristInformation_iKnowModule() {
        TourismInformation ti = new TouristInformationMockBuilder().build();

        when(page.getModules()).thenReturn(Collections.singletonList(ti));
        when(iKnowFactory.getModule(any(), eq(null))).thenReturn(new IKnowModule());

        builder.addModules(request);

        List<Module> items = (List<Module>) request.getAttribute(PageTemplateBuilder.PAGE_ITEMS);
        assertEquals(1, items.size());
        assertEquals(ti, items.get(0).getHippoBean());
    }

    /**
     * Verifies that is able to add an iKnowModule when the minimum amount of information has been provided
     * Verifies that is able set the Hippo Bean when 2 items are returned.
     * Verifies that only one Hippo Bean is set edit module is enabled.
     */
    @Test
    void addTouristInformation_iCentreModule() {

        TourismInformation ti = new TouristInformationMockBuilder().build();

        when(page.getModules()).thenReturn(Collections.singletonList(ti));

        when(iCentreFactory.getModule(any(), eq(request.getLocale()), eq(null))).thenReturn(new ICentreModule());
        when(iKnowFactory.getModule(any(), eq(null))).thenReturn(new IKnowModule());

        builder.addModules(request);

        List<Module> items = (List<Module>) request.getAttribute(PageTemplateBuilder.PAGE_ITEMS);
        assertEquals(2, items.size());
        assertEquals(ti, items.get(0).getHippoBean());
        assertNull(items.get(1).getHippoBean());
    }
}
