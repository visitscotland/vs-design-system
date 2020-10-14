<template>
    <section class="vs-megalinks">
        <VsContainer>
            <VsRow>
                <VsCol
                    cols="12"
                    md="8"
                    offset-md="2"
                >
                    <div
                        class="vs-megalinks__intro"
                        v-if="title"
                    >
                        <VsHeading
                            level="2"
                            class="vs-megalinks__heading"
                        >
                            {{ title }}
                        </VsHeading>
                        <VsRichTextWrapper
                            variant="lead"
                            class="vs-megalinks__intro-content"
                        >
                            <!-- @slot Slot to contain optional intro content -->
                            <slot name="vs-multilinks-intro" />
                        </VsRichTextWrapper>
                    </div>
                </VsCol>
            </VsRow>
            <!-- @slot Default slot to contain link blocks -->
            <slot />
        </VsContainer>
    </section>
</template>

<script>
import VsHeading from '@components/elements/heading/Heading';
import VsRichTextWrapper from '@components/elements/rich-text-wrapper/RichTextWrapper';
import {
    VsContainer,
    VsRow,
    VsCol,
} from '@components/elements/layout';

/**
 * Megalinks wrapper used with Megalinks components.
 */
export default {
    name: 'VsMegalinks',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsContainer,
        VsRow,
        VsCol,
        VsHeading,
        VsRichTextWrapper,
    },
    props: {
        /**
        * The title is optional. If it isn't supplied, the
        * intro content shouldn't render.
        */
        title: {
            type: String,
            default: null,
            required: false,
        },
    },
};
</script>

<style lang="scss">
    .vs-megalinks {
        .vs-megalinks__intro {
            text-align: center;
            padding-bottom: $spacer-9;
        }

        .vs-megalinks__heading {
            margin-bottom: 0 !important;
        }

        .vs-megalinks__intro-content {
            margin-top: $spacer-6;
        }
    }
</style>

<docs>
    ```js
    <vs-megalinks
        title="Find your experiences in the highest village of Scotland"
    >
        <template slot="vs-multilinks-intro">
            Lorem ipsum dolor sit amet, consectetur adipisicing elit.
            Esse dolores ab veritatis quibusdam voluptas dicta,
            ullam distinctio placeat aliquam facere libero dolore veniam!
            Vero pariatur molestiae sunt vitae labore esse?
        </template>

        THIS IS WHERE THE LINKS WOULD GO
    </vs-megalinks>
    ```
</docs>
