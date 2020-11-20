<template>
    <div class="card">
        <!-- @slot Contains the image for the card  -->
        <slot name="stretchedCardImage" />

        <div class="card-body">
            <VsHeading
                level="3"
                class="card-title"
            >
                <VsLink
                    :href="link"
                    :type="type"
                    class="stretched-link"
                    data-test="stretched-link"
                >
                    <!-- @slot Contains header content for the card  -->
                    <slot name="stretchedCardHeader" />
                </VsLink>
            </VsHeading>
            <!-- @slot Contains body content for the card  -->
            <slot name="stretchedCardContent" />
        </div>
    </div>
</template>

<script>
import VsHeading from '@components/elements/heading/Heading';
import VsLink from '@components/elements/link/Link';
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
        VsLink,
    },
    props: {
        /**
        * The link that the component will use
        */
        link: {
            type: String,
            required: true,
        },
        /**
        * The type of link. This will set the icon.
        * `external, internal, download`
        */
        type: {
            type: String,
            required: true,
            validator: (value) => value.match(/(external|internal|download)/),
        },
    },
};
</script>

<docs>
  ```
    <vs-stretched-link-card link="https://visitscotland.com" type="external">
        <vs-img
            slot="stretchedCardImage"
            src="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
            alt="Claire standing stones"
            fluid
        />

        <template slot="stretchedCardHeader">
            A Title Would Go Here
        </template>

        <vs-rich-text-wrapper slot="stretchedCardContent">
            The content for the card goes here
        </vs-rich-text-wrapper>
    </vs-stretched-link-card>
  ```
</docs>
