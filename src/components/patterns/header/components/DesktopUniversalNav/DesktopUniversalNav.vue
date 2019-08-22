<template>
  <component :is="type" :aria-label="name">
    <ul class="vs-desktop-universal-nav__list list-unstyled d-flex">
      <li role="none" v-for="(link, index) in dropdownList" :key="index">
        <a
          class="vs-desktop-universal-nav__link"
          :href="link.href"
          :target="link.isExternal ? '_blank' : false"
          :data-vs-track="link.trackingId"
        >
          {{ link.title }}
        </a>
      </li>
    </ul>
  </component>
</template>

<script>
export default {
  name: "VsDesktopUniversalNav",
  status: "prototype",
  release: "0.0.1",
  components: {},
  data() {
    return {
      show: false,
    }
  },
  props: {
    /**
     * The html element name used for the component
     */
    type: {
      type: String,
      default: "nav",
    },
    dropdownList: {
      type: Array,
      required: true,
    },
    name: {
      type: String,
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/text";
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/type";
@import "../../styles/placeholders";

.vs-desktop-universal-nav__list {
  margin: 0;
}

.vs-desktop-universal-nav__link {
  @extend %uni-nav-button-style;
  padding-left: 10px;
  padding-right: 10px;
}
</style>

<docs>
  ```jsx
  const ourSites = require("../../../../../assets/fixtures/ourSites.json")

  <div style="position: relative; height: 100vh; display: flex; justify-content: space-between;">
    <vs-desktop-universal-nav
        name="Our sites"
        :dropdown-list="ourSites"
    />
  </div>
  ```
</docs>
