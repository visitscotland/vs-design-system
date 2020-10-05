package com.visitscotland.brmx.components.content;

import com.visitscotland.brmx.beans.BaseDocument;
import com.visitscotland.brmx.beans.Megalinks;
import com.visitscotland.brmx.beans.Page;
import com.visitscotland.brmx.beans.mapping.megalinks.LinksModule;
import com.visitscotland.brmx.beans.mapping.megalinks.MultiImageLinksModule;
import com.visitscotland.brmx.components.content.factory.*;
import com.visitscotland.brmx.mock.MegalinksMockService;
import com.visitscotland.brmx.utils.HippoUtilsService;
import org.hippoecm.hst.mock.core.component.MockHstRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
public class PageTemplateBuilderTest {

    MegalinksMockService megalinkService;

    MockHstRequest request;

    Page document;

    PageTemplateBuilder builder;

    @BeforeEach
    void init(){

        megalinkService = new MegalinksMockService(null);

        request = new MockHstRequest();
        request.setLocale(Locale.UK);

        document = mock(Page.class);

        //Adds a mock document to the Request
        request.setAttribute("document", document);

        HippoUtilsService utils = mock(HippoUtilsService.class);

        LinkModulesFactory linksFactory = new LinkModulesFactory(utils, null, null);
        ICentreFactory iCentreFactory = new ICentreFactory(utils, null, null);
        IKnowFactory iKnowFactory = new IKnowFactory(utils);

        builder = new PageTemplateBuilder(linksFactory, iCentreFactory, iKnowFactory);
    }

    @Test
    void pageWithoutElements(){
        //Page with no documents associated.
        when(document.getModules()).thenReturn(Arrays.asList());

        builder.addModules(request);

        List items = (List) request.getAttribute(PageTemplateBuilder.PAGE_ITEMS);

        assertEquals(0, items.size());
    }

    @Test
    void addMegalinksModules(){
        //Page with one Megalinks document associated
        Megalinks megalinks = megalinkService.mockMultiImage();
        MultiImageLinksModule module = new MultiImageLinksModule();

//        when(linksFactory.getMegalinkModule(any(Megalinks.class), any(Locale.class))).thenReturn(module);
        when(document.getModules()).thenReturn(Arrays.asList(megalinks));

        builder.addModules(request);
        List items = (List)request.getAttribute(PageTemplateBuilder.PAGE_ITEMS);

        assertEquals(1, items.size());
    }

    /**
     * Styles alternate and the last repeats the first colour
     */
    @Test
    void checkMegalinksAlternateColours(){
        List<BaseDocument> list = Arrays.asList(
                megalinkService.mockMultiImage("Section 1"),
                megalinkService.mockMultiImage("Section 2"),
                megalinkService.mockMultiImage("Section 3"),
                megalinkService.mockMultiImage("Section 4"));

        when(document.getModules()).thenReturn(list);


        builder.addModules(request);
        List<LinksModule> items = (List)request.getAttribute(PageTemplateBuilder.PAGE_ITEMS);

        assertEquals(4, items.size());

        //
        for (int i = 0; i < 4; i++) {
            assertEquals(PageTemplateBuilder.styles[i%3], items.get(i).getStyle());
        }
    }

    /**
     * 3 first items share colour because their title is null, 4th is different
     */
    @Test
    void checkMegalinksSkipAlternateColours(){
        MultiImageLinksModule module = new MultiImageLinksModule();
        MultiImageLinksModule moduleWithTitle = new MultiImageLinksModule();
        moduleWithTitle.setTitle("Section Title");

        List<BaseDocument> list = Arrays.asList(
                megalinkService.mockMultiImage("Section 1"),
                megalinkService.mockMultiImage(),
                megalinkService.mockMultiImage(),
                megalinkService.mockMultiImage("Section 1"));

        when(document.getModules()).thenReturn(list);
//        when(linksFactory.getMegalinkModule(list.get(0), Locale.UK)).thenReturn(moduleWithTitle);
//        when(linksFactory.getMegalinkModule(list.get(1), Locale.UK)).thenReturn(module);
//        when(linksFactory.getMegalinkModule(list.get(2), Locale.UK)).thenReturn(module);
//        when(linksFactory.getMegalinkModule(list.get(3), Locale.UK)).thenReturn(moduleWithTitle);

        builder.addModules(request);
        List<LinksModule> items = (List)request.getAttribute(PageTemplateBuilder.PAGE_ITEMS);

        assertEquals(4, items.size());

        for (int i = 0; i < 4; i++) {
            assertEquals(PageTemplateBuilder.styles[i!=3?0:1], items.get(i).getStyle());
        }
    }

    /**
     * First item always have the same style independently of if the section title is defined
     */
    @Test
    void checkMegalinksFirstItemColour(){

        // Build the first case where the first element has no title
        Megalinks case1 = megalinkService.mockMultiImage(null);
        when(document.getModules()).thenReturn(Arrays.asList(case1));
        builder.addModules(request);
        LinksModule firstModuleWithoutTitle= ((List<LinksModule>) request.getAttribute(PageTemplateBuilder.PAGE_ITEMS)).get(0);

        // Build the second case where the first element has a title
        Megalinks case2 = megalinkService.mockMultiImage("Section Title");
        Page document2 = mock(Page.class);
        request.setAttribute("document", document2);
        when(document2.getModules()).thenReturn(Arrays.asList(case2));
        builder.addModules(request);
        LinksModule firstModuleWithTitle= ((List<LinksModule>) request.getAttribute(PageTemplateBuilder.PAGE_ITEMS)).get(0);

        //Compare that the result is identical
        assertEquals(firstModuleWithoutTitle.getStyle(), firstModuleWithTitle.getStyle());
    }
}
