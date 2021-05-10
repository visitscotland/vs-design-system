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

                        <VsRow>
                            <VsCol
                                cols="12"
                                lg="10"
                                offset="0"
                                offset-lg="1"
                            >
                                <div class="vs-article__content">
                                    <VsHeading
                                        level="2"
                                        class="text-center mb-9"
                                    >
                                        <slot name="vsArticleTitle" />
                                    </VsHeading>

                                    <VsRichTextWrapper
                                        variant="lead"
                                        class="text-center mb-10"
                                    >
                                        <slot name="vsArticleIntro" />
                                    </VsRichTextWrapper>

                                    <slot />
                                </div>
                            </VsCol>
                        </VsRow>
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

        figure{
            margin-bottom: 0;
        }
    }

    &__content{
        margin: $spacer-11 0;
    }
}
</style>

<docs>
  ```jsx

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

    <template slot="vsArticleTitle">
        The mountain with its head in the clouds means that its raining
    </template>

    <template slot="vsArticleIntro">
        There are two main walking routes up Ben Nevis. The Mountain Track
        (sometimes called the Tourist Track or the Pony Track) is used by most walkers,
        whilst the Carn Mor Dearg Arête route presents a more challenging climb
        for more experienced hikers.
    </template>

</VsArticle>

  ```
</docs>
