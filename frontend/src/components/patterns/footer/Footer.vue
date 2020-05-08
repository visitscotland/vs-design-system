<template>
    <div class="vs-footer pt-6">
        <VsContainer>
            <slot />
        </VsContainer>
    </div>
</template>

<script>
import { VsContainer } from "@components/elements/layout"

export default {
    name: "VsFooter",
    status: "prototype",
    release: "0.1.0",
    components: {
        VsContainer,
    },
    props: {
    },
}
</script>

<style lang="scss" scoped>
.vs-footer {
    background: $color-theme-dark;

    @include media-breakpoint-down(sm) {
        .container {
            max-width: 100%;
            padding: 0;
        }
    }
}
</style>

<docs>
  ```
    <vs-footer>
        <vs-footer-nav-list>
            <vs-row>
                <vs-col cols="12" md="4" lg="3">
                    <vs-accordion-item :visible="false" variant="dark" index="1">
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
                    </vs-accordion-item>
                </vs-col>
                <vs-col cols="12" md="4" lg="3">
                    <vs-accordion-item :visible="false" variant="dark" index="2">
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
                    </vs-accordion-item>
                </vs-col>
                <vs-col cols="12" md="4" lg="3">
                    <vs-accordion-item :visible="false" variant="dark" index="3">
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
            </vs-row>
        </vs-footer-nav-list>
    </vs-footer>
  ```
</docs>
