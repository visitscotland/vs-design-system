<template>
  <nav aria-label="breadcrumbs">
    <b-breadcrumb class="flex-nowrap p-0" v-bind="$attrs">
      <slot />
    </b-breadcrumb>
  </nav>
</template>

<script>
import { BBreadcrumb } from "bootstrap-vue"

/**
 * Breadcrumbs are used to indicate the current page position within the navigational hierarchy.
 */

export default {
  name: "VsBreadcrumb",
  status: "prototype",
  release: "0.0.1",
  components: {
    BBreadcrumb,
  },
  props: {
    items: {
      type: Array,
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/breadcrumb";
</style>

<docs>
  ```jsx
  <vs-breadcrumb>
    <vs-breadcrumb-item 
      v-for="(item, index) in breadcrumb.breadcrumb"
      :key="index"
      :href="item.href"
      :active="item.active"
      :text="item.name"
      >
    </vs-breadcrumb-item>
  </vs-breadcrumb>
  ```
</docs>
