<template>
    <div
        class="vs-icentre"
        data-test="vs-icentre"
    >
        <VsContainer>
            <VsRow>
                <VsCol
                    cols="10"
                    offset="1"
                    md="8"
                    offset-md="2"
                    lg="6"
                    offset-lg="3"
                    class="vs-icentre__intro-content"
                >
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
                </VsCol>

                <VsCol
                    cols="12"
                    md="10"
                    offset-md="1"
                    class="vs-icentre__main-content"
                >
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

                    <div
                        class="vs-icentre__quote-block"
                        v-if="!!this.$slots['icentreQuote']"
                    >
                        <slot name="icentreLinks" />
                        <slot name="icentreQuote" />
                    </div>
                </VsCol>
            </VsRow>
        </VsContainer>
    </div>
</template>

<script>
import VsSvg from '@components/elements/svg/Svg';
import VsImageWithCaption from '@components/patterns/image-with-caption/ImageWithCaption';
import VsContainer from '@components/elements/layout/Container';
import VsRow from '@components/elements/layout/Row';
import VsCol from '@components/elements/layout/Col';

export default {
    name: 'VsIcentre',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsSvg,
        VsImageWithCaption,
        VsContainer,
        VsRow,
        VsCol,
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

        <span slot="icentreImageCaption">
            A test caption
        </span>

        <span slot="icentreImageCredit">
            &copy; Some test credits
        </span>

        <span slot="icentreQuote">
            <p>This is some nice content down here</p>
        </span>

        <p slot="icentreLinks">Test content <a href="#">link</a></p>
    </vs-icentre>

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
