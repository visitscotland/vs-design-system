<template>
  <section class="content-section" :class="{ lead: intro, ['content-section-' + type]: true }">
    <vs-heading level="2" v-if="heading">{{ heading }}</vs-heading>

    <slot />
  </section>
</template>

<script>
import "../elements/Heading"

/**
 * Section component
 */
export default {
  name: "VsContentSection",
  status: "prototype",
  release: "0.0.1",
  props: {
    /**
     * Section heading
     */
    heading: {
      type: String,
    },

    /**
     * Whether or not this section should be styled as introductory content
     * via .lead class
     */
    intro: {
      type: Boolean,
    },

    /**
     * The type or location of the section
     * `article, aside`
     */
    type: {
      type: String,
      default: "article",
      validator: value => {
        return value.match(/(article|aside)/)
      },
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";
@import "~bootstrap/scss/utilities";

.content-section-aside {
  @extend .p-4;
  @extend .mb-5;
  background: #eee;
}
</style>

<docs>
  ```jsx
  <div>
  
  </div>
  ```
</docs>
