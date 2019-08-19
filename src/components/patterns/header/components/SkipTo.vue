<template>
  <component :is="type">
    <vs-container>
      <vs-row>
        <vs-col class="d-inline-flex justify-content-end justify-content-lg-center">
          <a class="sr-only sr-only-focusable vs-skip-to" :href="href">
            {{ title }}
            <vs-icon name="chevron-down" size="xs" variant="reverse-white" />
          </a>
        </vs-col>
      </vs-row>
    </vs-container>
  </component>
</template>

<script>
import VsIcon from "../../../elements/icon/Icon"
import VsContainer from "../../../elements/layout/Container"
import VsRow from "../../../elements/layout/Row"
import VsCol from "../../../elements/layout/Col"
import { ClickOutside } from "../../../../directives/ClickOutside.js"

export default {
  name: "VsSkipTo",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsIcon,
    VsRow,
    VsCol,
    VsContainer,
  },
  data() {
    return {}
  },
  methods: {
    handleClickOutside() {
      console.log("click outside")
    },
  },
  props: {
    /**
     * The html element name used for the component
     */
    type: {
      type: String,
      default: "div",
    },
    href: {
      type: String,
    },
    title: {
      type: String,
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/text";
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/utilities/screenreaders";
@import "../styles/placeholders";
@import "../styles/animations";

.vs-skip-to {
  align-items: center;
  background-color: $color-base-text;
  box-shadow: 0 8px 6px -6px rgba(0, 0, 0, 0.3);
  color: $color-white;
  display: inline-flex;
  flex-direction: column;
  letter-spacing: 2px;
  padding: 2px 2rem;
  position: absolute;
  top: 0;
  transition: background-color 250ms;
  z-index: 6;

  &.sr-only-focusable:focus {
    position: absolute;
  }

  &:hover {
    color: $color-white;
    background-color: $gray-shade-5;
  }
}
</style>

<docs>
  ```jsx
  <div style="position: relative; height: 100px;">
    <vs-skip-to
      href="#main"
      title="Skip to Content"
    />
  </div>
  ```
</docs>
