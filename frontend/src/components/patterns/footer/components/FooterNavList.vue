<template>
    <div class="vs-footer-nav-list pb-md-6">
        <VsAccordion>
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
}
</script>

<style lang="scss">
.vs-footer-nav-list {
    .vs-accordion-item {
        @include media-breakpoint-up(md) {
            border-left: 1px solid $color-gray-shade-2;
            padding: 0 $spacer-3;
            background: $color-theme-dark;
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
    }
}
</style>

<docs>
  ```js
    <vs-footer-nav-list>
        <vs-row>
            <vs-col cols="12" md="4" lg="3">
                <vs-accordion-item :visible="false" variant="dark" index="1" :responsive="true">
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
                            linkText="Brochures"
                        ></vs-footer-nav-list-item>
                        <vs-footer-nav-list-item
                            href="#"
                            linkText="VisitScotland iCentres"
                        ></vs-footer-nav-list-item>
                        <vs-footer-nav-list-item
                            href="#"
                            linkText="iKnow Scotland Community"
                        ></vs-footer-nav-list-item>
                        <vs-footer-nav-list-item
                            href="#"
                            linkText="VisitScotland Awards"
                        ></vs-footer-nav-list-item>
                    </vs-list>
                </vs-accordion-item>
            </vs-col>

            <vs-col cols="12" md="4" lg="3">
                <vs-accordion-item :visible="false" variant="dark" index="2" :responsive="true">
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
                            linkText="Brochures"
                        ></vs-footer-nav-list-item>
                        <vs-footer-nav-list-item
                            href="#"
                            linkText="VisitScotland iCentres"
                        ></vs-footer-nav-list-item>
                        <vs-footer-nav-list-item
                            href="#"
                            linkText="iKnow Scotland Community"
                        ></vs-footer-nav-list-item>
                        <vs-footer-nav-list-item
                            href="#"
                            linkText="VisitScotland Awards"
                        ></vs-footer-nav-list-item>
                    </vs-list>
                </vs-accordion-item>
            </vs-col>

            <vs-col cols="12" md="4" lg="3">
                <vs-accordion-item :visible="false" variant="dark" index="3" :responsive="true">
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
                            linkText="Brochures"
                            external
                        ></vs-footer-nav-list-item>
                        <vs-footer-nav-list-item
                            href="#"
                            linkText="VisitScotland iCentres"
                            external
                        ></vs-footer-nav-list-item>
                        <vs-footer-nav-list-item
                            href="#"
                            linkText="iKnow Scotland Community"
                            external
                        ></vs-footer-nav-list-item>
                        <vs-footer-nav-list-item
                            href="#"
                            linkText="VisitScotland Awards"
                            external
                        ></vs-footer-nav-list-item>
                    </vs-list>
                </vs-accordion-item>
            </vs-col>
        </vs-row>
    </vs-footer-nav-list>

  ```
</docs>
