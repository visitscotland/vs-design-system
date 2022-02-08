<template>
    <VsButton
        class="vs-site-search"
        icon="search"
        size="md"
        :animate="false"
        :variant="showSearchForm ? 'light' : 'primary'"
        @click.native="toggleAction"
    >
        <!-- Default slot for Search button text -->
        <span class="sr-only-lg-down">
            <slot />
        </span>
    </VsButton>
</template>

<script>
import VsButton from '@components/elements/button/Button';
/**
 * TODO: Document Usage
 *
 * @displayName Site Search
 */

export default {
    name: 'VsSiteSearch',
    components: {
        VsButton,
    },
    props: {
        /**
         * Toggles the search form
         */
        showSearchForm: {
            type: Boolean,
            default: false,
        },
    },
    methods: {
        toggleAction() {
            this.showSearchForm = !this.showSearchForm;
            this.$emit('toggleAction', this.showSearchForm);
        },
    },
};
</script>

<style lang="scss">
.vs-site-search {
    &.vs-button.btn{
        z-index: 1;
        padding: $spacer-2;
        height: 45px;

        @include media-breakpoint-up(lg) {
            padding: $spacer-3;
            height: 55px;
        }

        .vs-icon{
            margin-right: 0;
        }
    }
}
</style>

<docs>
  ```jsx
    <VsSiteSearch>
        Search
    </VsSiteSearch>
  ```
</docs>
