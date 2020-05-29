<template>
    <VsAccordionItem
        class="vs-footer-accordion-item mb-md-4"
        :control-id="controlId"
        :item-break-point="itemBreakPoint"
        :open-by-default="openByDefault"
        :variant="variant"
    >
        <template #title>
            <!-- @slot Put the title here  -->
            <slot name="title" />
        </template>

        <template #icon-open>
            <!-- @slot Slot for the icon to show when accordion item is open  -->
            <slot name="icon-open" />
        </template>
        <template #icon-closed>
            <!-- @slot Slot for the icon to show when accordion item is closed  -->
            <slot name="icon-closed" />
        </template>

        <!-- @slot The default slot is the content for the accordion  -->
        <slot />
    </VsAccordionItem>
</template>

<script>
import VsAccordionItem from "@components/patterns/accordion/AccordionItem"

/**
 * The FooterAccordionItem is an accordion item used inside the FooterNavList.
 * It's a wrapper for FooterNavListItems in the footer.
 */

export default {
    name: "VsFooterAccordionItem",
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
         * If this is provided, the accordion expands above
         * the specified viewport `xs, sm, md, lg, xl`
         */
        itemBreakPoint: {
            type: String,
            default() {
                return this.breakPoint
            },
            validator: (value) => value.match(/(xs|sm|md|lg|xl)/),
        },
        /**
         * Choose to show accordion open or closed by default
         */
        openByDefault: {
            type: Boolean,
            default: true,
        },
        /**
         * Variant for which button to show in headers
         */
        variant: {
            type: String,
            default: "primary",
        },
    },
}
</script>

<style lang="scss">
.vs-footer-accordion-item {
    .vs-accordion-item__title {
        margin: $spacer-3 0;
    }

    .btn.vs-accordion-toggle {
        text-transform: none !important;
        letter-spacing: initial;
        padding: $spacer-3;
        line-height: $line-height-xs;
        font-weight: 500;

        &:hover {
            background: $color-theme-dark;
            border-color: $color-theme-dark;
            text-decoration: underline;
        }

        &:focus {
            box-shadow: 0 0 0 1px $color-yellow;
        }
    }

    @include media-breakpoint-up(sm) {
        .btn.vs-accordion-toggle {
            padding: $spacer-3 $spacer-6;
        }
    }

    @include media-breakpoint-up(md) {
        border-bottom: 0;
        border-left: 1px solid $color-gray-shade-2;
        padding: 0 $spacer-3;
        background: $color-theme-dark;

        .vs-accordion-item__title {
            margin-top: $spacer-1;
        }

        .vs-accordion-item__panel {
            padding-bottom: 0;
            background: $color-theme-dark;
            border-top: 0;
        }
    }
}
</style>

<docs>
  ```js
    <vs-footer>
        <vs-footer-nav-list break-point="md">
            <vs-col cols="12" md="6">
                <vs-footer-accordion-item
                    :openByDefault="true"
                    variant="dark"
                    control-id="1"
                    class="border-left-0"
                >
                    <span slot="title">
                        Find us on
                    </span>

                    <span slot="icon-open">
                        <vs-icon name="chevron-up" variant="light" size="xs" />
                    </span>

                    <span slot="icon-closed">
                        <vs-icon name="chevron-right" variant="light" size="xs" />
                    </span>

                    <vs-list unstyled class="pb-2">
                        <vs-footer-social-item
                            href="#"
                            icon="facebook"
                        ></vs-footer-social-item>
                        <vs-footer-social-item
                            href="#"
                            icon="twitter"
                        ></vs-footer-social-item>
                        <vs-footer-social-item
                            href="#"
                            icon="youtube"
                        ></vs-footer-social-item>
                        <vs-footer-social-item
                            href="#"
                            icon="instagram"
                        ></vs-footer-social-item>
                    </vs-list>
                </vs-footer-accordion-item>
            </vs-col>
        </vs-footer-nav-list>
    </vs-footer>
  ```
</docs>
