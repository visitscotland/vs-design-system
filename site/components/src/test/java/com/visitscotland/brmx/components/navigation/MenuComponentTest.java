package com.visitscotland.brmx.components.navigation;

import com.visitscotland.brmx.beans.Page;
import com.visitscotland.brmx.beans.Widget;
import com.visitscotland.brmx.services.ResourceBundleService;
import com.visitscotland.brmx.utils.HippoUtilsService;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoFolder;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.container.ComponentManager;
import org.hippoecm.hst.core.linking.HstLink;
import org.hippoecm.hst.core.request.ResolvedSiteMapItem;
import org.hippoecm.hst.core.sitemenu.HstSiteMenu;
import org.hippoecm.hst.core.sitemenu.HstSiteMenuItem;
import org.hippoecm.hst.mock.core.component.MockHstRequest;
import org.hippoecm.hst.site.HstServices;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;


@ExtendWith(MockitoExtension.class)
public class MenuComponentTest {

    private static final String MENU_ID = "mock";
    private static final String BUNDLE_ID = MenuComponent.NAVIGATION_PREFIX+ MENU_ID;

    private static final Locale LOCALE = Locale.UK;

    @Mock
    HippoUtilsService utils;

    @Mock
    ResourceBundleService bundle;

    @Mock
    HstSiteMenuItem menuItem;

    @BeforeEach
    void setUp() {
        //static Class that provides some basic Hippo Functionality
        HstServices.setComponentManager(mock(ComponentManager.class));

        //Basic Request

    }

    @Test
    void basicHippoMockingTest(){
        MenuComponent menu = new MenuComponent(bundle, utils);

//        menu.doBeforeRender(request, null);
    }

    @Test
    void titleFromResourceBundle_butNotCta(){
        //Basic element with no link. Takes the title of the menu from a ResourceBundle but doesn't add a CTA text
        HstRequest request = mockRequest();
        MenuComponent menu = new MenuComponent(bundle, utils);

        when(menuItem.getName()).thenReturn("home");
        when(bundle.getResourceBundle(BUNDLE_ID, "home", LOCALE, true)).thenReturn("Home Page");

        menu.enhanceMenu(request);

        Assertions.assertEquals(1, getEnhancedMenu(request).size());
        Assertions.assertEquals("Home Page", getEnhancedMenu(request).get(0).getTitle());
        Assertions.assertNull(getEnhancedMenu(request).get(0).getCta());
    }

    @Test
    void resourceBundleTakesPrecedenceFromDocumentTitle(){
        //Basic element with no link. Takes the title of the menu from a ResourceBundle but doesn't add a CTA text
        HstRequest request = mockRequest();
        MenuComponent menu = new MenuComponent(bundle, utils);
        Page page = mock(Page.class, withSettings().lenient());
        addHstLink(request, page);
        when(page.getTitle()).thenReturn("Page Title");
        when(menuItem.getName()).thenReturn("home");
        when(bundle.getResourceBundle(BUNDLE_ID, "home", LOCALE, true)).thenReturn("Resource Bundle Title");

        menu.enhanceMenu(request);

        Assertions.assertEquals(1, getEnhancedMenu(request).size());
        Assertions.assertEquals("Resource Bundle Title", getEnhancedMenu(request).get(0).getTitle());
    }



    @Test
    void notExisting_or_notPublishedDocument(){
        //The document is not available (main reason: is that it is not published)
        HstRequest request =  mockRequest();
        MenuComponent menu = new MenuComponent(bundle, utils);
        addHstLink(request, null);

        menu.enhanceMenu(request);

        Assertions.assertEquals(0, getEnhancedMenu(request).size());
    }

    @Test
    void existingFolderWithNoContentDocument(){
        //The path resolves a folder but not a document (main reason: content document was renamed or deleted)
        HstRequest request =  mockRequest();
        MenuComponent menu = new MenuComponent(bundle, utils);
        HippoFolder folder = mock(HippoFolder.class);
        addHstLink(request, folder);

        menu.enhanceMenu(request);

        Assertions.assertEquals(0, getEnhancedMenu(request).size());
    }

    @Test
    void widget(){
        //The path resolves a folder but not a document (main reason: content document was renamed or deleted)
        HstRequest request =  mockRequest();
        MenuComponent menu = new MenuComponent(bundle, utils);
        Widget widget = mock(Widget.class);
        addHstLink(request, widget);
        when(menuItem.getName()).thenReturn("widget.title");

        menu.enhanceMenu(request);

        Assertions.assertEquals(1, getEnhancedMenu(request).size());
        Assertions.assertNotNull(getEnhancedMenu(request).get(0).getWidget());
    }

    @Test
    void documentWithNoTitle_skippedFromRender(){
        //When a document has a link but it has no title and no resource bundle. It gets droppped from the list
        // On the current specification the field title is mandatory. So, this is more defensive programming rather than a real case
        HstRequest request =  mockRequest();
        MenuComponent menu = new MenuComponent(bundle, utils);
        Page page = mock(Page.class);
        addHstLink(request, page);

        when(menuItem.getName()).thenReturn("home");
        when(bundle.getResourceBundle(BUNDLE_ID,"home", LOCALE, true)).thenReturn(null);
        when(bundle.existsResourceBundleKey(BUNDLE_ID,"home.cta", LOCALE)).thenReturn(false);

        menu.enhanceMenu(request);

        Assertions.assertEquals(0, getEnhancedMenu(request).size());
    }

    @Test
    void populateCtaText(){
        //The cta gets populated with the resourceBundle information
        HstRequest request =  mockRequest();
        MenuComponent menu = new MenuComponent(bundle, utils);
        Page page = mock(Page.class);
        addHstLink(request, page);

        when(menuItem.getName()).thenReturn("home");
        when(bundle.getResourceBundle(BUNDLE_ID,"home", LOCALE, true)).thenReturn("Home Page");
        when(bundle.existsResourceBundleKey(BUNDLE_ID,"home.cta", LOCALE)).thenReturn(true);
        when(bundle.getResourceBundle(BUNDLE_ID,"home.cta", LOCALE)).thenReturn("CTA text");

        menu.enhanceMenu(request);

        Assertions.assertEquals(1, getEnhancedMenu(request).size());
        Assertions.assertEquals("CTA text", getEnhancedMenu(request).get(0).getCta());
    }

    @Test
    void populateCtaText_defaultValue(){
        //The cta gets the text defaulted to CMS configuration
        HstRequest request =  mockRequest();
        MenuComponent menu = new MenuComponent(bundle, utils);
        Page page = mock(Page.class);
        addHstLink(request, page);

        when(page.getTitle()).thenReturn("Cities");

        when(menuItem.getName()).thenReturn("home");
        when(bundle.getResourceBundle(BUNDLE_ID,"home", LOCALE, true)).thenReturn(null);
        when(bundle.existsResourceBundleKey(BUNDLE_ID,"home.cta", LOCALE)).thenReturn(false);
        when(bundle.getResourceBundle(MenuComponent.STATIC,"see-all-cta", request.getLocale())).thenReturn("See all %s");

        menu.enhanceMenu(request);

        Assertions.assertEquals(1, getEnhancedMenu(request).size());
        Assertions.assertEquals("See all Cities", getEnhancedMenu(request).get(0).getCta());
    }

    @Test
    void resourceBundle_seeAll_notProperlyDefinedOnTheCMS_noParams(){
        //ResourceBundle does not throw an exception when the text is not specified correctly
        HstRequest request =  mockRequest();
        MenuComponent menu = new MenuComponent(bundle, utils);
        Page page = mock(Page.class);
        addHstLink(request, page);
        when(page.getTitle()).thenReturn("Cities");

        when(menuItem.getName()).thenReturn("home");
        when(bundle.getResourceBundle(BUNDLE_ID,"home", LOCALE, true)).thenReturn(null);
        when(bundle.existsResourceBundleKey(BUNDLE_ID,"home.cta", LOCALE)).thenReturn(false);
        when(bundle.getResourceBundle(MenuComponent.STATIC,"see-all-cta", request.getLocale())).thenReturn("See all");

        menu.enhanceMenu(request);

        Assertions.assertEquals(1, getEnhancedMenu(request).size());
        Assertions.assertEquals("See all", getEnhancedMenu(request).get(0).getCta());

        // "%s hello %s"
        // "hello"
    }

    @Test
    void resourceBundle_seeAll_notProperlyDefinedOnTheCMS_severalParams(){
        //ResourceBundle does not throw an exception when the text is not specified correctly
        HstRequest request =  mockRequest();
        MenuComponent menu = new MenuComponent(bundle, utils);
        Page page = mock(Page.class);
        addHstLink(request, page);
        when(page.getTitle()).thenReturn("Cities");

        when(menuItem.getName()).thenReturn("home");
        when(bundle.getResourceBundle(BUNDLE_ID,"home", LOCALE, true)).thenReturn(null);
        when(bundle.existsResourceBundleKey(BUNDLE_ID,"home.cta", LOCALE)).thenReturn(false);
        when(bundle.getResourceBundle(MenuComponent.STATIC,"see-all-cta", request.getLocale())).thenReturn("See all %s %s %s.");

        menu.enhanceMenu(request);

        Assertions.assertEquals(1, getEnhancedMenu(request).size());
        Assertions.assertEquals("See all   .", getEnhancedMenu(request).get(0).getCta());
    }

    @Test
    void nestedConfiguration_complexCase(){
        //Complex Scenario that tests the nested menu items
        HstRequest request =  mockRequest();
        MenuComponent menu = new MenuComponent(bundle, utils);
        Page page = mock(Page.class);
        addHstLink(request, page);

        when(menuItem.getName()).thenReturn("home");
        when(bundle.getResourceBundle(BUNDLE_ID, "home", LOCALE, true)).thenReturn("I'm the granny");
        when(bundle.existsResourceBundleKey(BUNDLE_ID, "home.cta", LOCALE)).thenReturn(true);
        when(bundle.getResourceBundle(BUNDLE_ID, "home.cta", LOCALE)).thenReturn("See my family");

        HstSiteMenuItem child = mock(HstSiteMenuItem.class);
        addHstLink(request, page, child);
        when(child.getName()).thenReturn("child");
        when(bundle.getResourceBundle(BUNDLE_ID, "child", LOCALE, true)).thenReturn("Still a Parent");
        when(bundle.existsResourceBundleKey(BUNDLE_ID, "child.cta", LOCALE)).thenReturn(true);
        when(bundle.getResourceBundle(BUNDLE_ID, "child.cta", LOCALE)).thenReturn("See my children");

        when(menuItem.getChildMenuItems()).thenReturn(Collections.singletonList(child));

        HstSiteMenuItem grandChild = mock(HstSiteMenuItem.class);
        addHstLink(request, page, grandChild);
        when(grandChild.getName()).thenReturn("grandchild");
        when(bundle.getResourceBundle(BUNDLE_ID, "grandchild", LOCALE, true)).thenReturn("Leaf menu item");
        when(bundle.existsResourceBundleKey(BUNDLE_ID, "grandchild.cta", LOCALE)).thenReturn(true);
        when(bundle.getResourceBundle(BUNDLE_ID, "grandchild.cta", LOCALE)).thenReturn("Too young!");

        when(child.getChildMenuItems()).thenReturn(Collections.singletonList(grandChild));

        menu.enhanceMenu(request);

        Assertions.assertEquals(1, getEnhancedMenu(request).size());
        Assertions.assertEquals("home", getEnhancedMenu(request).get(0).getName());
        Assertions.assertEquals("I'm the granny", getEnhancedMenu(request).get(0).getTitle());
        Assertions.assertEquals("See my family", getEnhancedMenu(request).get(0).getCta());

        Assertions.assertEquals(1, getEnhancedMenu(request).get(0).getChildMenuItems().size());
        MenuItem aux = (MenuItem) getEnhancedMenu(request).get(0).getChildMenuItems().get(0);
        Assertions.assertEquals("child", aux.getName());
        Assertions.assertEquals("Still a Parent", aux.getTitle());
        Assertions.assertEquals("See my children", aux.getCta());

        Assertions.assertEquals(1, aux.getChildMenuItems().size());
        aux = (MenuItem) aux.getChildMenuItems().get(0);
        Assertions.assertEquals("grandchild", aux.getName());
        Assertions.assertEquals("Leaf menu item", aux.getTitle());
        Assertions.assertEquals("Too young!", aux.getCta());
    }

    private MockHstRequest mockRequest(){
        MockHstRequest request = new MockHstRequest();
        request.setLocale(LOCALE);
        HstSiteMenu model = mock(HstSiteMenu.class);

        when(model.getName()).thenReturn(MENU_ID);
        when(model.getSiteMenuItems()).thenReturn(Collections.singletonList(menuItem));
        request.setModel(MenuComponent.MENU, model);

        return request;
    }

    private List<MenuItem> getEnhancedMenu(HstRequest request){
        return (List<MenuItem>) request.getModel(MenuComponent.ENHANCED_MENU);
    }

    private void addHstLink(HstRequest request, HippoBean bean){
        addHstLink(request, bean, menuItem);
    }

    private void addHstLink(HstRequest request, HippoBean bean, HstSiteMenuItem menuItem){
        HstLink link = mock(HstLink.class);
        when(link.getPath()).thenReturn("/content/fake/path/to/home");
        ResolvedSiteMapItem rsi = mock(ResolvedSiteMapItem.class);

        when(menuItem.getHstLink()).thenReturn(link);
        when(menuItem.resolveToSiteMapItem()).thenReturn(rsi);

        when(utils.getBeanForResolvedSiteMapItem(request, rsi)).thenReturn(bean);
    }

}
