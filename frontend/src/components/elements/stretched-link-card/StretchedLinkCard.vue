<template>
    <div class="card">
        <!-- @slot Contains the image for the card  -->
        <slot name="stretched-card-image" />

        <div class="card-body">
            <VsHeading
                level="4"
                class="card-title"
            >
                <a
                    :href="link"
                    class="stretched-link"
                >
                    <!-- @slot Contains header content for the card  -->
                    <slot name="stretched-card-header" />
                </a>
            </VsHeading>
            <!-- @slot Contains body content for the card  -->
            <slot name="stretched-card-content" />
        </div>
    </div>
</template>

<script>
import VsHeading from '@components/elements/heading/Heading';
/**
 * The Stretched Link Card is a block that stretches its nested link across its whole area
 * meaning that the whole block is clickable
 */
export default {
    name: 'VsStretchedLinkCard',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsHeading,
    },
    props: {
        /**
        * The link that the component will use
        */
        link: {
            type: String,
            required: true,
        },
    },
};
</script>

<docs>
  ```
    <vs-stretched-link-card>
        <vs-img
            slot="stretched-card-image"
            src="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
            alt="Claire standing stones"
            fluid
        />

        <template slot="stretched-card-header">
            A Title Would Go Here
        </template>

        <vs-rich-text-wrapper slot="stretched-card-content">
            The content for the card goes here
        </vs-rich-text-wrapper>
    </vs-stretched-link-card>
  ```
</docs>
