<template>
  <component data-test="mobile-universal-nav" :is="type" class="vs-dropdown" :aria-label="name">
    <button
      data-test="mobile-universal-nav-button"
      class="vs-dropdown__button"
      v-b-toggle.collapse-universal-nav
    >
      <span> <span class="sr-only">Toggle menu for </span>{{ name }}</span>
      <div class="vs-dropdown__icon-wrapper">
        <vs-icon
          data-test="mobile-univeral-nav-chevron-icon"
          name="chevron-down"
          variant="reverse-white"
          size="xxs"
        />
      </div>
    </button>
    <b-collapse id="collapse-universal-nav">
      <ul
        data-test="mobile-univeral-nav-list"
        aria-role="menubar"
        :aria-label="name"
        class="vs-dropdown__list list-unstyled"
      >
        <li
          data-test="mobile-universal-nav-list-item"
          role="none"
          class="vs-dropdown__list-item"
          :class="{ 'vs-dropdown__list-item--active': link.isActive }"
          v-for="(link, index) in dropdownList"
          :key="index"
        >
          <a
            class="vs-dropdown__link"
            :href="link.href"
            :target="link.isExternal ? '_blank' : false"
            :data-vs-track="link.trackingId"
            @keydown="checkKeydown($event, index === last)"
          >
            {{ link.title }}
            <div v-if="link.isExternal" class="vs-dropdown__external-icon-wrapper">
              <vs-icon
                data-test="mobile-universal-nav-external-link-icon"
                name="external-link"
                size="xxs"
                variant="reverse-white"
              />
            </div>
          </a>
        </li>
      </ul>
    </b-collapse>
  </component>
</template>

<script>
import VsIcon from "../../../../elements/icon/Icon"
import { VBToggle, BCollapse } from "bootstrap-vue"

export default {
  name: "VsMobileUniversalNav",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsIcon,
    BCollapse,
  },
  directives: { "b-toggle": VBToggle },
  data() {
    return {}
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
  computed: {
    last() {
      return Object.keys(this.dropdownList).length - 1
    },
  },
  methods: {
    checkKeydown($event, isLast) {
      if ($event.key === "Tab" && !$event.shiftKey && isLast) {
        this.close()
      }
    },
    close() {
      this.$root.$emit("bv::toggle::collapse", "collapse-universal-nav")
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
@import "../../styles/animations";
@import "../../styles/vs-dropdown";
</style>

<docs>
  ```jsx
  const ourSites = require("../../../../../assets/fixtures/ourSites.json")

  <div style="position: relative; height: 100vh; display: flex; justify-content: space-between;">
    <vs-mobile-universal-nav
        name="Our sites"
        :dropdown-list="ourSites"
    />
  </div>
  ```
</docs>
