<template>
    <Component
        :is="type"
        class="vs-heading"
        :class="{
            'vs-heading--thin': thin,
            'vs-heading--alternative': alternative,
        }"
    >
        <!-- @slot The main heading content goes here -->
        <slot />

        <span
            class="vs-heading__sub-heading"
            v-if="!!this.$slots['sub-heading']"
        >
            <!-- @slot Slot for sub-heading content -->
            <slot name="sub-heading" />
        </span>
    </Component>
</template>

<script>
import { isNumber } from 'lodash';
/**
 * Headings are used to create visual hierarchy on a page.
 * This component uses the correct heading tags for semantic markup.
 *
 * @displayName Heading
 */
export default {
    name: 'VsHeading',
    status: 'prototype',
    release: '0.1.0',
    props: {
        /**
         * The heading level used for the heading.
         * `1, 2, 3, 4, 5, 6`
         */
        level: {
            type: [String, Number],
            default: '1',
            validator: (value) => (isNumber(value) ? value > 0 && value < 7 : value.match(/(1|2|3|4|5|6)/)),
        },

        /**
         * Use the thin font
         */
        thin: {
            type: Boolean,
        },
        /**
         * Alternative font
         */
        alternative: {
            type: Boolean,
            default: false,
        },
    },
    computed: {
        type() {
            return `h${this.level}`;
        },
        hasSubtitle() {
            return !!this.$slots['sub-heading'];
        },
    },
};
</script>

<style lang="scss">
@import "../../../assets/fonts/fonts.css";

$font-sizes: (
    1: $h1-font-size,
    2: $h2-font-size,
    3: $h3-font-size,
    4: $h4-font-size,
    5: $h5-font-size,
    6: $h6-font-size,
);

$md-font-sizes: (
    1: $h1-font-size-md,
    2: $h2-font-size-md,
    3: $h3-font-size-md,
    4: $h4-font-size-md,
    5: $h5-font-size-md,
    6: $h6-font-size-md,
);

/* Update font sizes */
$sub-font-sizes: (
    2: $h2-sub-font-size,
    3: $h3-sub-font-size,
);

.vs-heading {
    font-family: $headings-font-family;

    @each $level, $size in $font-sizes {
        @at-root h#{$level}#{&} {
            letter-spacing: $size * 0.1;
            margin-bottom: $size;
            margin-top: $size;
            font-size: $size;
        }
    }

    @each $level, $size in $md-font-sizes {
        @at-root h#{$level}#{&} {
            @include media-breakpoint-up(md) {
                letter-spacing: $size * 0.1;
                margin-bottom: $size;
                margin-top: $size;
                font-size: $size;
            }
        }
    }

    &.vs-heading--thin {
        font-family: $headings-font-family-thin;
    }

    .vs-heading__sub-heading {
        font-family: $headings-font-family-thin;
        display: block;

        @each $level, $size in $sub-font-sizes {
            @at-root h#{$level}#{&} {
                letter-spacing: $size * 0.1;
                font-size: $size;
                margin-top: $size * 0.5;
            }
        }
    }

    &--alternative {
        font-family: $font_family_sans_serif;
        font-weight: $font-weight-light;
        letter-spacing: normal;
    }
}

h6.vs-heading, h5.vs-heading{
    font-family: $font-family-sans-serif;
    letter-spacing: normal;
    font-weight: $font-weight-bold;
}

</style>
