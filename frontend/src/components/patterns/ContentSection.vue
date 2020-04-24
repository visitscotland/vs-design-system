<template>
    <section
        class="content-section"
        :class="{ lead: lead, ['content-section-' + tag]: true }"
    >
        <VsHeading
            level="3"
            v-if="heading"
        >
            {{ heading }}
        </VsHeading>

        <slot />
    </section>
</template>

<script>
import VsHeading from "../elements/heading"

/**
 * Section component
 */
export default {
    name: "VsContentSection",
    components: {
        VsHeading,
    },
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
         * Whether or not this section should be styled as leading content
         * via .lead class
         */
        lead: {
            type: Boolean,
        },

        /**
         * The element used for the container
         * `article, aside, div`
         */
        tag: {
            type: String,
            default: "article",
            validator: (value) => value.match(/(article|aside|div)/),
        },
    },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";
@import "~bootstrap/scss/utilities";

.content-section {
    @extend .mb-5;
}

.content-section-aside {
    @extend .p-4;
    background: #eee;
}
</style>

<docs>
  ```jsx
  <vs-container>
    <vs-row>
      <vs-article cols="8" md="12">
        <vs-content-section heading="Article section title">
          Article section content
        </vs-content-section>
      </vs-article>
      <br /><br />
      <vs-sidebar>
        <vs-content-section tag="aside" heading="Aside section title">
          Aside section content
        </vs-content-section>
      </vs-sidebar>
    </vs-row>
  </vs-container>

  ```
</docs>
