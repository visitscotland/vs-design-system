<template>
  <component :is="type" class="sidebar"> <slot /> </component>
</template>

<script>
/**
 * Sidebar component
 */
export default {
  name: "VsSidebar",
  status: "prototype",
  release: "0.0.1",
  props: {
    /**
     * The html element name used for the component
     */
    type: {
      type: String,
      default: "aside",
    },
  },
}
</script>

<style lang="scss" scoped></style>

<docs>
  ```jsx
  <div>

  </div>
  ```
</docs>
