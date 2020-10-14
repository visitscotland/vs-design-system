<template>
    <VsStretchedLinkCard
        link="https://visitscotland.com"
        type="download"
        class="megalink-multi-image p-2"
    >
        <VsImg
            slot="stretched-card-image"
            src="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
            alt="This is a test image"
            class="megalink-multi-image__img"
        />
        <span
            slot="stretched-card-header"
            class="megalink-multi-image__title"
        >
            This is the header
        </span>
        <VsRichTextWrapper
            slot="stretched-card-content"
            class="mt-4"
        >
            The content for the card goes here
        </VsRichTextWrapper>
    </VsStretchedLinkCard>
</template>

<script>
import VsStretchedLinkCard from '@components/elements/stretched-link-card/StretchedLinkCard';

export default {
    name: 'VsMegalinkMultiImage',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsStretchedLinkCard,
    },
};
</script>

<style lang="scss">
    .megalink-multi-image.card {
        border: none;

        .stretched-link {
            color: $color-base-text;
            text-decoration: none;

            &:hover {
                text-decoration: underline;
            }

            &:focus {
                outline: 1px solid $color-theme-primary;
            }
        }

        .card-body {
            padding: $spacer-4 0 $spacer-5;
        }

        &:hover {
            box-shadow: 10px 10px 20px $color-gray-tint-4;
        }
    };
</style>

<docs>
    ```js
    <VsContainer>
        <VsRow>
            <VsCol
                cols="12"
                md="6"
                lg="4"
            >
                <vs-megalink-multi-image></vs-megalink-multi-image>
            </VsCol>
            <VsCol
                cols="12"
                md="6"
                lg="4"
            >
                <vs-megalink-multi-image></vs-megalink-multi-image>
            </VsCol>
            <VsCol
                cols="12"
                md="6"
                lg="4"
            >
                <vs-megalink-multi-image></vs-megalink-multi-image>
            </VsCol>
        </VsRow>
    </VsContainer>
    ```
</docs>
