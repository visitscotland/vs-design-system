<template>
    <VsSkipToButton
        @click.native.prevent="skipTo"
        :tabindex="tabindex"
    >
        <slot />
    </VsSkipToButton>
</template>

<script>
import {
    isFunction, get, isNumber,
} from 'lodash';

/**
 * The SkipTo component provides users of assistive
 * technologies with a focusable control that moves the
 * focus to a provided target element when activated.
 *
 * @displayName Skip To
 */
export default {
    name: 'VsSkipTo',
    status: 'prototype',
    release: '0.0.1',
    props: {
        /**
         * The tabindex attribute for this element. For some reason
         * tabindex isn't passed to the root element so we must do
         * that manually.
         */
        tabindex: {
            type: String,
            default: '',
        },
        /**
         * The target element to skip to: a Vue ref - e.g.
         * from this.$refs - or a DOM Element.
         */
        target: {
            type: Object,
            default: null,
        },
    },
    methods: {
        skipTo() {
            let element;

            if (isFunction(get(this.target, 'focus'))) {
                element = this.target;
            } else if (isFunction(get(this.target, '$el.focus'))) {
                element = this.target.$el;
            }

            if (element) {
                if (!isNumber(element.tabIndex)) {
                    element.tabIndex = -1;
                }
                element.focus();
            }
        },
    },
};
</script>

<style lang="scss"></style>