<template>
    <BCard
        no-body
        class="vs-accordion-item"
    >
        <BCardHeader role="tab">
            <VsButton
                :animate="false"
                :aria-expanded="show ? 'true' : 'false'"
                :aria-controls="'panel_' + index"
                aria-haspopup="true"
                @click.native="triggerToggle()"
                class="vs-accordion-item__toggle-btn clearfix"
                block
                :variant="variant"
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
            </VsButton>
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
import VsButton from "@components/elements/button/Button"
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
        VsButton,
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
        triggerToggle() {
            this.show = !this.show
        },
    },
}
</script>

<style lang="scss">
    .vs-accordion-item{
        // border-bottom: 1px solid #AAA9A7;
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
            // border-top: 1px solid #727272
            border-top: 1px solid $color-gray-shade-2;
        }
    }
</style>

<docs>
  ```js
    <vs-accordion-item :visible="true" variant="dark" index="1">
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
