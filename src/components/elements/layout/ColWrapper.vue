<template>
  <component :is="theTag" :tag="isVsCol ? tag : false" v-bind="breakpointAttrs">
    <slot />
  </component>
</template>

<script>
import { some, partial, get } from "lodash"

import colsMixin from "@/mixins/cols.js"
import VsCol from "./Col"
import breakpoints from "@/utils/breakpoints.bootstrap-vue.config"

/**
 * The ColWrapper component is used by components to wrap a the VsCol component
 * and includes some shared functionality for that scenario
 */
export default {
  name: "VsColWrapper",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsCol,
  },
  mixins: [colsMixin],
  props: {
    /**
     * The html element name used for the component
     */
    tag: {
      type: String,
      default: "div",
    },
  },
  computed: {
    /**
     * Returns the tag name for the component
     *
     * If isVsCol returns true then the tag is vs-col,
     * otherwise it's the value of the tag prop
     */
    theTag() {
      return this.isVsCol ? "vs-col" : this.tag
    },
    /**
     * Returns true if one of the breakpoint props is specified
     */
    isVsCol() {
      return some(breakpoints, breakpoint => {
        return get(this, breakpoint)
      })
    },
  },
}
</script>

<style lang="scss" scoped></style>

<docs>
  ```jsx
  <div>
    <vs-container>
      <vs-row>
        <vs-col-wrapper tag="article" sm="10" lg="8" xxl="6">
          <p>This vs-col-wrapper component has (breakpoint-specific) col attributes so is rendered as a vs-col component (with an article tag, as specified).</p>
        </vs-col-wrapper>
      </vs-row>

      <vs-row>
        <vs-col>
          <vs-col-wrapper tag="article">
            <p>This vs-col-wrapper component has no col attributes so is directly rendered as an article component.</p>
          </vs-col-wrapper>
        </vs-col>
      </vs-row>
    </vs-container>

  </div>
  ```
</docs>
