<template>
    <div
        class="vs-accordion"
        role="tablist"
    >
        <slot />
    </div>
</template>

<script>


/**
 * Dropdown component for lists of links for example.
 */
export default {
    name: "VsAccordion",
    components: {
    },
}
</script>

<style lang="scss">
</style>

<docs>
  ```js
    <vs-accordion>
        <vs-accordion-item :visible=true variant="dark">
            <span slot="title">
                This is a title
            </span>

            Lorem ipsum dolor sit amet, consectetur adipiscing elit. In luctus quam non
            enim commodo consectetur. Curabitur accumsan non mauris et laoreet. Praesent
            maximus sagittis mauris a finibus. Morbi fringilla, lorem ut fringilla sollicitudin,
            turpis enim venenatis ipsum, vitae finibus sem tellus sit amet mauris.
        </vs-accordion-item>
    </vs-accordion>

  ```
</docs>
