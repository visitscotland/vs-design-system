<template>
    <div
        class="vs-rich-text-wrapper"
        :class="[
            `vs-rich-text-wrapper--variant-${variant}`,
            {
                lead: variant === 'lead',
            },
        ]"
    >
        <!-- @slot Default slot for rich text content -->
        <slot />
    </div>
</template>

<script>
/**
 * The rich text wrapper renders HTML or text
 * content from WYSIWYG editors.
 *
 * @displayName Rich Text Wrapper
 */
export default {
    name: 'VsRichTextWrapper',
    status: 'prototype',
    release: '0.0.1',
    props: {
        /**
         * The font size used by the wrapper
         * `normal|lead`
         */
        variant: {
            type: String,
            default: 'normal',
            validator: (value) => value.match(/(normal|lead)/),
        },
    },
};
</script>

<style lang="scss">
.vs-rich-text-wrapper {
    &--variant-normal{
        font-family: $font-family-base;
        font-size: $body-font-size;

        @include media-breakpoint-up(md) {
            font-size: $body-font-size-md;
        }
    }

    &--variant-lead {
        line-height: $line-height-lead;
        font-size: $lead-font-size;

        @include media-breakpoint-up(md) {
            font-size: $lead-font-size-md;
        }
    }
}

</style>
