<template>
    <VsStretchedLinkCard
        link="https://visitscotland.com"
        type="external"
        img-src="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
        img-alt="This is the alt text"
        class="vs-mega-nav-featured-item"
    >
        <template slot="stretchedCardHeader">
            A Title Would Go Here
        </template>

        <template slot="stretchedCardContent">
            <p>The content for the card goes here</p>

            <p>A second line of content</p>
        </template>
    </VsStretchedLinkCard>
</template>

<script>
import VsStretchedLinkCard from '@components/patterns/stretched-link-card/StretchedLinkCard';

/**
 *  This component is used to show a featured item in the meganav
 *
 * @displayName MegaNav Featured Item
 */

export default {
    name: 'VsMegaNavFeaturedItem',
    status: 'prototype',
    release: '0.1.0',
    components: {
        VsStretchedLinkCard,
    },
};
</script>

<style lang="scss">
    .card.vs-mega-nav-featured-item {
        .card-body {
            position: absolute;
            width: 80%;
            bottom: 0;
            left: 0;
            background: $color-white;
        }

        .card-title {
            font-size: $small-font-size;
        }
    }
</style>

<docs>
    ```jsx
        <VsContainer>
            <VsRow>
                <VsCol md="4">
                    <VsMegaNavFeaturedItem />
                </VsCol>
            </VsRow>
        </VsContainer>
    ```
</docs>
