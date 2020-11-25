<template>
    <div
        class="vs-icentre"
        data-test="vs-icentre"
    >
        <div class="vs-icentre__intro-content">
            <div class="vs-icentre__heading">
                <slot name="icentreHeading" />
            </div>

            <VsSvg
                class="vs-icentre__logo"
                path="iCentre"
            />

            <div
                class="vs-icentre__links"
                v-if="!!this.$slots['icentreLinks'] && !this.$slots['icentreQuote']"
            >
                <slot name="icentreLinks" />
            </div>
        </div>

        <VsImageWithCaption
            :alt-text="imgAlt"
            :image-src="imgSrc"
            variant="fullwidth"
        >
            <span slot="caption">
                <slot name="icentreImageCaption" />
            </span>

            <span slot="credit">
                <slot name="icentreImageCredit" />
            </span>
        </VsImageWithCaption>
    </div>
</template>

<script>
import VsSvg from '@components/elements/svg/Svg';
import VsImageWithCaption from '@components/patterns/image-with-caption/ImageWithCaption';

export default {
    name: 'VsIcentre',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsSvg,
        VsImageWithCaption,
    },
    props: {
        /**
        * The image to use in the component
        */
        imgSrc: {
            required: true,
            type: String,
        },
        /**
        * The image alt text to use in the component
        */
        imgAlt: {
            required: false,
            type: String,
            default: '',
        },
    },
};
</script>

<style lang="scss">
.vs-icentre {
    padding: $spacer-8;
    background: $white;

    @include media-breakpoint-up(lg) {
        padding: $spacer-11;
    }

    &__heading {
        margin-bottom: $spacer-8;
    }

    &__logo {
        height: $spacer-9;
        margin-bottom: $spacer-8;
    }

    &__links {
        margin-bottom: 3rem;
    }

    &__intro-content {
        text-align: center;
    }
}
</style>

<docs>
```jsx
    <vs-icentre
        imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
        imgAlt="This is the alt text 1"
    >
        <VsHeading level="2" slot="icentreHeading">A tip from your local experts</VsHeading>

        <p slot="icentreLinks">Test content <a href="#">link</a></p>

        <span slot="icentreImageCaption">
            A test caption
        </span>

        <span slot="icentreImageCredit">
            &copy; Some test credits
        </span>
    </vs-icentre>
```
</docs>
