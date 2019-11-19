<template>
  <b-dropdown v-bind="$attrs" class="vs-dropdown" no-caret>
    <slot v-for="(_, name) in nonButtonContentSlots" :name="name" :slot="name" />
    <template #button-content>
      <slot name="button-content">
        {{ text }}
      </slot>
      <vs-icon name="chevron-down" variant="reverse-white" size="xxs" class="ml-1" />
    </template>
    <slot />
  </b-dropdown>
</template>

<script>
import { BDropdown } from "bootstrap-vue"
import { reject } from "lodash"

/**
 * Dropdown component for lists of links for example.
 */
export default {
  name: "VsDropdown",
  components: {
    BDropdown,
  },
  props: {
    text: {
      type: String,
    },
  },
  computed: {
    nonButtonContentSlots() {
      return reject(this.$slots, { name: "button-content" })
    },
  },
}
</script>

<style lang="scss">
@import "~bootstrap/scss/dropdown";
@import "~bootstrap/scss/buttons";
@import "~bootstrap-vue/src/components/dropdown/dropdown";

.vs-dropdown {
  .dropdown-toggle {
    .icon {
      transition: all 150ms ease-in-out;
    }
  }
  &.show {
    .dropdown-toggle {
      .icon {
        transform: rotate(-180deg);
      }
    }
  }
}
</style>

<docs>
  ```js
  <vs-dropdown text="Dropdown">
    <vs-dropdown-item href="https://www.google.com">Google</vs-dropdown-item>
    <vs-dropdown-item>bbbb</vs-dropdown-item>
    <vs-dropdown-item>feeeeee</vs-dropdown-item>
  </vs-dropdown>
  ```
</docs>
