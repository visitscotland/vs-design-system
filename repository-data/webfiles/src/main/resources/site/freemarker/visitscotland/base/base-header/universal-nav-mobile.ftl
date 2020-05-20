<#include "../../include/imports.ftl">
<#include "../../vs-dotcom-ds/components/list-group.ftl">
<#include "../../vs-dotcom-ds/components/drawer-list-item.ftl">

<vs-list-group class="d-lg-none" tabindex="-1">
    <vs-drawer-list-item
        key="1"
        href="/"
        :active="true"
        tracking-id="nav.link.test-tracking-id"
    >
        VisitScotland
    </vs-drawer-list-item>
    <vs-drawer-list-item
        key="2"
        href="https://businessevents.visitscotland.com"
        tracking-id="nav.link.test-tracking-id"
        is-external
    >
        Business Events
    </vs-drawer-list-item>
    <vs-drawer-list-item
        key="3"
        href="https://traveltrade.visitscotland.org"
        tracking-id="nav.link.test-tracking-id"
        is-external
    >
        Travel Trade
    </vs-drawer-list-item>
    <vs-drawer-list-item
        key="4"
        href="http://mediacentre.visitscotland.org"
        tracking-id="nav.link.test-tracking-id"
    >
        Media Center
    </vs-drawer-list-item>
    <vs-drawer-list-item
        key="5"
        href="/"
        :active="true"
        tracking-id="nav.link.test-tracking-id"
    >
        VisitScotland
    </vs-drawer-list-item>
</vs-list-group>

