<template>
    <div class="vs-footer-nav-list pb-md-6">
        <VsAccordion
            :responsive="responsive"
            :break-point="breakPoint"
        >
            <!-- @slot Default slot to contain Accordion Items in the footer -->
            <slot />
        </VsAccordion>
    </div>
</template>

<script>
import VsAccordion from "@components/patterns/accordion/Accordion"

/**
 * The FooterNavList is a set of accordion items used for
 * separate list of links in the footer.
 */

export default {
    name: "VsFooterNavList",
    components: {
        VsAccordion,
    },
    props: {
        /**
         * If this is provided, the accordion will change to an open
         * menu with a title instead of button.
         */
        responsive: {
            type: Boolean,
            default: false,
        },
        /**
         * If this is provided and responsive is set, this prop
         * decides when the accordion expands.
         */
        breakPoint: {
            type: String,
            default: "",
        },
    },
}
</script>

<style lang="scss">
.vs-footer-nav-list {
    .vs-accordion-item {
        .vs-accordion-item__title {
            padding: $spacer-3 0;
        }

        .btn.vs-accordion-toggle {
            text-transform: none !important;
            letter-spacing: initial;
            padding: $spacer-3;
            line-height: 1;
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

        @include media-breakpoint-up(md) {
            border-bottom: 0;
            border-left: 1px solid $color-gray-shade-2;
            padding: 0 $spacer-3;
            background: $color-theme-dark;

            .vs-accordion-item__title {
                padding-top: $spacer-1;
            }

            .vs-accordion-item__panel {
                padding-bottom: 0;
                background: $color-theme-dark;
                border-top: 0;
            }
        }
    }
}
</style>

<docs>
  ```js
    <vs-footer-nav-list break-point="md">
        <vs-row>
            <vs-col cols="12" md="4" lg="3">
                <vs-accordion-item :visible="false" variant="dark" aria-control-id="1" class="border-left-0">
                    <span slot="title">
                        Visitor information
                    </span>

                    <span slot="icon-open">
                        <vs-icon name="chevron-up" variant="light" size="xs" />
                    </span>

                    <span slot="icon-closed">
                        <vs-icon name="chevron-right" variant="light" size="xs" />
                    </span>

                    <vs-list unstyled class="pb-2">
                        <vs-footer-nav-list-item
                            href="#"
                            link-text="Brochures"
                        ></vs-footer-nav-list-item>
                        <vs-footer-nav-list-item
                            href="#"
                            link-text="VisitScotland iCentres"
                        ></vs-footer-nav-list-item>
                        <vs-footer-nav-list-item
                            href="#"
                            link-text="iKnow Scotland Community"
                        ></vs-footer-nav-list-item>
                        <vs-footer-nav-list-item
                            href="#"
                            link-text="VisitScotland Awards"
                        ></vs-footer-nav-list-item>
                    </vs-list>
                </vs-accordion-item>
            </vs-col>

            <vs-col cols="12" md="4" lg="3">
                <vs-accordion-item :visible="false" variant="dark" aria-control-id="2">
                    <span slot="title">
                        Get in touch
                    </span>

                    <span slot="icon-open">
                        <vs-icon name="chevron-up" variant="light" size="xs" />
                    </span>

                    <span slot="icon-closed">
                        <vs-icon name="chevron-right" variant="light" size="xs" />
                    </span>

                    <vs-list unstyled class="pb-2">
                        <vs-footer-nav-list-item
                            href="#"
                            link-text="Brochures"
                        ></vs-footer-nav-list-item>
                        <vs-footer-nav-list-item
                            href="#"
                            link-text="VisitScotland iCentres"
                        ></vs-footer-nav-list-item>
                        <vs-footer-nav-list-item
                            href="#"
                            link-text="iKnow Scotland Community"
                        ></vs-footer-nav-list-item>
                        <vs-footer-nav-list-item
                            href="#"
                            link-text="VisitScotland Awards"
                        ></vs-footer-nav-list-item>
                    </vs-list>
                </vs-accordion-item>
            </vs-col>

            <vs-col cols="12" md="4" lg="3">
                <vs-accordion-item :visible="false" variant="dark" aria-control-id="3">
                    <span slot="title">
                        Our other sites
                    </span>

                    <span slot="icon-open">
                        <vs-icon name="chevron-up" variant="light" size="xs" />
                    </span>

                    <span slot="icon-closed">
                        <vs-icon name="chevron-right" variant="light" size="xs" />
                    </span>

                    <vs-list unstyled class="pb-2">
                        <vs-footer-nav-list-item
                            href="#"
                            link-text="Brochures"
                            external
                        ></vs-footer-nav-list-item>
                        <vs-footer-nav-list-item
                            href="#"
                            link-text="VisitScotland iCentres"
                            external
                        ></vs-footer-nav-list-item>
                        <vs-footer-nav-list-item
                            href="#"
                            link-text="iKnow Scotland Community"
                            external
                        ></vs-footer-nav-list-item>
                        <vs-footer-nav-list-item
                            href="#"
                            link-text="VisitScotland Awards"
                            external
                        ></vs-footer-nav-list-item>
                    </vs-list>
                </vs-accordion-item>
            </vs-col>
        </vs-row>
    </vs-footer-nav-list>

  ```
</docs>
