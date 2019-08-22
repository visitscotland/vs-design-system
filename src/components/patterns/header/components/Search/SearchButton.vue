<template>
  <component
    :is="type"
    class="vs-search__button"
    id="searchbutton"
    v-b-toggle.collapse-search
    @click="setFocus()"
  >
    <span class="d-md-none sr-only">Toggle Search</span>
    <vs-icon name="search" size="sm" variant="reverse-white" />
    <span class="d-none d-md-flex vs-search__search-button-text">
      <span class="sr-only">Toggle</span> Search
    </span>
  </component>
</template>

<script>
import VsIcon from "../../../../elements/icon/Icon"
import { VBToggle } from "bootstrap-vue"

export default {
  name: "VsSearchButton",
  status: "prototype",
  release: "0.0.1",
  components: { VsIcon },
  data() {
    return {}
  },
  directives: { "b-toggle": VBToggle },
  methods: {
    setFocus() {
      setTimeout(() => {
        document.getElementById("searchinput").focus()
      }, 100)
    },
  },
  props: {
    /**
     * The html element name used for the component
     */
    type: {
      type: String,
      default: "button",
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/text";
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/utilities/screenreaders";
@import "../../styles/placeholders";
@import "../../styles/animations";

.vs-search__button {
  @extend %button-reset;
  @extend %main-nav-button-style;

  background-color: $color-pink;
  color: $color-white;

  &:focus {
    outline: none;
    box-shadow: inset 0 -3px 0 0 $color-white;

    @include media-breakpoint-up(md) {
      box-shadow: 0 3px 0 0 $color-white;
    }
  }

  @include media-breakpoint-up(md) {
    align-items: center;
    box-shadow: 0 5px 0 0 $color-pink;
    display: flex;
    font-size: 1.125rem;
    font-weight: $font-weight-semi-bold;
    justify-content: center;
    padding: 0 1.25rem 0 0.5rem;
    width: auto;
  }

  &[aria-expanded="true"] {
    background-color: $gray-tint-7;
    color: $color-base-text;

    @include media-breakpoint-up(md) {
      box-shadow: 0 5px 0 0 $gray-tint-7;
    }

    svg {
      fill: $gray-shade-2 !important;
    }

    &:after {
      height: 0;
    }
  }
}
</style>

<docs>
  ```jsx
  <div style="position: relative; height: 100px;">
    <vs-search-button
    />
  </div>
  ```
</docs>
