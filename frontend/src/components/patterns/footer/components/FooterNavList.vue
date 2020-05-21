<template>
    <div class="vs-footer-nav-list pb-md-6">
        <VsAccordion
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
         * If this is provided, the accordion expands above
         * the specified viewport `xs, sm, md, lg, xl`
         */
        breakPoint: {
            type: String,
            default: null,
            validator: (value) => value.match(/(xs|sm|md|lg|xl)/),
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
    <vs-footer>
        <vs-footer-nav-list break-point="md">
            <vs-row>
                <vs-col cols="12" md="4" lg="3">
                    <vs-accordion-item
                        :openByDefault="false"
                        variant="dark"
                        aria-control-id="1"
                        class="border-left-0"
                    >
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
                    <vs-accordion-item
                        :openByDefault="false"
                        variant="dark"
                        aria-control-id="2"
                    >
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
                    <vs-accordion-item
                        :openByDefault="false"
                        variant="dark"
                        aria-control-id="3"
                    >
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
                <vs-col cols="12" lg="3" xl="2">
                    <vs-accordion-item
                        :openByDefault="true"
                        variant="dark"
                        aria-control-id="1"
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

                        <vs-list unstyled class="mt-5 pr-lg-8 pr-xl-4">
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
                    </vs-accordion-item>
                </vs-col>
            </vs-row>
        </vs-footer-nav-list>
    </vs-footer>

  ```
</docs>
