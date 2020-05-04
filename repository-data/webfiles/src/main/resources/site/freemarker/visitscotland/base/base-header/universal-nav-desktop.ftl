<#include "../../include/imports.ftl">
<#--  <#include "../../vs-dotcom-ds/components/header-list-group.ftl">
<#include "../../vs-dotcom-ds/components/header-list-group-item.ftl">  -->
    
<vs-header-list-group class="d-lg-inline-flex d-none" section="top">
    <vs-header-list-group-item
        key="1"
        href="/"
        :active="true"
        tracking-id="nav.link.test-tracking-id"
    >
        VisitScotland
    </vs-header-list-group-item>
    <vs-header-list-group-item
        key="2"
        href="https://businessevents.visitscotland.com"
        tracking-id="nav.link.test-tracking-id"
        is-external
    >
        Business Events
    </vs-header-list-group-item>
    <vs-header-list-group-item
        key="3"
        href="https://traveltrade.visitscotland.org"
        :active="true"
        tracking-id="nav.link.test-tracking-id"
    >
        Travel Trade
    </vs-header-list-group-item>
    <vs-header-list-group-item
        key="4"
        href="http://mediacentre.visitscotland.org"
        :active="true"
        tracking-id="nav.link.test-tracking-id"
    >
        Media Center
    </vs-header-list-group-item>
    <vs-header-list-group-item
        key="5"
        href="https://www.visitscotland.org"
        :active="true"
        tracking-id="nav.link.test-tracking-id"
    >
        Corporate
    </vs-header-list-group-item>
</vs-header-list-group>