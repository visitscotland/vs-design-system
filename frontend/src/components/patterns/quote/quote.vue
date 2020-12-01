<template>
    <div
        class="vs-quote"
        data-test="vs-quote"
    >
        <div class="vs-quote__speech-container">
            <div class="vs-quote__speech-mark-outer">
                <span class="vs-quote__speech-mark">“</span>
            </div>
        </div>
        <div class="vs-quote__author-container">
            <div class="vs-quote__author-image">
                <slot name="quoteImage" />
            </div>
        </div>
        <div class="vs-quote__content-container">
            <div class="vs-quote__content">
                <slot name="quoteContent" />
            </div>
            <p class="vs-quote__author-name">
                <slot name="quoteAuthorName" />
            </p>
            <p class="vs-quote__author-title">
                <slot name="quoteAuthorTitle" />
            </p>
            <slot name="quoteLink" />
        </div>
    </div>
</template>

<script>
export default {
    name: 'VsQuote',
    status: 'prototype',
    release: '0.0.1',
};
</script>

<style lang="scss">
.vs-quote {
    &__author-image {
        width: 100%;

        img {
            width: 100%;
        }
    }

    &__content {
        font-size: $font-size-lg;
    }

    &__author-name {
        font-weight: bold;
    }

    &__speech-mark-outer {
        width: 3.5rem;
        height: 2.5rem;
        position: relative;
    }

    &__speech-mark {
        font-weight: bold;
        color: $color-gray-tint-4;
        font-size: 7rem;
        height: 5rem;
        line-height: 1;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
    }

    &__speech-container {
        display: block;
        margin-bottom: $spacer-5;
        margin-right: $spacer-5;

        @include media-breakpoint-up(sm) {
            display: inline-block;
            vertical-align: top;
        }
    }

    &__author-container {
        display: block;
        width: 50%;
        margin-bottom: $spacer-5;

        @include media-breakpoint-up(sm) {
            display: inline-block;
            vertical-align: top;
        }

        @include media-breakpoint-up(lg) {
            width: 6rem;
            margin-right: $spacer-5;
        }
    }

    &__content-container {
        display: block;

        @include media-breakpoint-up(sm) {
            display: inline-block;
            vertical-align: top;
        }

        @include media-breakpoint-up(lg) {
            width: calc(100% - 13rem);
        }
    }
}
</style>

<docs>
```jsx
    <vs-quote>
        <VsImg
            src="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
            alt="This is the alt text"
            data-sizes="auto"
            class="lazyload"
            slot="quoteImage">
        </VsImg>
        <p
            slot="quoteContent"
        >
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi
            ac urna non metus tempor accumsan ut non risus. In turpis est,
            imperdiet eu sagittis ac, sodales quis nunc. Ut sagittis vulputate
            lacinia. Vivamus faucibus lorem leo, nec laoreet ligula auctor a.
            Donec id eros a ipsum facilisis lacinia nec ac nunc.
        </p>
        <span
            slot="quoteAuthorName"
        >
            Penny
        </span>
        <span
            slot="quoteAuthorTitle"
        >
            Visitor Services Advisor at Edinburgh iCentre
        </span>
        <VsButton
            href="#"
            slot="quoteLink"
        >
            Signet Library
        </VsButton>
    </vs-quote>
```
</docs>
