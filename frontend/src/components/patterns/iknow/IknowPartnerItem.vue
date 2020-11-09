<template>
    <div class="vs-iknow-partner-item">
        <!-- @slot Holds the component heading -->
        <slot name="iknow-heading" />

        <VsSvg
            class="vs-iknow-partner-item__logo"
            path="iKnowScotland"
        />

        <div class="vs-iknow-partner-item__text">
            <!-- @slot Holds the component text -->
            <slot name="iknow-text" />

            <VsSvg
                class="vs-iknow-partner-item__coo"
                path="highland-cow"
            />

            <div
                v-if="!!this.$slots['iknow-cta']"
                class="vs-iknow-partner-item__cta"
            >
                <!-- @slot Optional slot for link/button CTA -->
                <slot name="iknow-cta" />
            </div>
        </div>
    </div>
</template>

<script>
import VsSvg from '@components/elements/svg/Svg';

/**
 * Reusable component to promote iKnow partners to visitors as
 * a way for them to access information from local experts.
 */

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

    &__cta {
        margin-top: $spacer-8;
    }
}
</style>

<docs>
```jsx
    <vs-iknow-partner-item>
        <VsHeading level="2" slot="iknow-heading">Help and device</VsHeading>
        <div slot="iknow-text">
            You can also get advice from our accredited
            <a href="#">iKnow partners in Edinburgh</a>,
            with excellent local expertise,
            travel advice and insider tips about must-see
            attractions and great accommodation in the local area.
        </div>
    </vs-iknow-partner-item>

    <vs-iknow-partner-item>
        <VsHeading level="2" slot="iknow-heading">Help and device</VsHeading>
        <div slot="iknow-text">
            iKnow Scotland is a network of accredited
            VisitScotland partners, with excellent local
            expertise.

            Wherever you see the iKnow logo you can
            trust that you'll receive excellent advice on
            how to get the most from your visit, with
            travel advice and insider tips about the
            must-see attractions in the local area.
        </div>

        <a href="#" slot="iknow-cta">Find our iKnow partners</a>
    </vs-iknow-partner-item>

    <vs-iknow-partner-item>
        <VsHeading level="2" slot="iknow-heading">Help and device</VsHeading>
        <div slot="iknow-text">
            iKnow Scotland is a network of accredited
            VisitScotland partners, with excellent local
            expertise.

            Wherever you see the iKnow logo you can
            trust that you'll receive excellent advice on
            how to get the most from your visit, with
            travel advice and insider tips about the
            must-see attractions in the local area.
        </div>

        <vs-button slot="iknow-cta">Find our iKnow partners</vs-button>
    </vs-iknow-partner-item>
```
</docs>
