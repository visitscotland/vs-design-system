<template>
    <div
        class="vs-iknow-community"
        data-test="vs-iknow-community"
    >
        <VsContainer>
            <VsRow>
                <VsCol
                    cols="10"
                    offset="1"
                    md="8"
                    offset-md="2"
                    lg="6"
                    offset-lg="3"
                    class="vs-iknow-community__intro-content"
                >
                    <VsSvg
                        class="vs-iknow-community__logo"
                        path="iKnowScotland"
                    />
                </VsCol>

            </VsRow>
            <VsRow>
                <VsCol
                    cols="12"
                    md="5"
                    offset-md="1"
                    lg="6"
                    offset-lg="0"
                    xl="5"
                    offset-xl="1"
                    class="vs-iknow-community__main-content py-10"
                >
                    <!-- @slot Holds explanatory text (html expected) -->
                    <slot name="iknowCommunityText" />

                    <div
                        class="vs-iknow-community__link"
                        v-if="!!this.$slots['iknowCommunityLink']"
                    >
                        <!-- @slot Holds a link to the main community board (anchor expected) -->
                        <slot name="iknowCommunityLink" />
                    </div>

                    <div
                        class="vs-iknow-community__tags"
                        v-if="!!this.$slots['iknowCommunityTags']"
                    >
                        <!-- @slot Holds popular tags (html expected) -->
                        <slot name="iknowCommunityTags" />
                    </div>
                </VsCol>
            </VsRow>
        </VsContainer>
    </div>
</template>

<script>
import VsSvg from '@components/elements/svg/Svg';
import VsContainer from '@components/elements/layout/Container';
import VsCol from '@components/elements/layout/Col';
import VsRow from '@components/elements/layout/Row';

/**
 * Filler
 *
 * @displayName iKnow Community
 */
export default {
    name: 'VsIknowCommunity',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsSvg,
        VsContainer,
        VsRow,
        VsCol,
    },
};
</script>

<style lang="scss">
.vs-iknow-community {
    &__intro-content {
        text-align: center;
    }

    &__logo {
        height: $spacer-10;
    }

    &__main-content {
        text-align: left;
    }

    &__link {
        padding-top: $spacer-4;
    }

    &__tags {
        padding-top: $spacer-4;
    }
}
</style>

<docs>
```jsx
    <VsIknowCommunity>
        <div slot="iknowCommunityText">
            <p>If you're looking for the BEST tips, secrets and hidden
                gems, then look no further. Talk to people in the iKnow
                and share your own stories in our iKnow Community.</p>
        </div>

        <template slot="iknowCommunityLink"><a href="#">Join the conversation</a></template>

        <template slot="iknowCommunityTags">
            <VsButton
                href="#"
                size="sm"
                variant="outline-primary"
                class="mr-2"
            >
                Activities
            </VsButton>
        </template>
    </VsIknowCommunity>
```
</docs>
