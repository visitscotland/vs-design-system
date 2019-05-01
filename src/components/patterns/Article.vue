<template>
  <component :is="type" class="article"> <slot /> </component>
</template>

<script>
/**
 * Article component
 */
export default {
  name: "VsArticle",
  status: "prototype",
  release: "0.0.1",
  props: {
    /**
     * The html element name used for the component
     */
    type: {
      type: String,
      default: "article",
    },
  },
  computed: {},
}
</script>

<style lang="scss" scoped>
.article {
  @extend .mt-5;
}
/deep/ .heading:first-of-type {
  @extend .mb-5;
}
</style>

<docs>
  ```jsx
  <div>
  
  </div>
  ```
</docs>
