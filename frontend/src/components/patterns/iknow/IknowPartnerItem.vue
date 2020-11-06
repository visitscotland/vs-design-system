<template>
    <div class="vs-iknow-partner-item">
        <slot name="iknow-heading" />
        <VsSvg
            class="vs-iknow-partner-item__logo"
            path="iKnowScotland"
        />
        <div class="vs-iknow-partner-item__text">
            <slot name="iknow-text" />
            <VsSvg
                class="vs-iknow-partner-item__coo"
                path="highland-cow"
            />
        </div>
    </div>
</template>

<script>
import VsSvg from '@components/elements/svg/Svg';

export default {
    name: 'VsIknowPartnerItem',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsSvg,
    },
};
</script>

<style lang="scss">
.vs-iknow-partner-item {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    padding: $spacer-8;

    @include media-breakpoint-up(xl) {
        padding: $spacer-11;
    }

    &__logo {
        display: inline-block;
        max-height: 30px;
        max-width: 105px;
        margin-bottom: $spacer-8;

        @include media-breakpoint-up(xl) {
            max-height: 40px;
            max-width: 138px;
        }
    }

    &__text {
        font-size: $display3-size;
        margin-bottom: 0;

        @include media-breakpoint-up(xl) {
            text-align: center;
            max-width: 620px;
            position: relative;
        }
    }

    &__coo {
        display: block;
        width: 100%;
        height: auto;
        max-width: 115px;
        max-height: 100px;
        margin: $spacer-8 auto 0;

        @include media-breakpoint-up(xl) {
            max-height: 210px;
            max-width: 185px;
            position: absolute;
            bottom: -5.5rem;
            left: -14rem;
        }
    }
}
</style>

<docs>
```jsx
    <vs-iknow-partner-item>
        <VsHeading level="2" slot="iknow-heading">Help and help</VsHeading>
        <p slot="iknow-text">
            You can also get advice from our accredited
            <a href="#">iKnow partners in Edinburgh</a>,
            with excellent local expertise,
            travel advice and insider tips about must-see
            attractions and great accommodation in the local area.
        </p>
    </vs-iknow-partner-item>
```
</docs>
