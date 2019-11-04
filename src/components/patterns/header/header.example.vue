<template>
  <div style="overflow-y: scroll; min-height: 600px;">
    <vs-skip-to :target="contentContainer">
      Skip to Content
    </vs-skip-to>

    <vs-drawer-toggle content-key="site-search" drawer-key="header-bottom" type="vs-skip-to-button">
      Skip to Search
    </vs-drawer-toggle>

    <vs-header>
      <template #top-left>
        <vs-header-drawer-toggle
          section="top"
          class="d-lg-none"
          content-key="universal-nav"
          drawer-key="header-top"
        >
          Our sites
        </vs-header-drawer-toggle>

        <vs-header-list-group class="d-lg-inline-flex d-none" section="top">
          <vs-header-list-group-item
            v-for="(site, i) in header.ourSites"
            :key="i"
            :href="site.href"
            :active="site.isActive"
            :tracking-id="site.trackingId"
          >
            {{ site.title }}
          </vs-header-list-group-item>
        </vs-header-list-group>
      </template>

      <template #top-right>
        <vs-header-login-button>
          <template v-slot:logged-in-content>
            <span class="d-none">Log out</span>
            <span>Hi Boudicca... (not you?)</span>
          </template>
          <template v-slot:logged-out-content>
            <span>Login</span>
          </template>
        </vs-header-login-button>

        <vs-header-drawer-toggle
          section="top"
          class="d-lg-none"
          content-key="language-list"
          drawer-key="header-top"
        >
          ENG
        </vs-header-drawer-toggle>

        <vs-header-dropdown text="EN" right class="d-none d-lg-flex" section="top">
          <vs-dropdown-item
            v-for="(lang, i) in header.languages"
            :key="i"
            :href="lang.href"
            :active="lang.isActive"
            :tracking-id="lang.trackingId"
          >
            {{ lang.title }}
          </vs-dropdown-item>
        </vs-header-dropdown>
      </template>

      <template #top-drawer>
        <vs-drawer-content content-key="universal-nav" open-focus="content">
          <vs-list-group class="d-lg-none" tabindex="-1">
            <vs-drawer-list-item
              v-for="(site, i) in header.ourSites"
              :key="i"
              :href="site.href"
              :external="site.isExternal"
              :active="site.isActive"
              :tracking-id="site.trackingId"
              full-width
            >
              {{ site.title }}
            </vs-drawer-list-item>
          </vs-list-group>
        </vs-drawer-content>
        <vs-drawer-content content-key="language-list" open-focus="content">
          <vs-list-group class="d-lg-none" tabindex="-1">
            <vs-drawer-list-item
              v-for="(lang, i) in header.languages"
              :key="i"
              :href="lang.href"
              :active="lang.isActive"
              :tracking-id="lang.trackingId"
              full-width
            >
              <span>{{ lang.title }}</span>
            </vs-drawer-list-item>
          </vs-list-group>
        </vs-drawer-content>
      </template>

      <template #logo>
        <vs-logo />
      </template>

      <!-- <template #mobile-nav-button>
        <vs-mobile-nav-button />
      </template> -->

      <template #bottom-right>
        <vs-site-search-toggle-button
          drawer-key="header-bottom"
          content-key="site-search"
          section="bottom"
        >
          Search
        </vs-site-search-toggle-button>

        <vs-header-drawer-toggle
          drawer-key="header-bottom"
          content-key="favourites-list"
          :href="favourite.href"
          :title="favourite.title"
          type="vs-favourites-button"
          section="bottom"
        />
      </template>

      <template #bottom-drawer>
        <vs-drawer-content content-key="site-search" ref="siteSearch" open-focus="content">
          <vs-site-search />
        </vs-drawer-content>
        <vs-drawer-content content-key="favourites-list" open-focus="close">
          <vs-favourites-list />
        </vs-drawer-content>
      </template>

      <template #desktop-nav-toggles>
        <vs-desktop-nav-toggles
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
          :toggleId="index + 1"
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
        <vs-site-nav-item
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
          <vs-site-nav-item
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
            <vs-site-nav-item
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
            </vs-site-nav-item>
          </vs-site-nav-item>
        </vs-site-nav-item>
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
    </vs-header>

    <div class="container">
      <div class="row">
        <div id="content-container" class="col p-3" tabindex="3000" style="height:1000">
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
