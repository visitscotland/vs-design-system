<template>
    <div
        class="vs-article-sidebar"
        data-test="vs-article-sidebar"
    >
        <slot name="vsArticleSidebarImg" />

        <div class="mt-8">
            <slot name="vsArticleSidebarQuote" />
        </div>
    </div>
</template>

<script>
/**
 * The Article component is a wrapper for article content, generally wrapped in content sections
 *
 * @displayName Article Sidebar
 */
export default {
    name: 'VsArticleSidebar',
    status: 'prototype',
    release: '0.0.1',
    components: {
    },
};
</script>

<style lang="scss">
.vs-article-sidebar {
    background: $color-white;
}
</style>

<docs>
  ```jsx

<VsArticleSidebar>
<template slot="vsArticleSidebarImg">
    <VsImageWithCaption
        altText="An image"
        image-src="fixtures\image-with-caption\images\city-country-breaks.jpg"
    >
        <span slot="caption">
            A nice image
        </span>

        <span slot="credit">
            &copy; Gemma Stephen
        </span>
    </VsImageWithCaption>
</template>
<template slot="vsArticleSidebarQuote">
    <VsQuote>
        <p slot="quoteContent">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi
            ac urna non metus tempor accumsan ut non risus. In turpis est,
            imperdiet eu sagittis ac, sodales quis nunc. Ut sagittis vulputate
            lacinia. Vivamus faucibus lorem leo, nec laoreet ligula auctor a.
            Donec id eros a ipsum facilisis lacinia nec ac nunc.
        </p>
        <span slot="quoteAuthorName">Penny</span>
        <span slot="quoteAuthorTitle">
            Visitor Services Advisor at Edinburgh iCentre
        </span>
    </VsQuote>
</template>
</VsArticleSidebar>
  ```
</docs>
