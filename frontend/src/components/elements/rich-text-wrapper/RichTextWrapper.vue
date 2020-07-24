<template>
    <div
        class="vs-rich-text-wrapper"
        :class="[
            $style.root,
            variantClass,
            {
                lead: variant === 'lead',
            }
        ]"
    >
        <slot />
    </div>
</template>

<script>
import { upperFirst } from 'lodash';

/**
  * Text Wrapper is used to wrap and render HTML or text strings from
  * WYSIWYG editors or others and apply styles when needed.
 */
export default {
    name: 'VsRichTextWrapper',
    status: 'prototype',
    release: '0.0.1',
    props: {
        /**
         * The font size used by the wrapper
         * `normal | lead`
         */
        variant: {
            type: String,
            default: 'normal',
            validator: (value) => value.match(/(normal|lead)/),
        },
    },
    computed: {
        variantClass() {
            return this.$style[`variant${upperFirst(this.variant)}`];
        },
    },
};
</script>

<style lang="scss">
@import "~bootstrap/scss/type";
</style>

<style lang="scss" module>
.variantNormal {
    font-family: $font-family-base;
    font-size: $font-size-base;

}
.variantLead {
    line-height: $line-height-lead;
}

</style>

<docs>
```jsx
    <bs-wrapper class="mb-9">
        <h3>Normal Variant</h3>
        <vs-rich-text-wrapper>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis
            <vs-link href="#foo" external>eget</vs-link> ante urna.<br/> Pellentesque aliquam
            faucibus enim fermentum fringilla. Vivamus ultrices dictum justo ac porta.
            Quisque mattis <b>tortor</b> dapibus tellus aliquet, finibus lacinia felis pulvinar.
        </vs-rich-text-wrapper>
    </bs-wrapper>

    <bs-wrapper class="mb-4">
        <h3>Lead Variant</h3>
        <vs-rich-text-wrapper variant="lead">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis
            <vs-link href="#foo" external>eget</vs-link> ante urna.<br/> Pellentesque aliquam
            faucibus enim fermentum fringilla. Vivamus ultrices dictum justo ac porta.
            Quisque mattis <b>tortor</b> dapibus tellus aliquet, finibus lacinia felis pulvinar.
        </vs-rich-text-wrapper>
    </bs-wrapper>
```
</docs>
