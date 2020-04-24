<template>
    <VsContainer
        class="vs-site-nav bg-white"
        v-hand-down-focus="firstNavItem"
    >
        <VsRow no-gutters>
            <VsCol
                lg="7"
                offset-lg="3"
                type="nav"
            >
                <VsSiteNavList :level="1">
                    <slot />
                </VsSiteNavList>
            </VsCol>
        </VsRow>
    </VsContainer>
</template>

<script>
import {
    VsCol, VsRow, VsContainer,
} from "@components/elements/layout"
import VsSiteNavList from "./SiteNavList"

import HandDownFocus from "@/directives/hand-down-focus.js"

export default {
    name: "VsSiteNav",
    status: "prototype",
    release: "0.1.0",
    components: {
        VsSiteNavList,
        VsCol,
        VsRow,
        VsContainer,
    },
    directives: {
        HandDownFocus,
    },
    computed: {
        firstNavItem() {
            console.log(this.$slots.default)
            debugger
        },
    },
}
</script>

<style lang="scss" scoped>
.vs-site-nav {
    @extend %default-box-shadow;
}
</style>

<docs>
  ```jsx

  <div style="position: relative; height: 600px; overflow-y: scroll; max-width: 400px;">
    <vs-mobile-nav-button></vs-mobile-nav-button>
    <vs-mobile-nav>
      <vs-mobile-nav-list-item
        v-for="(item, index) in header.mainNav"
        :level="1"
        :href="item.href"
        :is-external="item.isExternal"
        :title="item.title"
        :subnav="item.subnav"
        :tracking-id="item.trackingId"
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
          :title="level2.title"
          :subnav="level2.subnav"
          :tracking-id="item.trackingId"
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
            :subnav="level3.subnav"
            :tracking-id="item.trackingId"
            :promo-list="level3.promoList"
            :promo-item="level3.promoItem"
            :key="index3"
          >
          </vs-mobile-nav-list-item>
        </vs-mobile-nav-list-item>
      </vs-mobile-nav-list-item>
    </vs-mobile-nav>
  </div>
  ```
</docs>
