<template>
    <VsFooterAccordionItem
        class="vs-footer-social-accordion-item"
        :control-id="controlId"
        :item-break-point="itemBreakPoint"
        :open-by-default="openByDefault"
        :variant="variant"
    >
        <template #title>
            <!-- @slot Put the title here  -->
            <slot name="title" />
        </template>

        <!-- @slot The default slot is the content for the accordion  -->
        <slot />
    </VsFooterAccordionItem>
</template>

<script>
import VsFooterAccordionItem from "@components/patterns/footer/components/FooterAccordionItem"

/**
 * The FooterSocialAccordionItem is used inside the FooterNavList.
 * It's a wrapper for social media links in the footer.
 */

export default {
    name: "VsFooterSocialAccordionItem",
    components: {
        VsFooterAccordionItem,
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
.vs-footer-social-accordion-item {
    @include media-breakpoint-down(md) {
        border-left: 0;
        border-top: 1px solid $color-gray-shade-2;
        padding: $spacer-8 0 0 0;

        .vs-accordion-item__title {
            padding: 0;
        }

        .vs-list {
            display: flex;
            justify-content: center;
        }
    }

    @include media-breakpoint-down(sm) {
        border-top: 0;
        padding: 0;

        .vs-accordion-item__panel {
            background: $color-theme-dark;
            border: 0;
        }

        .vs-accordion-item__title {
            padding: $spacer-6 $spacer-3 0;
        }
    }
}
</style>

<docs>
  ```js
    <vs-footer>
        <vs-footer-nav-list break-point="md">
            <vs-row>
                <vs-col cols="12" md="6">
                    <vs-footer-social-accordion-item
                        :openByDefault="true"
                        variant="dark"
                        control-id="1"
                    >
                        <span slot="title">
                            Find us on
                        </span>

                        <vs-list unstyled class="mt-5 mt-md-0 mt-lg-5 pr-lg-8 pr-xl-4">
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
                    </vs-footer-social-accordion-item>
                </vs-col>
            </vs-row>
        </vs-footer-nav-list>
    </vs-footer>

  ```
</docs>
