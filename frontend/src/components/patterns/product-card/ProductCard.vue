<template>
    <section
        class="card vs-product-card"
        v-bind="$attrs"
    >
        <VsImg
            :src="imgSrc"
            :alt="imgAlt"
            class="vs-stretched-link-card__img"
            data-test="vs-stretched-link-card__img"
        />
    </section>
</template>

<script>
import VsImg from '@components/elements/img/Img';

/**
* Generic product card for canned search
*
* @displayName Product Card
*/
export default {
    name: 'VsProductCard',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsImg,
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
            type: String,
            default: '',
        },
    },
};
</script>

<style lang="scss">
    .card.vs-product-card {
        transition: box-shadow $duration-slowly;
        border: none;
        position: relative;

        &:hover {
            box-shadow: 10px 10px 20px $color-gray-tint-4;

            .megalink-link-list__title {
                text-decoration: underline;
            }
        }
    }
</style>

<docs>
    ```
        <VsContainer>
            <VsRow>
                <VsCol cols="12" md="6" lg="4">
                    <VsProductCard
                        imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                        imgAlt="This is the alt text"
                    >
                    </VsProductCard>
                </VsCol>
            </VsRow>
        </VsContainer>
    ```
</docs>
