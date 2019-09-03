<template>
  <component
    data-test="skip-to-search-button"
    :is="type"
    class="sr-only sr-only-focusable vs-skip-to"
    @click.prevent="handleClick"
  >
    <span data-test="skip-to-search-text">{{ title }}</span>
    <vs-icon
      data-test="skip-to-search-chevron-icon"
      name="chevron-down"
      size="xs"
      variant="reverse-white"
    />
  </component>
</template>

<script>
import VsIcon from "../../../../elements/icon/Icon"

export default {
  name: "VsSkipToSearch",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsIcon,
  },
  data() {
    return {}
  },
  methods: {
    handleClick() {
      document.getElementById("search-toggle-trigger").click()
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
    target: {
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
@import "../../styles/placeholders";
@import "../../styles/vs-skip-to";
</style>

<docs>
  ```jsx
  <div style="position: relative; height: 100px;">
    <vs-skip-to-search
      title="Skip to Search"
    />
  </div>
  ```
</docs>
