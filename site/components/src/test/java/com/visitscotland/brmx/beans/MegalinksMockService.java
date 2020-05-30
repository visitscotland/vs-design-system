package com.visitscotland.brmx.beans;

import org.easymock.EasyMock;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

import java.util.ArrayList;
import java.util.List;

public class MegalinksMockService {

    private final MegalinkItemMockService itemService;

    public MegalinksMockService(){
        itemService = new MegalinkItemMockService();
    }

    public MegaLinks createMock(String title, boolean introduction, boolean listView, boolean teaser, int links, String intro){
        MegaLinks mega = EasyMock.createNiceMock(MegaLinks.class);
        SingleImageModule single = EasyMock.createNiceMock(SingleImageModule.class);
        Image img = EasyMock.createNiceMock(Image.class);
        List<MegaLinkItem> items = new ArrayList<>();

        EasyMock.expect(mega.getTitle()).andReturn(title).anyTimes();
        EasyMock.expect(mega.getList()).andReturn(listView).anyTimes();
        EasyMock.expect(mega.getHideTeaser()).andReturn(teaser).anyTimes();

        EasyMock.expect(mega.getSingleImageModule()).andReturn(single).anyTimes();

        EasyMock.expect(single.getTitle()).andReturn(intro);
        EasyMock.expect(single.getImage()).andReturn(img);
        EasyMock.expect(single.getFullWidth()).andReturn(false);
        EasyMock.expect(single.getIntroduction()).andReturn(EasyMock.createNiceMock(HippoHtml.class));

        if (introduction){
            EasyMock.expect(mega.getIntroduction()).andReturn(EasyMock.createNiceMock(HippoHtml.class)).anyTimes();
        } else {
            EasyMock.expect(mega.getIntroduction()).andReturn(null).anyTimes();
        }

        for (int i=0; i<links; i++) {
            items.add(itemService.createMock(false));
        }

        EasyMock.expect(mega.getMegaLinkItems()).andReturn(items).anyTimes();

        EasyMock.replay(mega, single, img);

        return mega;
    }

    public MegaLinks createMock(String title){
        return createMock(title, title!=null,true, true, 0);
    }


    public MegaLinks createMock(String title, boolean introduction, boolean listView, boolean teaser, int links){
        MegaLinks mega = EasyMock.createNiceMock(MegaLinks.class);
        List<MegaLinkItem> items = new ArrayList<>();

        EasyMock.expect(mega.getTitle()).andReturn(title).anyTimes();
        EasyMock.expect(mega.getList()).andReturn(listView).anyTimes();
        EasyMock.expect(mega.getHideTeaser()).andReturn(teaser).anyTimes();
        EasyMock.expect(mega.getSingleImageModule()).andReturn(null).anyTimes();

        if (introduction){
            EasyMock.expect(mega.getIntroduction()).andReturn(EasyMock.createNiceMock(HippoHtml.class)).anyTimes();
        } else {
            EasyMock.expect(mega.getIntroduction()).andReturn(null).anyTimes();
        }

        for (int i=0; i<links; i++) {
            items.add(itemService.createMock(false));
        }

        EasyMock.expect(mega.getMegaLinkItems()).andReturn(items).anyTimes();

        EasyMock.replay(mega);

        return mega;
    }


}
