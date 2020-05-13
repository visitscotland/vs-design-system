<template>
    <div
        class="vs-accordion"
        role="tablist"
    >
        <!-- @slot Default slot to contain Accordion Items -->
        <slot />
    </div>
</template>

<script>


/**
 * Accordion wrapper used with AccordionItems.
 */
export default {
    name: "VsAccordion",
    components: {
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
    /**
     * Provides responsive prop to be injected to child component AccordionItem
     */
    provide() {
        return {
            responsive: this.responsive,
            breakPoint: this.breakPoint,
        }
    },
}
</script>

<style lang="scss">
</style>

<docs>
  ```js

    <h3>Basic Accordion</h3>
    <vs-accordion>
        <vs-accordion-item :visible="true" variant="dark" index="1">
            <span slot="title">
                This is a title
            </span>

            <span slot="icon-open">
                <vs-icon name="chevron-down" variant="light" size="xs" />
            </span>

            <span slot="icon-closed">
                <vs-icon name="chevron-up" variant="light" size="xs" />
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
                This is a title
            </span>

            <span slot="icon-open">
                <vs-icon name="chevron-down" variant="light" size="xs" />
            </span>

            <span slot="icon-closed">
                <vs-icon name="chevron-up" variant="light" size="xs" />
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
                This is a title
            </span>

            <span slot="icon-open">
                <vs-icon name="chevron-down" variant="light" size="xs" />
            </span>

            <span slot="icon-closed">
                <vs-icon name="chevron-up" variant="light" size="xs" />
            </span>

            <div class="p-3">
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. In luctus quam non
                enim commodo consectetur. Curabitur accumsan non mauris et laoreet. Praesent
                maximus sagittis mauris a finibus. Morbi fringilla, lorem ut fringilla sollicitudin,
                turpis enim venenatis ipsum, vitae finibus sem tellus sit amet mauris.
            </div>
        </vs-accordion-item>
    </vs-accordion>

    <h3  class="mt-6">Responsive Accordion</h3>
    <vs-accordion :responsive="true" break-point="sm">
        <vs-accordion-item :visible="true" variant="dark" index="4">
            <span slot="title">
                This is a title
            </span>

            <span slot="icon-open">
                <vs-icon name="chevron-down" variant="light" size="xs" />
            </span>

            <span slot="icon-closed">
                <vs-icon name="chevron-up" variant="light" size="xs" />
            </span>

            <div class="p-3">
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. In luctus quam non
                enim commodo consectetur. Curabitur accumsan non mauris et laoreet. Praesent
                maximus sagittis mauris a finibus. Morbi fringilla, lorem ut fringilla sollicitudin,
                turpis enim venenatis ipsum, vitae finibus sem tellus sit amet mauris.
            </div>
        </vs-accordion-item>

        <vs-accordion-item :visible="false" variant="dark" index="5">
            <span slot="title">
                This is a title
            </span>

            <span slot="icon-open">
                <vs-icon name="chevron-down" variant="light" size="xs" />
            </span>

            <span slot="icon-closed">
                <vs-icon name="chevron-up" variant="light" size="xs" />
            </span>

            <div class="p-3">
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. In luctus quam non
                enim commodo consectetur. Curabitur accumsan non mauris et laoreet. Praesent
                maximus sagittis mauris a finibus. Morbi fringilla, lorem ut fringilla sollicitudin,
                turpis enim venenatis ipsum, vitae finibus sem tellus sit amet mauris.
            </div>
        </vs-accordion-item>
        <vs-accordion-item :visible="false" variant="dark" index="6">
            <span slot="title">
                This is a title
            </span>

            <span slot="icon-open">
                <vs-icon name="chevron-down" variant="light" size="xs" />
            </span>

            <span slot="icon-closed">
                <vs-icon name="chevron-up" variant="light" size="xs" />
            </span>

            <div class="p-3">
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. In luctus quam non
                enim commodo consectetur. Curabitur accumsan non mauris et laoreet. Praesent
                maximus sagittis mauris a finibus. Morbi fringilla, lorem ut fringilla sollicitudin,
                turpis enim venenatis ipsum, vitae finibus sem tellus sit amet mauris.
            </div>
        </vs-accordion-item>
    </vs-accordion>

  ```
</docs>
