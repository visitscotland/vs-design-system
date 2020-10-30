<template>
    <VsStretchedLinkCard
        link="https://visitscotland.com"
        :type="linkType"
        class="megalink-link-list p-2"
        :class="{
            'megalink-link-list--featured' : featured,
            'megalink-link-list--featured-last' : lastFeatured,
        }"
    >
        <VsImg
            slot="stretchedCardImage"
            :src="imgSrc"
            :alt="imgAlt"
            class="megalink-link-list__img"
        />
        <span
            slot="stretchedCardHeader"
            class="megalink-link-list__title"
        ><!-- @slot Slot to contain heading --><slot name="vsLinkListHeading" /></span>

        <VsRichTextWrapper
            slot="stretchedCardContent"
            class="lead megalink-link-list__content"
        >
            <!-- @slot Slot to contain content -->
            <slot name="vsLinkListContent" />
        </VsRichTextWrapper>
    </VsStretchedLinkCard>
</template>

<script>
import VsStretchedLinkCard from '@components/elements/stretched-link-card/StretchedLinkCard';
import VsRichTextWrapper from '@components/elements/rich-text-wrapper/RichTextWrapper';
import VsImg from '@components/elements/img/Img';

/**
* Megalink link list cards to be used in the megalinks component
* There is a standard and featured variant.
*/

export default {
    name: 'VsMegalinkLinkList',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsStretchedLinkCard,
        VsRichTextWrapper,
        VsImg,
    },
    props: {
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
        /**
        * The type of link. This will set the icon.
        * `external, internal, download`
        */
        linkType: {
            type: String,
            required: true,
            validator: (value) => value.match(/(external|internal|download)/),
        },
    },
};
</script>

<style lang="scss">

</style>

<docs>
    ```js
    <VsMegalinks>
        <VsContainer>
            <VsRow>
                <VsCol
                    cols="12"
                    md="6"
                    xl="12"
                >
                    <vs-megalink-link-list
                        featured
                        imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                        imgAlt="This is the alt text"
                        linkType="internal"
                    >
                        <template slot="vsLinkListHeading">
                            The Edinburgh International Festival and summer festival
                        </template>
                        <template slot="vsLinkListContent">
                            <p>Right across the country, you’ll find amazing places
                            to eat and drink from local markets to renowned
                            restaurants.</p>
                        </template>
                    </vs-megalink-link-list>
                </VsCol>
                <VsCol
                    cols="12"
                    md="6"
                    xl="4"
                >
                    <vs-megalink-link-list
                        imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                        imgAlt="This is the alt text 1"
                        linkType="external"
                    >
                        <template slot="vsLinkListHeading">
                            Count 7,000 shining stars in the iconic galloway forest
                        </template>
                        <template slot="vsLinkListContent">
                            <p>Right across the country, you’ll find amazing
                            places to eat and drink from local markets to renowned
                            restaurants. Here are some recomm…</p>
                        </template>
                    </vs-megalink-link-list>
                </VsCol>
                <VsCol
                    cols="12"
                    md="6"
                    xl="4"
                >
                    <vs-megalink-link-list
                        imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                        imgAlt="This is the alt text 2"
                        linkType="external"
                    >
                        <template slot="vsLinkListHeading">
                            Count 7,000 shining stars in the iconic galloway forest
                        </template>
                        <template slot="vsLinkListContent">
                            <p>Right across the country, you’ll find amazing places
                            to eat and drink
                            from local markets to renowned restaurants.
                            Here are some recomm…</p>
                            <p>Right across the country, you’ll find amazing places
                            to eat and drink
                            from local markets to renowned restaurants.
                            Here are some recomm…</p>
                            <p>Right across the country, you’ll find amazing places
                            to eat and drink
                            from local markets to renowned restaurants.
                            Here are some recomm…</p>
                        </template>
                    </vs-megalink-link-list>
                </VsCol>
                <VsCol
                    cols="12"
                    md="6"
                    xl="4"
                >
                    <vs-megalink-link-list
                        imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                        linkType="download"
                    >
                        <template slot="vsLinkListHeading">
                            Soar through the air on a boat of Falkirk Wheel (PDF 3MB)
                        </template>
                        <template slot="vsLinkListContent">
                            <p>Right across the country, you’ll find amazing
                            places to eat and drink from local markets to renowned
                            restaurants. Here are some recomm…</p>
                        </template>
                    </vs-megalink-link-list>
                </VsCol>
            </VsRow>
        </VsContainer>
    </VsMegalinks>
    ```
</docs>
