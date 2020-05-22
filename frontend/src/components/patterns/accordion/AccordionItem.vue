<template>
    <BCard
        no-body
        class="vs-accordion-item"
    >
        <BCardHeader role="tab">
            <!-- @slot Slot to contain Header for accordion item.
            Defaults to Accordion Toggle button. If component is responsive
            and has a breakPoint it will show title instead of button on larger screens. -->
            <VsAccordionToggle
                :aria-controls="'panel_' + controlId"
                :visible="show"
                :variant="variant"
                :class="toggleAccordionBtn"
                @toggle-panel="onButtonClick"
            >
                <!-- @slot Put the title here  -->
                <slot name="title" />

                <template #icon-open>
                    <!-- @slot Slot for the icon to show when accordion item is open  -->
                    <slot name="icon-open" />
                </template>
                <template #icon-closed>
                    <!-- @slot Slot for the icon to show when accordion item is closed  -->
                    <slot name="icon-closed" />
                </template>
            </VsAccordionToggle>

            <span
                class="d-none vs-accordion-item__title"
                :class="toggleResponsiveItem"
            >
                <!-- @slot Put the title here  -->
                <slot name="title" />
            </span>
        </BCardHeader>

        <BCardBody
            v-show="show"
            :id="'panel_' + controlId"
            class="vs-accordion-item__panel"
            :class="toggleResponsiveItem"
        >
            <!-- @slot The default slot is the content for the accordion  -->
            <slot />
        </BCardBody>
    </BCard>
</template>

<script>
import VsAccordionToggle from "@components/patterns/accordion/AccordionToggle"

import {
    BCard, BCardHeader, BCardBody,
} from "bootstrap-vue"

/**
 * Accordion item for use within the Accordion component.
 * Contains a button to toggle the panel open or closed.
 */
export default {
    name: "VsAccordionItem",
    components: {
        VsAccordionToggle,
        BCard,
        BCardHeader,
        BCardBody,
    },
    props: {
        /**
         * The aria control ID used for panel ID to match button aria control
         */
        controlId: {
            type: String,
            required: true,
        },
        /**
         * If this is provided, the accordion expands above
         * the specified viewport `xs, sm, md, lg, xl`
         */
        itemBreakPoint: {
            type: String,
            default() {
                return this.breakPoint
            },
            validator: (value) => value.match(/(xs|sm|md|lg|xl)/),
        },
        /**
         * Choose to show accordion open or closed by default
         */
        openByDefault: {
            type: Boolean,
            default: true,
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
            show: this.openByDefault,
        }
    },
    computed: {
        toggleAccordionBtn() {
            if (!this.itemBreakPoint) {
                return "d-block"
            }

            return this.itemBreakPoint === "xs" ? "d-none" : `d-${this.itemBreakPoint}-none`
        },
        toggleResponsiveItem() {
            if (!this.itemBreakPoint) {
                return ""
            }

            return this.itemBreakPoint === "xs" ? "d-block" : `d-${this.itemBreakPoint}-block`
        },
    },
    /**
     * Injects breakPoint prop provided by Accordion
     */
    inject: ["breakPoint"],
    methods: {
        onButtonClick() {
            this.show = !this.show
        },
    },
}
</script>

<style lang="scss">
.vs-accordion-item {
    border-bottom: 1px solid $color-gray-shade-2;

    .btn.vs-accordion-item__toggle-btn {
        text-align: left;

        .icon.icon-xs {
            height: 16px;
            width: 16px;
            padding: 0;
        }
    }

    .vs-accordion-item__title {
        background: $color-gray-shade-7;
        color: $color-white;
        padding: $spacer-3;
        line-height: 1;
        font-weight: 500;
    }

    .vs-accordion-item__panel {
        background: $color-gray-shade-6;
        color: $color-white;
        border-top: 1px solid $color-gray-shade-2;
        padding-bottom: $spacer-2;
    }
}
</style>

<docs>
  ```js
    <vs-accordion>
        <vs-accordion-item :openByDefault="true" variant="dark" control-id="1">
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

        <vs-accordion-item :openByDefault="false" variant="dark" control-id="3">
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
        <vs-accordion-item :openByDefault="false" variant="dark" control-id="2">
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
