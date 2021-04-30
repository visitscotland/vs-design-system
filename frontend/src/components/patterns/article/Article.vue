<template>
    <article
        class="vs-article"
        data-test="vs-article"
        v-bind="$attrs"
    >
        <VsContainer>
            <VsRow>
                <VsCol
                    cols="12"
                    lg="10"
                    offset="0"
                    offset-lg="1"
                >
                    <div class="vs-article__wrapper">
                        <slot name="vsArticleImg" />
                        <slot />
                    </div>
                </VsCol>
            </VsRow>
        </VsContainer>
    </article>
</template>

<script>
import {
    VsCol, VsRow, VsContainer,
} from '@components/elements/layout';

/**
 * The Article component is a wrapper for article content, generally wrapped in content sections
 *
 * @displayName Article
 */
export default {
    name: 'VsArticle',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsCol,
        VsRow,
        VsContainer,
    },
    props: {
    },
};
</script>

<style lang="scss">
.vs-article {
    &__wrapper{
        border: 1px solid $color-gray-tint-5;
    }
}
</style>

<docs>
  ```jsx

<VsPageIntro background="light" :heroIntro="false" :isItinerary="false">
    <template slot="vsIntroBreadcrumb">
        <VsBreadcrumb>
            <VsBreadcrumbItem
            v-for="(item, index) in breadcrumb.breadcrumb"
            :key="index"
            :href="item.href"
            :active="item.active"
            :text="item.name"
            >
            </VsBreadcrumbItem>
        </VsBreadcrumb>
    </template>
    <template slot="vsIntroHeading">
        Ben Nevis Scotland's Tallest Peak
    </template>

    <template slot="vsIntroContent">
        <div v-html="itineraries.sampleItinerary.introduction" />
    </template>
</VsPageIntro>

<VsArticle>
    <template slot="vsArticleImg">
        <VsImageWithCaption
            v-for="(item, index) in imageWithCaption.imageExamples.fullwidth"
            :altText="item.altText"
            :closedDefaultCaption="item.isSmall"
            :image-src="item.imageSrc"
            :key="`fullwidth1-${index}`"
            variant="fullwidth"
        >
            <VsImg
                class="lazyload"
                :src="item.imageSrc"
                :data-srcset="item.imageSrc"
                :alt="item.altText"
                data-sizes="auto">
            </VsImg>

            <span slot="caption" v-if="item.caption">
                {{ item.caption }}
            </span>

            <span slot="credit" v-if="item.credit">
                &copy; {{ item.credit }}
            </span>
        </VsImageWithCaption>
    </template>
    <p>
        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris orci eros,
        malesuada vitae tempor in, venenatis. Aliquam erat volutpat. Morbi placerat
        fringilla nibh sed scelerisque. Etiam lacinia dolor porta sollicitudin.
        Duis nec nunc suscipit, sagittis turpis et, bibendum arcu.
    </p>

    <p>
        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris orci eros,
        malesuada vitae tempor in, venenatis. Aliquam erat volutpat. Morbi placerat
        fringilla nibh sed scelerisque. Etiam lacinia dolor porta sollicitudin.
        Duis nec nunc suscipit, sagittis turpis et, bibendum arcu.
    </p>

    <p>
        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris orci eros,
        malesuada vitae tempor in, venenatis. Aliquam erat volutpat. Morbi placerat
        fringilla nibh sed scelerisque. Etiam lacinia dolor porta sollicitudin.
        Duis nec nunc suscipit, sagittis turpis et, bibendum arcu.
    </p>
</VsArticle>

  ```
</docs>
