<template>
    <section class="vs-megalinks">
        <VsContainer>
            <VsRow>
                <VsCol
                    cols="12"
                    md="8"
                    offset-md="2"
                >
                    <div class="vs-megalinks__intro">
                        <VsHeading
                            level="2"
                            class="vs-megalinks__heading"
                        >
                            <!-- @slot Slot to contain optional title -->
                            <slot name="vs-multilinks-title" />
                        </VsHeading>

                        <!-- @slot Slot to contain optional intro content -->
                        <slot name="vs-multilinks-intro" />
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
    <vs-megalinks>
        <template slot="vs-multilinks-title">
            Find your experiences in the highest village of Scotland
        </template>
        <VsRichTextWrapper
            variant="lead"
            slot="vs-multilinks-intro"
            class="mt-6"
        >

            Lorem ipsum dolor sit amet consectetur adipisicing elit.
            Quae, in quidem doloribus iure, fuga laboriosam libero voluptatem
            earum, rerum eveniet cumque molestiae dolores. Fugiat eligendi
            inventore quidem, nesciunt explicabo repudiandae.
        </VsRichTextWrapper>

        THIS IS WHERE THE LINKS WOULD GO
    </vs-megalinks>
    ```
</docs>
