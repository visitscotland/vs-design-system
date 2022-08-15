<template>
    <section class="vs-skip-to d-flex">
        <p class="vs-skip-to__label">
            Skip to:
        </p>
        <!-- @slot Default slot for skip to links  -->
        <slot />
    </section>
</template>

<script>
// import {
//     isFunction, get, isNumber,
// } from 'lodash';
// import VsSkipToButton from './components/SkipToButton';

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
    // components: {
    //     VsSkipToButton,
    // },
    // props: {
    //     /**
    //      * The tabindex attribute for this element. For some reason
    //      * tabindex isn't passed to the root element so we must do
    //      * that manually.
    //      */
    //     tabindex: {
    //         type: String,
    //         default: '',
    //     },
    //     /**
    //      * The target element to skip to: a Vue ref - e.g.
    //      * from this.$refs - or a DOM Element.
    //      */
    //     target: {
    //         type: Object,
    //         default: null,
    //     },
    // },
    // methods: {
    //     skipTo() {
    //         let element;

    //         if (isFunction(get(this.target, 'focus'))) {
    //             element = this.target;
    //         } else if (isFunction(get(this.target, '$el.focus'))) {
    //             element = this.target.$el;
    //         }

    //         if (element) {
    //             if (!isNumber(element.tabIndex)) {
    //                 element.tabIndex = -1;
    //             }
    //             element.focus();
    //         }
    //     },
    // },
};
</script>

<style lang="scss">
    .vs-skip-to {
        position: absolute;
        transform: translateY(-100%);
        top: 0;
        height: $spacer-9;
        align-items: center;
        padding: 0 $spacer-2;

        &__label {
            margin: 0;
        }

        &:focus-within {
            position: relative;
            transform: translateY(0);
        }

        .vs-link {
            margin-left: $spacer-2;
        }

        @include media-breakpoint-up(lg) {
            height: $spacer-10;
        }
    }
</style>
