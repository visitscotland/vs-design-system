<template>
    <div
        class="vs-icentre"
        data-test="vs-icentre"
    >
        <div class="vs-icentre__intro-content">
            <VsHeading
                class="vs-icentre__heading"
                level="2"
            >
                A tip from your local experts
            </VsHeading>

            <VsSvg
                class="vs-icentre__logo"
                path="iCentre"
            />

            <p class="vs-icentre__links">
                Test content <a href="#">link</a>
            </p>
        </div>

        <VsImageWithCaption
            :alt-text="`Test Alt Text 1`"
            :image-src="`https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=md`"
            variant="fullwidth"
        >
            <span slot="caption">
                Some Caption
            </span>

            <span slot="credit">
                Some Credit
            </span>
        </VsImageWithCaption>
    </div>
</template>

<script>
import VsSvg from '@components/elements/svg/Svg';
import VsHeading from '@components/elements/heading/Heading';
import VsImageWithCaption from '@components/patterns/image-with-caption/ImageWithCaption';

export default {
    name: 'VsIcentre',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsSvg,
        VsHeading,
        VsImageWithCaption,
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
    <vs-icentre>

    </vs-icentre>
```
</docs>
