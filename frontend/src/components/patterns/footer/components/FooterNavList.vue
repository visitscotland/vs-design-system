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
    }
}
</style>

<docs>
  ```js
  <vs-footer-nav-list>
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
</vs-footer-nav-list>

  ```
</docs>
