<template>
    <BTab
        class="vs-tab__item"
        data-test="vs-tab__item"
        title-link-class="vs-tab-link"
        :title="title"
    >
        <!-- @slot default slot for tab item body content -->
        <slot />
    </BTab>
</template>

<script>

import { BTab } from 'bootstrap-vue';
/**
 * Tab item for use within the Tabs component.
 *
 * @displayName Tab Item
 */
export default {
    name: 'VsTabItem',
    components: {
        BTab,
    },
    props: {
        /**
         * Title of the tab button
         */
        title: {
            type: String,
            required: true,
        },
    },
};
</script>

<style lang="scss">
.vs-tabs{
    .nav-item{
        flex: auto;
        align-self: flex-end;
    }

    .vs-tab-link.nav-link{
        background-color: $color-secondary-gray-tint-6;
        color: $color-base-text;
        font-size: $display3-size;
        font-weight: $font-weight-bold;
        border-color: $color-secondary-gray-tint-6 $color-secondary-gray-tint-6 $color-white;
        padding: $spacer-5;

        &.active{
            background-color: $color-white;
            padding: $spacer-7 $spacer-5;
        }
    }

    .vs-tab__item{
        background-color: $color-white;
        color: $color-base-text;
        text-align: left;
    }
}
</style>

<docs>
  ```js
  ```
</docs>
