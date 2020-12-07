<template>
    <VsAccordionItem
        class="vs-mega-nav-accordion-item"
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
                variant="secondary"
                size="xs"
            />
        </template>
        <template #icon-closed>
            <VsIcon
                name="plus"
                orientation="right"
                variant="secondary"
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

    &.vs-accordion-item.card .vs-accordion-item__panel.card-body{
        padding: 0;
    }

    &--level-1, &--level-2{
        > .vs-accordion-item__card-header{
            .vs-accordion-toggle{
                &[aria-expanded="true"]{
                    &::after{
                        content: "";
                        position: absolute;
                        display: block;
                        top: 0;
                        left: 0;
                        width: 12px;
                        height: 100%;
                        background: $color-pink;
                    }
                }

                &.btn-primary{
                    letter-spacing: normal;
                    text-align: left;
                    font-weight: $font-weight-normal;
                    transition: none;
                    border: 0;
                    border-top: 1px solid $border-color;

                    &:focus, &:active, &:active:focus {
                        box-shadow: 0 0 0 0.1rem $color-pink inset;
                    }
                }
            }
        }
    }

    &--level-1{
        > .vs-accordion-item__card-header{
            .vs-accordion-toggle.btn-primary{
                background-color: $color-white;
                color: $color-base-text;
                font-size: $h2-font-size;
                padding-left: $spacer-5;
                padding-right: $spacer-5;

                &:hover {
                    background-color: $color-secondary-gray-tint-6;
                }
            }
        }
    }

    &--level-2{
        > .vs-accordion-item__card-header{
            .vs-accordion-toggle.btn-primary{
                background-color: #F8F8F8;
                color: $color-secondary-gray-shade-3;
                font-size: $h3-font-size;
                line-height: $line-height-s;
                padding-left: $spacer-8;
                padding-right: $spacer-5;

                &:hover {
                    background-color: $color-white;
                    color: $color-pink;
                }
            }
        }
    }
}

</style>

<docs>
  ```jsx

  ```
</docs>
