<template>
    <div class="lead">
        <slot />
    </div>
</template>

<script>
/**
 * Applies Bootstrap font-size lead
 */
export default {
    name: "VsLeadParagraph",
    status: "prototype",
    release: "0.1.0",
    props: {},
    computed: {},
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";
.lead ::v-deep {
    line-height: 1.75rem;
}
</style>

<docs>
  ```jsx
  <div>
    <vs-lead-paragraph>Lorem ipsum dolor sit amet.</vs-lead-paragraph>
  </div>
  ```
</docs>
