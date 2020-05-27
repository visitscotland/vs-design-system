<template>
    <VsContainer class="vs-footer-nav-list">
        <div class="vs-footer-nav-list">
            <VsAccordion :break-point="breakPoint">
                <VsRow>
                    <!-- @slot Default slot to contain Accordion Items in the footer -->
                    <slot />
                </VsRow>
            </VsAccordion>
        </div>
    </VsContainer>
</template>

<script>
import VsAccordion from "@components/patterns/accordion/Accordion"
import { VsRow, VsContainer } from "@components/elements/layout"

/**
 * The FooterNavList is a set of menu items used for
 * separate list of links in the footer.
 */

export default {
    name: "VsFooterNavList",
    components: {
        VsAccordion,
        VsRow,
        VsContainer,
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
         * the specified viewport `xs,sm, md, lg, xl`
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
    @include media-breakpoint-down(sm) {
        &.container {
            max-width: 100%;
            padding: 0;

            .row {
                margin-right: 0;
                margin-left: 0;

                > .col,
                > [class*="col-"] {
                    padding-right: 0;
                    padding-left: 0;
                }
            }
        }
    }
}
</style>

<docs>
  ```js
    <vs-footer>
        <vs-footer-nav-list break-point="md">
            <vs-col cols="12" md="4" lg="3">
                <vs-footer-accordion-item
                    :openByDefault="false"
                    variant="dark"
                    control-id="1"
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
                </vs-footer-accordion-item>
            </vs-col>

            <vs-col cols="12" md="4" lg="3">
                <vs-footer-accordion-item
                    :openByDefault="false"
                    variant="dark"
                    control-id="2"
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
                </vs-footer-accordion-item>
            </vs-col>

            <vs-col cols="12" md="4" lg="3">
                <vs-footer-accordion-item
                    :openByDefault="false"
                    variant="dark"
                    control-id="3"
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
                </vs-footer-accordion-item>
            </vs-col>
            <vs-col cols="12" lg="3" xl="2">
                <vs-footer-accordion-item
                    :openByDefault="true"
                    variant="dark"
                    control-id="1"
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
                </vs-footer-accordion-item>
            </vs-col>
        </vs-footer-nav-list>
    </vs-footer>
  ```
</docs>
