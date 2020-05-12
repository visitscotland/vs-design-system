<template>
    <VsButton
        :animate="false"
        :aria-expanded="show ? 'true' : 'false'"
        :aria-controls="'panel_' + index"
        aria-haspopup="true"
        @click.native="triggerToggle"
        class="vs-accordion-toggle clearfix"
        block
        :variant="variant"
    >
        <!-- @slot Default slot contains text for the button -->
        <slot />

        <div class="float-right">
            <!-- @slot Put the icon to be used when panel is open  -->
            <slot
                v-if="show"
                name="icon-open"
            />

            <!-- @slot Put the icon to be used when panel is closed  -->
            <slot
                v-else
                name="icon-closed"
            />
        </div>
    </VsButton>
</template>

<script>
import VsButton from "@components/elements/button/Button"

/**
 * Accordion toggle button used with AccordionItem
 * It emits an event to the parent to toggle the Accordion panel.
 */
export default {
    name: "VsAccordionToggle",
    components: {
        VsButton,
    },
    props: {
        /**
         * The index used for button to match panel ID
         */
        index: {
            type: String,
            default: "",
        },
        /**
         * Variant for which button to show in headers
         */
        variant: {
            type: String,
            default: "primary",
        },
    },
    data() {
        return {
            show: this.visible,
        }
    },
    methods: {
        triggerToggle() {
            this.show = !this.show
            this.$emit("toggle-panel", this.show)
        },
    },
}
</script>

<style lang="scss">
.btn.vs-accordion-toggle {
    text-align: left;

    .icon.icon-xs {
        height: 16px;
        width: 16px;
        padding: 0;
    }
}
</style>

<docs>
  ```js

  ```
</docs>
