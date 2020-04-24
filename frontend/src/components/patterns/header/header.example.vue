<template>
    <div style="overflow-y: scroll; min-height: 600px;">
        <VsSkipTo :target="contentContainer">
            Skip to Content
        </VsSkipTo>

        <VsDrawerToggle
            content-key="site-search"
            drawer-key="header-bottom"
            tag="vs-skip-to-button"
        >
            Skip to Search
        </VsDrawerToggle>

        <VsHeader>
            <template #top-left>
                <VsHeaderDrawerToggle
                    section="top"
                    class="d-lg-none"
                    content-key="universal-nav"
                    drawer-key="header-top"
                >
                    Our sites
                </VsHeaderDrawerToggle>

                <VsHeaderListGroup
                    class="d-lg-inline-flex d-none"
                    section="top"
                >
                    <VsHeaderListGroupItem
                        v-for="(site, i) in header.ourSites"
                        :key="i"
                        :href="site.href"
                        :active="site.isActive"
                        :tracking-id="site.trackingId"
                    >
                        {{ site.title }}
                    </VsHeaderListGroupItem>
                </VsHeaderListGroup>
            </template>

            <template #top-right>
                <VsHeaderLoginButton>
                    <template v-slot:logged-in-content>
                        <span class="d-none">Log out</span>
                        <span>Hi Boudicca... (not you?)</span>
                    </template>
                    <template v-slot:logged-out-content>
                        <span>Login</span>
                    </template>
                </VsHeaderLoginButton>

                <VsHeaderDrawerToggle
                    section="top"
                    class="d-lg-none"
                    content-key="language-list"
                    drawer-key="header-top"
                >
                    ENG
                </VsHeaderDrawerToggle>

                <VsHeaderDropdown
                    text="EN"
                    right
                    class="d-none d-lg-flex"
                    section="top"
                >
                    <VsDropdownItem
                        v-for="(lang, i) in header.languages"
                        :key="i"
                        :href="lang.href"
                        :active="lang.isActive"
                        :tracking-id="lang.trackingId"
                    >
                        {{ lang.title }}
                    </VsDropdownItem>
                </VsHeaderDropdown>
            </template>

            <template #top-drawer>
                <VsDrawerContent
                    content-key="universal-nav"
                    open-focus="content"
                >
                    <VsListGroup
                        class="d-lg-none"
                        tabindex="-1"
                    >
                        <VsDrawerListItem
                            v-for="(site, i) in header.ourSites"
                            :key="i"
                            :href="site.href"
                            :external="site.isExternal"
                            :active="site.isActive"
                            :tracking-id="site.trackingId"
                            full-width
                        >
                            {{ site.title }}
                        </VsDrawerListItem>
                    </VsListGroup>
                </VsDrawerContent>
                <VsDrawerContent
                    content-key="language-list"
                    open-focus="content"
                >
                    <VsListGroup
                        class="d-lg-none"
                        tabindex="-1"
                    >
                        <VsDrawerListItem
                            v-for="(lang, i) in header.languages"
                            :key="i"
                            :href="lang.href"
                            :active="lang.isActive"
                            :tracking-id="lang.trackingId"
                            full-width
                        >
                            <span>{{ lang.title }}</span>
                        </VsDrawerListItem>
                    </VsListGroup>
                </VsDrawerContent>
            </template>

            <template #logo>
                <VsLogo />
            </template>

            <!-- <template #mobile-nav-button>
        <vs-mobile-nav-button />
      </template> -->

            <template #bottom-right>
                <VsHeaderDrawerToggle
                    drawer-key="header-bottom"
                    content-key="site-search"
                    tag="vs-site-search-toggle-button"
                    section="bottom"
                >
                    Search
                </VsHeaderDrawerToggle>

                <VsHeaderDrawerToggle
                    drawer-key="header-bottom"
                    content-key="favourites-list"
                    tag="vs-favourites-view-button"
                    section="bottom"
                />
            </template>

            <template #bottom-drawer>
                <VsDrawerContent
                    content-key="site-search"
                    ref="siteSearch"
                    open-focus="content"
                >
                    <VsSiteSearch />
                </VsDrawerContent>
                <VsDrawerContent
                    content-key="favourites-list"
                    open-focus="close"
                >
                    <VsFavouritesList />
                </VsDrawerContent>
            </template>

            <template #desktop-nav-toggles>
                <VsDesktopNavToggles
                    v-for="(item, index) in header.mainNav"
                    :level="1"
                    :href="item.href"
                    :is-external="item.isExternal"
                    :title="item.title"
                    :subnav="item.subnav"
                    :promo-list="item.promoList"
                    :promo-item="item.promoItem"
                    :chart-widgets="item.chartWidgets"
                    :key="index"
                    :toggle-id="index + 1"
                />
            </template>

            <!-- <vs-desktop-nav-submenu
        slot="desktop-submenu"
        v-for="(item, index) in header.mainNav"
        :level="1"
        :href="item.href"
        :is-external="item.isExternal"
        :tracking-id="item.trackingId"
        :title="item.title"
        :subnav="item.subnav"
        :promo-list="item.promoList"
        :promo-item="item.promoItem"
        :chart-widgets="item.chartWidgets"
        :key="index"
        :subnavId="index + 1"
      >
        <vs-desktop-nav-list-item
          slot="subnav"
          v-for="(level2, index2) in item.subnav"
          :level="2"
          :href="level2.href"
          :is-external="level2.isExternal"
          :tracking-id="level2.trackingId"
          :title="level2.title"
          :subnav="level2.subnav"
          :key="index2"
        >
          <vs-desktop-nav-list-item
            slot="subnav"
            v-for="(level3, index3) in level2.subnav"
            :level="3"
            :href="level3.href"
            :is-external="level3.isExternal"
            :title="level3.title"
            :tracking-id="level3.trackingId"
            :subnav="level3.subnav"
            :key="index3"
          >
          </vs-desktop-nav-list-item>
        </vs-desktop-nav-list-item>
      </vs-desktop-nav-submenu> -->

            <template #site-navigation>
                <VsSiteNavListItem
                    v-for="(item1, index) in header.mainNav"
                    :href="item1.href"
                    :tracking-id="item1.trackingId"
                    :subnav="item1.subnav"
                    :key="index"
                >
                    {{ item1.title }}
                    <template
                        #subnav
                        v-f="!item1.promo"
                    >
                        <Component
                            :is="
                                item2.promo
                                    ? 'vs-site-nav-list-promo-item'
                                    : 'vs-site-nav-list-item'
                            "
                            v-for="(item2, index2) in item1.subnav"
                            :href="item2.href"
                            :tracking-id="item2.trackingId"
                            :subnav="item2.subnav"
                            :key="index2"
                            :image-href="item2.imageHref"
                        >
                            {{ item2.title }}
                            <template
                                #subnav
                                v-f="!item2.promo"
                            >
                                <Component
                                    :is="
                                        item3.promo
                                            ? 'vs-site-nav-list-promo-item'
                                            : 'vs-site-nav-list-item'
                                    "
                                    v-for="(item3, index3) in item2.subnav"
                                    :href="item3.href"
                                    :tracking-id="item3.trackingId"
                                    :key="index3"
                                    :image-href="item3.imageHref"
                                >
                                    {{ item3.title }}
                                </Component>
                            </template>
                        </Component>
                    </template>
                </VsSiteNavListItem>
            </template>

            <!-- <vs-mobile-nav-list-item
        slot="mobile-nav-items"
        v-for="(item, index) in header.mainNav"
        :level="1"
        :href="item.href"
        :is-external="item.isExternal"
        :tracking-id="item.trackingId"
        :title="item.title"
        :subnav="item.subnav"
        :promo-list="item.promoList"
        :promo-item="item.promoItem"
        :key="index"
      >
        <vs-mobile-nav-list-item
          slot="subnav"
          v-for="(level2, index2) in item.subnav"
          :level="2"
          :href="level2.href"
          :is-external="level2.isExternal"
          :tracking-id="level2.trackingId"
          :title="level2.title"
          :subnav="level2.subnav"
          :promo-list="level2.promoList"
          :promo-item="level2.promoItem"
          :key="index2"
        >
          <vs-mobile-nav-list-item
            slot="subnav"
            v-for="(level3, index3) in level2.subnav"
            :level="3"
            :href="level3.href"
            :is-external="level3.isExternal"
            :title="level3.title"
            :tracking-id="level3.trackingId"
            :subnav="level3.subnav"
            :promo-list="level3.promoList"
            :promo-item="level3.promoItem"
            :key="index3"
          >
          </vs-mobile-nav-list-item>
        </vs-mobile-nav-list-item>
      </vs-mobile-nav-list-item> -->
        </VsHeader>

        <div class="container">
            <div class="row">
                <div
                    id="content-container"
                    class="col p-3"
                    tabindex="3000"
                    style="height:1000"
                >
                    Dummy page contents
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            contentContainer: null,
            siteSearchModule: null,
        }
    },
    mounted() {
        this.contentContainer = document.getElementById("content-container")
        this.siteSearchModule = this.$refs.siteSearch
    },
}
</script>
