<template>
    <div
        class="vs-article-section"
        data-test="vs-article-section"
    >
        <VsContainer>
            <VsRow>
                <VsCol cols="12">
                    <VsRichTextWrapper>
                        <slot />
                    </VsRichTextWrapper>
                </VsCol>
            </VsRow>
        </VsContainer>
    </div>
</template>

<script>
import {
    VsCol, VsRow, VsContainer,
} from '@components/elements/layout';

/**
 * The Article component is a wrapper for article content, generally wrapped in content sections
 *
 * @displayName Article Section
 */
export default {
    name: 'VsArticleSection',
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
.vs-article-section {

}
</style>

<docs>
  ```jsx

<VsPageIntro background="dark" :heroIntro="true" :isItinerary="false" class="mb-8">
      <VsHero
        slot="vsIntroHero"
        :altText="itineraries.sampleItinerary.image.altText"
        :credit="itineraries.sampleItinerary.image.credit"
        :caption="itineraries.sampleItinerary.image.caption"
        :image-src="itineraries.sampleItinerary.image.imageSrc"
        :latitude="itineraries.sampleItinerary.image.latitude"
        :longitude="itineraries.sampleItinerary.image.longitude"
      >
      <img
        class="lazyload"
        :src="itineraries.sampleItinerary.image.imageSrc"
        srcset="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="
        :data-srcset="itineraries.sampleItinerary.image.imageSrc"
        :alt="itineraries.sampleItinerary.image.altText"
        data-sizes="auto"
        />
      </VsHero>
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
            {{itineraries.sampleItinerary.h1Heading}}
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

    <template slot="vsArticleTitle">
        The mountain with its head in the clouds means that its raining
    </template>

    <template slot="vsArticleIntro">
        There are two main walking routes up Ben Nevis. The Mountain Track
        (sometimes called the Tourist Track or the Pony Track) is used by most walkers,
        whilst the Carn Mor Dearg Arête route presents a more challenging climb
        for more experienced hikers.
    </template>

    <VsArticleSection>
        <VsHeading level="6">
            How do I climb Ben Nevis safely?
        </VsHeading>

        <p>
            The difficulty of this hike is often under-estimated so always be
            prepared and take the walk at your own pace. If you are not confident in your
            own sense of direction, there are local guided walking tours available.
            Remember this is the UK's highest mountain!
        </p>
        <p>
            Make sure someone knows where you're headed and ensure that you have
            plenty of time to get back well before nightfall.
        </p>
        <p>
            Any ascent in snow requires a high degree of fitness, winter equipment and the
            skills to use them and mountaineering and navigation experience. Bear in mind
            snow can cover parts of the 'tourist' path into the summer months. If you're at
            all unsure, local mountain guides can advise and guide you to the summit and back.
        </p>
        <p>
            Make sure you fill in a mountain safety route card and leave it with someone you trust.
            For more information on keeping safe on Ben Nevis, see these tips from
            Mountaineering Scotland and Walk Highlands.
        </p>

        <VsHeading level="6">
            Is Ben Nevis suitable for children to climb?
        </VsHeading>
        <p>
            Absolutely, as long as they are prepared for all weather conditions and keen for
            a challenge. The Carn Mor Dearg Arete route is generally not advised for children.
        </p>
    </VsArticleSection>

</VsArticle>

  ```
</docs>
