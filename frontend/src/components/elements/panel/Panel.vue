<template>
    <BCard
        class="vs-panel"
        :class="[this.$slots['vs-panel-title'] ? '' : 'pt-10']"
    >
        <BCardTitle
            v-if="this.$slots['vs-panel-title']"
            class="vs-panel-title mb-8"
        >
            <!-- @slot Contains an optional title for the panel  -->
            <slot name="vs-panel-title" />
        </BCardTitle>

        <BCardText class="vs-panel-text">
            <!-- @slot default slot containing main body text -->
            <slot />
        </BCardText>
    </BCard>
</template>

<script>
import { BCard, BCardTitle, BCardText } from "bootstrap-vue"

/**
 * The panel is used for simple content and contains slots for title and content.
 */
export default {
    name: "VsPanel",
    status: "prototype",
    release: "0.0.1",
    components: {
        BCard,
        BCardTitle, 
        BCardText
    },
}
</script>

<style lang="scss" scoped>
.vs-panel {
    background-color: $color-theme-light;
    padding: $spacer-9 $spacer-4 $spacer-9;

    @include media-breakpoint-up(md) {
        padding: $spacer-9 $spacer-8 $spacer-10;
    }

    @include media-breakpoint-up(lg) {
        padding: $spacer-9 $spacer-11 $spacer-10;
    }

    @include media-breakpoint-up(xxl) {
        padding: $spacer-9 $spacer-12 $spacer-10;
    }

    .vs-panel-title {
        display: block;
        text-align: center;
    }

    .vs-panel-text {
        @include media-breakpoint-down(xs) {
            .vs-rich-text-wrapper.lead {
                font-size: $font-size-base;
                line-height: $line-height-m;
            }
        }
    }
}
</style>

<docs>
```jsx
    <vs-panel>
        <vs-heading thin level="4" slot="vs-panel-title">A Title Would Go Here</vs-heading>

        <vs-rich-text-wrapper variant="lead">
             <!-- eslint-disable-next-line max-len -->
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras maximus est congue nunc fermentum, eu hendrerit odio fringilla. Donec porta eleifend ipsum Praesent in magna fringilla.</p>

             <!-- eslint-disable-next-line max-len -->
            <p>Morbi faucibus risus in varius sollicitudin. Aenean eget leo ullamcorper, scelerisque urna eu, imperdiet nibh. Duis sit amet nibh tortor. Donec ac ante eros. Praesent in magna fringilla, consectetur eros id, fermentum nibh. Duis luctus ante sapien, scelerisque fermentum massa tincidunt eu. Nullam sed arcu a justo auctor consectetur.</p>
        </vs-rich-text-wrapper>
    </vs-panel>
```
</docs>
