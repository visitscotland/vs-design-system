<template>
    <BCard no-body class="vs-accordion-item">
        <BCardHeader role="tab">
            <VsButton
                :animate="false"
                :aria-expanded="show ? 'true' : 'false'"
                :aria-controls="'panel_' + index"
                aria-haspopup="true"
                @click.native="triggerToggle()"
                class="vs-accordion-item__toggle-btn"
                block
                :variant="variant"
            >
                <!-- @slot Put the title here  -->
                <slot name="title" />

                <VsIcon v-if="show" name="chevron-down" variant="light" size="xs" />
                <VsIcon v-else name="chevron-up" variant="light" size="xs" />
            </VsButton>
        </BCardHeader>

        <BCardBody v-show="show" :id="'panel_' + index">
            <!-- @slot The default slot is the content for the accordion  -->
            <slot />
        </BCardBody>
    </BCard>
</template>

<script>
import VsIcon from "@components/elements/icon/Icon"
import VsButton from "@components/elements/button/Button"
import { BCard, BCardHeader, BCardBody } from "bootstrap-vue"

/**
 * Dropdown component for lists of links for example.
 */
export default {
    name: "VsAccordionItem",
    components: {
        VsButton,
        VsIcon,
        BCard,
        BCardHeader,
        BCardBody,
    },
    props: {
        index: {
            type: String,
            default: "",
        },
        visible: {
            type: Boolean,
            default: true,
        },
        variant: {
            type: String,
            default: "primary",
        },
    },
    data() {
        return {
            show: true,
        }
    },
    methods: {
        triggerToggle() {
            this.show = !this.show
        },
    },
}
</script>

<style lang="scss"></style>

<docs>
  ```js
    <vs-accordion-item :visible=true variant="dark">
        <span slot="title">
            This is a title
        </span>

        Lorem ipsum dolor sit amet, consectetur adipiscing elit. In luctus quam non
        enim commodo consectetur. Curabitur accumsan non mauris et laoreet. Praesent
        maximus sagittis mauris a finibus. Morbi fringilla, lorem ut fringilla sollicitudin,
        turpis enim venenatis ipsum, vitae finibus sem tellus sit amet mauris.
    </vs-accordion-item>
  ```
</docs>
