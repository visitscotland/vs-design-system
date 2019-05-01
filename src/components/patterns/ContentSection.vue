<template>
  <section
    class="content-section"
    :class="{ 'content-section-introductory': introductory, ['content-section-' + type]: true }"
  >
    <vs-heading level="2">{{ heading }}</vs-heading>

    <slot />
  </section>
</template>

<script>
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
     */
    introductory: {
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
.content-section-introductory {
  font-weight: bold;
}
</style>

<docs>
  ```jsx
  <div>
  
  </div>
  ```
</docs>
