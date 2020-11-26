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
                        data-test="vs-icentre__standalone-links"
                        v-if="!!this.$slots['icentreLinks'] && !this.$slots['icentreQuote']"
                    >
                        <slot name="icentreLinks" />
                    </div>
                </VsCol>

                <VsCol
                    cols="12"
                    md="10"
                    offset-md="1"
                >
                    <slot name="icentreImageWithCaption" />
                </VsCol>

                <VsCol
                    cols="12"
                    md="10"
                    offset-md="1"
                    lg="7"
                    offset-lg="5"
                    class="vs-icentre__quote-block-container"
                    v-if="!!this.$slots['icentreQuote']"
                >
                    <div class="vs-icentre__quote-block">
                        <slot name="icentreQuote" />

                        <div
                            class="vs-icentre__links-contained"
                            data-test="vs-icentre__embedded-links"
                        >
                            <slot name="icentreLinks" />
                        </div>
                    </div>
                </VsCol>
            </VsRow>
        </VsContainer>
    </div>
</template>

<script>
import VsSvg from '@components/elements/svg/Svg';
import VsContainer from '@components/elements/layout/Container';
import VsRow from '@components/elements/layout/Row';
import VsCol from '@components/elements/layout/Col';

export default {
    name: 'VsIcentre',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsSvg,
        VsContainer,
        VsRow,
        VsCol,
    },
};
</script>

<style lang="scss">
.vs-icentre {
    padding: $spacer-8 $spacer-0;

    @include media-breakpoint-up(md) {
        padding: $spacer-8;
    }

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
        margin-bottom: $spacer-9;
    }

    &__intro-content {
        text-align: center;
    }

    &__main-image {
        margin-bottom: 0;
    }

    &__quote-block-container {
        z-index: 2;

        @include media-breakpoint-up(lg) {
            transform: translateY(-50%);
        }
    }

    &__quote-block {
        background: $white;
        padding: $spacer-8 $spacer-8;

        @include media-breakpoint-up(md) {
            padding: $spacer-9 $spacer-10;
        }
    }

    &__links-contained {
        margin-bottom: $spacer-0;
    }
}
</style>

<docs>
```jsx
    <vs-icentre>
        <VsHeading level="2" slot="icentreHeading">A tip from your local experts</VsHeading>

        <VsImageWithCaption
            imageSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
            altText="This is the alt text"
            slot="icentreImageWithCaption">

            <span slot="caption">
                A test caption
            </span>

            <span slot="credit">
                &copy; Some test credits
            </span>
        </VsImageWithCaption>

        <span slot="icentreQuote">
            <p>This is an example quote, it doesn't look like a quote but it fills the space.</p>
            <p>When the quote component is done this will be one of those instead</p>
        </span>

        <p slot="icentreLinks">Test content <a href="#">link</a></p>
    </vs-icentre>

    <vs-icentre>
        <VsHeading level="2" slot="icentreHeading">A tip from your local experts</VsHeading>

        <VsImageWithCaption
            imageSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
            altText="This is the alt text"
            slot="icentreImageWithCaption">

            <span slot="caption">
                A test caption
            </span>

            <span slot="credit">
                &copy; Some test credits
            </span>
        </VsImageWithCaption>

        <p slot="icentreLinks">Test content <a href="#">link</a></p>
    </vs-icentre>
```
</docs>
