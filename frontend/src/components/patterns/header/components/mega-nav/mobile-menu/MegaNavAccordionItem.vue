<template>
    <VsAccordionItem
        class="vs-mega-nav-accordion-item mb-md-4"
        :class="navLevel"
        :data-unique-id="getUniqueId"
        :control-id="controlId"
        :open-by-default="false"
    >
        <template #title>
            {{ title }}
        </template>

        <template #icon-open>
            <VsIcon
                name="minus"
                variant="light"
                size="xs"
            />
        </template>
        <template #icon-closed>
            <VsIcon
                name="plus"
                orientation="right"
                variant="light"
                size="xs"
            />
        </template>

        <!-- @slot The default slot is the content for the accordion  -->
        <slot />
    </VsAccordionItem>
</template>

<script>

import VsAccordionItem from '@components/patterns/accordion/AccordionItem';

export default {
    name: 'VsMegaNavAccordionItem',
    status: 'prototype',
    release: '0.1.0',
    components: {
        VsAccordionItem,
    },
    props: {
        /**
         * The aria control ID used for panel ID to match button aria control
         */
        controlId: {
            type: String,
            required: true,
        },
        /**
         * The title for the submenu and alias for the testing data-unique-id
         */
        title: {
            type: String,
            required: true,
        },
        /**
         * Level of items in the nav
         */
        level: {
            type: String,
            required: true,
        },
    },
    computed: {
        getUniqueId() {
            let transformedTitle = this.title.toLowerCase();
            transformedTitle = transformedTitle.replace(/\s+/g, '-');

            return `vs-mega-nav-${transformedTitle}`;
        },
        navLevel() {
            return this.level === '1' ? 'vs-mega-nav-accordion-item--level-1' : 'vs-mega-nav-accordion-item--level-2';
        },
    },
};
</script>

<style lang="scss">
.vs-mega-nav-accordion-item {
}

</style>

<docs>
  ```jsx

  ```
</docs>
