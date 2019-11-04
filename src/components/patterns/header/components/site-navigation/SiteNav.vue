<template>
  <!-- <div> -->
  <!-- <b-collapse id="collapse-mobile-nav"> -->
  <!-- <div class="vs-mobile-nav__wrapper"> -->
  <vs-container class="vs-site-nav bg-white" :class="{ 'vs-site-nav--open': isOpen }">
    <vs-row no-gutters>
      <vs-col lg="7" offset-lg="3" type="nav">
        <ul class="vs-site-nav__list list-unstyled d-lg-flex">
          <slot />
        </ul>
      </vs-col>
    </vs-row>
  </vs-container>
  <!-- </div> -->
  <!-- </b-collapse> -->
  <!-- </div> -->
</template>

<script>
// import { BCollapse } from "bootstrap-vue"

export default {
  name: "VsSiteNav",
  status: "prototype",
  release: "0.1.0",
  components: {
    // BCollapse,
  },
  props: {
    isOpen: {
      type: Boolean,
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/text";
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/type";
@import "~bootstrap/scss/utilities/screenreaders";
@import "../../styles/placeholders";

// .collapse:not(.show) {
//   display: none;
//   opacity: 0;
// }

// .collapsing {
//   position: relative;
//   height: 0;
//   overflow: hidden;
//   opacity: 1;
//   transition: all 50ms ease-in-out;
// }

.vs-site-nav {
  @extend %default-box-shadow;

  @include media-breakpoint-down(md) {
    display: none;
  }

  &.vs-site-nav--open {
    display: initial;
  }
}

// .vs-mobile-nav {
//   display: flex;
//   margin: 0 -1rem;
// }

// .vs-mobile-nav__button {
//   @extend %button-reset;
//   @extend %main-nav-button-style;

//   &:focus {
//     @extend %focus-pink-inset;
//   }
// }

.vs-site-nav__list {
  height: 100vh;
  width: 100%;
  z-index: 2;

  @include media-breakpoint-up(lg) {
    position: absolute;
    top: -70px;
  }

  @include media-breakpoint-down(md) {
    width: auto;
  }
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
