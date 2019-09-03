<template>
  <component
    data-test="skip-to-content-button"
    :is="type"
    class="sr-only sr-only-focusable vs-skip-to"
    @click="handleClick()"
  >
    <span data-test="skip-to-content-text">{{ title }}</span>
    <vs-icon
      data-test="skip-to-content-chevron-icon"
      name="chevron-down"
      size="xs"
      variant="reverse-white"
    />
  </component>
</template>

<script>
import VsIcon from "../../../../elements/icon/Icon"

export default {
  name: "VsSkipToContent",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsIcon,
  },
  data() {
    return {}
  },
  props: {
    /**
     * The html element name used for the component
     */
    type: {
      type: String,
      default: "button",
    },
    title: {
      type: String,
    },
  },
  methods: {
    handleClick() {
      var landmarkMain = document.getElementById("main")
      landmarkMain.tabIndex = -1
      landmarkMain.focus()
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
    <vs-skip-to-content
      title="Skip to Content"
    />
  </div>
  ```
</docs>
