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
    .vs-footer-nav-list{
    }
</style>

<docs>
  ```js
    <vs-accordion-item :visible="false" variant="dark" index="1">
        <span slot="title">
            This is a title
        </span>

        <span slot="icon-open">
            <VsIcon name="chevron-down" variant="light" size="xs" />
        </span>

        <span slot="icon-closed">
            <VsIcon name="chevron-up" variant="light" size="xs" />
        </span>

        <div class="py-3">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. In luctus quam non
            enim commodo consectetur. Curabitur accumsan non mauris et laoreet. Praesent
            maximus sagittis mauris a finibus. Morbi fringilla, lorem ut fringilla sollicitudin,
            turpis enim venenatis ipsum, vitae finibus sem tellus sit amet mauris.
        </div>
    </vs-accordion-item>

    <vs-accordion-item :visible="false" variant="dark" index="2">
        <span slot="title">
            This is a title
        </span>

        <span slot="icon-open">
            <VsIcon name="chevron-down" variant="light" size="xs" />
        </span>

        <span slot="icon-closed">
            <VsIcon name="chevron-up" variant="light" size="xs" />
        </span>

        <div class="py-3">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. In luctus quam non
            enim commodo consectetur. Curabitur accumsan non mauris et laoreet. Praesent
            maximus sagittis mauris a finibus. Morbi fringilla, lorem ut fringilla sollicitudin,
            turpis enim venenatis ipsum, vitae finibus sem tellus sit amet mauris.
        </div>
    </vs-accordion-item>
    <vs-accordion-item :visible="false" variant="dark" index="3">
        <span slot="title">
            This is a title
        </span>

        <span slot="icon-open">
            <VsIcon name="chevron-down" variant="light" size="xs" />
        </span>

        <span slot="icon-closed">
            <VsIcon name="chevron-up" variant="light" size="xs" />
        </span>

        <div class="py-3">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. In luctus quam non
            enim commodo consectetur. Curabitur accumsan non mauris et laoreet. Praesent
            maximus sagittis mauris a finibus. Morbi fringilla, lorem ut fringilla sollicitudin,
            turpis enim venenatis ipsum, vitae finibus sem tellus sit amet mauris.
        </div>
    </vs-accordion-item>


  ```
</docs>
