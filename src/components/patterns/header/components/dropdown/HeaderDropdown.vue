<template>
  <vs-dropdown
    v-bind="$attrs"
    class="vs-header__dropdown"
    :class="{ 'vs-header__dropdown--top': isTop }"
  >
    <slot v-for="(_, name) in $slots" :name="name" :slot="name" />
  </vs-dropdown>
</template>

<script>
import { VsDropdown } from "@components/patterns/dropdown"

/**
 * Dropdown component for use in the header.
 */
export default {
  name: "VsHeaderDropdown",
  components: {
    VsDropdown,
  },
  props: {
    /**
     * Indicates which section of the header the dropdown is styled for.
     * `top, bottom`
     */
    section: {
      type: String,
      default: "bottom",
      validator: value => {
        return value.match(/(top|bottom)/)
      },
    },
  },
  computed: {
    isTop() {
      return this.section === "top"
    },
  },
}
</script>

<style lang="scss">
@import "../../styles/placeholders";

.vs-header__dropdown {
  .dropdown-toggle {
    @extend %header-nav-item;
  }

  &.vs-header__dropdown--top {
    .dropdown-toggle {
      @extend %header-nav-item-top;
    }
  }

  .dropdown-menu {
    margin-top: -1px;
  }
}
</style>

<docs>
  ```js
  <div>
    <div class="card bg-primary mb-2">
      <vs-header-dropdown text="TOP" section="top">
        <vs-dropdown-item href="https://www.google.com">Google</vs-dropdown-item>
        <vs-dropdown-item>bbbb</vs-dropdown-item>
        <vs-dropdown-item>feeeeee</vs-dropdown-item>
      </vs-header-dropdown>
    </div>
    
    <vs-header-dropdown text="BOTTOM">
      <vs-dropdown-item>sdafdfsaf</vs-dropdown-item>
      <vs-dropdown-item>bbbb</vs-dropdown-item>
      <vs-dropdown-item>feeeeee</vs-dropdown-item>
    </vs-header-dropdown>
  </div>
  ```
</docs>
