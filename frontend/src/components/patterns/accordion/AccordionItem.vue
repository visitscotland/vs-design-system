<template>
    <BCard
        no-body
        class="vs-accordion-item"
    >
        <BCardHeader role="tab">
            <VsAccordionToggle
                :index="index"
                :visible="show"
                :variant="variant"
                @toggle-panel="onButtonClick"
            >
                <!-- @slot Put the title here  -->
                <slot name="title" />

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
            </VsAccordionToggle>
        </BCardHeader>

        <BCardBody
            v-show="show"
            :id="'panel_' + index"
            class="vs-accordion-item__panel"
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
            show: this.visible,
        }
    },
    methods: {
        onButtonClick(e) {
            this.show = e
        },
    },
}
</script>

<style lang="scss">
    .vs-accordion-item{
        border-bottom: 1px solid $color-gray-shade-2;

        .btn.vs-accordion-item__toggle-btn {
            text-align: left;

            .icon.icon-xs {
                height: 16px;
                width: 16px;
                padding: 0;
            }
        }

        .vs-accordion-item__panel{
            background: #2B2929;
            color: #ffffff;
            border-top: 1px solid $color-gray-shade-2;
        }
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
            <VsIcon name="chevron-down" variant="light" size="xs" />
        </span>

        <span slot="icon-closed">
            <VsIcon name="chevron-up" variant="light" size="xs" />
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
            <VsIcon name="chevron-down" variant="light" size="xs" />
        </span>

        <span slot="icon-closed">
            <VsIcon name="chevron-up" variant="light" size="xs" />
        </span>

        <div class="p-3">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. In luctus quam non
            enim commodo consectetur. Curabitur accumsan non mauris et laoreet. Praesent
            maximus sagittis mauris a finibus. Morbi fringilla, lorem ut fringilla sollicitudin,
            turpis enim venenatis ipsum, vitae finibus sem tellus sit amet mauris.
        </div>
    </vs-accordion-item>
  ```
</docs>
