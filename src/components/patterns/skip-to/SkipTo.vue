<template>
  <component
    :is="type"
    class="vs-skip-to sr-only sr-only-focusable d-inline-flex p-absolute align-items-center flex-column py-2 px-3"
    @click.prevent="handle"
    :tabindex="tabindex"
  >
    <slot />
    <vs-icon name="chevron-down" size="xs" variant="reverse-white" />
  </component>
</template>

<script>
import VsIcon from "@components/elements/icon/Icon"
import { VueComponent } from "Vue"
import { isFunction, get, isNumber } from "lodash"

/**
 * The SkipTo component provides users of assistive
 * technologies with a focusable control that, when
 * activated, moves the focus to a provided target
 * element and/or fires an event handler.
 */
export default {
  name: "VsSkipTo",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsIcon,
  },
  props: {
    /**
     * The tabindex attribute for this element
     */
    tabindex: {
      type: String,
      required: true,
    },
    /**
     * The html element name used for the component
     */
    type: {
      type: String,
      default: "button",
    },
    /**
     * The target element to skip to. Pass a Vue ref - e.g.
     * from this.$refs - or an HTML Element
     */
    target: {
      default: null,
    },
  },
  methods: {
    handle() {
      let element

      if (isFunction(get(this.target, "focus"))) {
        element = this.target
      } else if (isFunction(get(this.target, "$el.focus"))) {
        element = this.target.$el
      }

      if (element) {
        if (!isNumber(element.tabIndex)) {
          element.tabIndex = -1
        }
        element.focus()
      }

      /**
       * Activated event
       */
      this.$emit("activated")
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/text";
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/utilities/screenreaders";
@import "~bootstrap/scss/utilities/spacing";

.vs-skip-to {
  background-color: $color-base-text;
  box-shadow: 0 8px 6px -6px rgba(0, 0, 0, 0.3);
  color: $color-white;
  letter-spacing: 2px;
  left: 50%;
  transform: translate(-50%);
  top: 0;
  transition: background-color 250ms;
  z-index: 6;

  &:focus {
    outline: none;
    box-shadow: inset 0 -3px 0 0 $color-base-text;
  }

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
  ```js

  new Vue({
    template: `
      <bs-wrapper class="d-flex flex-row">
        <bs-wrapper class="card mr-2" style="width:10rem" tabindex="10000">
          <bs-wrapper class="card-body">
            Click here then press tab to navigate
          </bs-wrapper>
        </bs-wrapper>

        <vs-skip-to :target="ref" @activated="activated('ref')" tabindex="10001">
          Skip to Vue ref target
        </vs-skip-to>

        <vs-skip-to :target="element" @activated="activated('HTML element')" tabindex="10002">
          Skip to HTML Element target
        </vs-skip-to>
        
        <bs-wrapper class="card mr-2" style="width:10rem" tabindex="10003">
          <bs-wrapper class="card-body">
            Thing to skip
          </bs-wrapper>
        </bs-wrapper>

        <bs-wrapper ref="refTarget" class="card mr-2" style="width:10rem" tabindex="10004">
          <bs-wrapper class="card-body">
            This target was passed as a Vue ref
          </bs-wrapper>
        </bs-wrapper>

        <bs-wrapper id="element-target" class="card" style="width:10rem" tabindex="10005">
          <bs-wrapper class="card-body">
            This target was passed as an HTML Element
          </bs-wrapper>
        </bs-wrapper>
      </bs-wrapper>
    `,
    data() {
      return { 
        element: null,
        ref: null
      }
    },
    methods: {
      activated(target) {
        alert("Skip to with \"" + target + "\" target was clicked on")
      }
    },
    mounted: function() {
      this.element = document.getElementById("element-target")
      this.ref = this.$refs.refTarget
    },
  })




  ```
</docs>
