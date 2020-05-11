<template>
    <div class="vs-footer-nav-list">
        <VsAccordion>
            <slot />
        </VsAccordion>
    </div>
</template>

<script>
import VsAccordion from "@components/patterns/accordion/Accordion"

/**
 * Dropdown component for lists of links for example.
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
        .btn.vs-accordion-item__toggle-btn {
            text-transform: none !important;
            letter-spacing: initial;
            padding: $spacer-3;
            line-height: 1;
            font-weight: 500;

            &:hover {
                background: #191919;
                text-decoration: underline;
            }

            &:focus {
                box-shadow: 0 0 0 1px $color_yellow;
            }
        }

        @include media-breakpoint-up(sm) {
            .vs-accordion-item__panel{
                display: block!important;
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
                <vs-accordion-item-responsive :visible="false" variant="dark" index="1">
                    <span slot="title">
                        Visitor information
                    </span>

                    <span slot="icon-open">
                        <VsIcon name="chevron-up" variant="light" size="xs" />
                    </span>

                    <span slot="icon-closed">
                        <VsIcon name="chevron-right" variant="light" size="xs" />
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
                </vs-accordion-item-responsive>
            </vs-col>

            <vs-col cols="12" md="4" lg="3">
                <vs-accordion-item-responsive :visible="false" variant="dark" index="2">
                    <span slot="title">
                        Get in touch
                    </span>

                    <span slot="icon-open">
                        <VsIcon name="chevron-up" variant="light" size="xs" />
                    </span>

                    <span slot="icon-closed">
                        <VsIcon name="chevron-right" variant="light" size="xs" />
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
                </vs-accordion-item-responsive>
            </vs-col>

            <vs-col cols="12" md="4" lg="3">
                <vs-accordion-item-responsive :visible="false" variant="dark" index="3">
                    <span slot="title">
                        Our other sites
                    </span>

                    <span slot="icon-open">
                        <VsIcon name="chevron-up" variant="light" size="xs" />
                    </span>

                    <span slot="icon-closed">
                        <VsIcon name="chevron-right" variant="light" size="xs" />
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
                </vs-accordion-item-responsive>
            </vs-col>
        </vs-row>
    </vs-footer-nav-list>

  ```
</docs>
