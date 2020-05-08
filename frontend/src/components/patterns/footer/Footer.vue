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

                        <div class="p-3">
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit. In luctus quam non
                            enim commodo consectetur. Curabitur accumsan non mauris et laoreet. Praesent
                            maximus sagittis mauris a finibus. Morbi fringilla, lorem ut fringilla sollicitudin,
                            turpis enim venenatis ipsum, vitae finibus sem tellus sit amet mauris.
                        </div>
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

                        <div class="p-3">
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit. In luctus quam non
                            enim commodo consectetur. Curabitur accumsan non mauris et laoreet. Praesent
                            maximus sagittis mauris a finibus. Morbi fringilla, lorem ut fringilla sollicitudin,
                            turpis enim venenatis ipsum, vitae finibus sem tellus sit amet mauris.
                        </div>
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

                        <div class="p-3">
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit. In luctus quam non
                            enim commodo consectetur. Curabitur accumsan non mauris et laoreet. Praesent
                            maximus sagittis mauris a finibus. Morbi fringilla, lorem ut fringilla sollicitudin,
                            turpis enim venenatis ipsum, vitae finibus sem tellus sit amet mauris.
                        </div>
                    </vs-accordion-item>
                </vs-col>
            </vs-row>
        </vs-footer-nav-list>
    </vs-footer>
  ```
</docs>
