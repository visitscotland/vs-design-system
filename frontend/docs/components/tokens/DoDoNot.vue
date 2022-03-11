<template>
    <div
        class="vs-do-do-not"
    >
        <VsContainer>
            <VsRow>
                <VsCol
                    cols="12"
                    md="6"
                >
                    <b-card
                        header-bg-variant="success"
                        header-text-variant="white"
                        header="Do"
                    >
                        <!-- @slot Holds the positive suggestion (html expected) -->
                        <slot name="thingToDo" />
                    </b-card>
                </VsCol>
                <VsCol
                    cols="12"
                    md="6"
                >
                    <b-card
                        header-bg-variant="danger"
                        header-text-variant="white"
                        header="Don't"
                    >
                        <!-- @slot Holds the negative suggestion (html expected) -->
                        <slot name="thingToNotDo" />
                    </b-card>
                </VsCol>
            </VsRow>
        </VsContainer>
    </div>
</template>

<script>
import VsContainer from '@components/elements/layout/Container';
import VsRow from '@components/elements/layout/Row';
import VsCol from '@components/elements/layout/Col';
import { BCard } from 'bootstrap-vue';

/**
 *
 */
export default {
    name: "DoDoNot",
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsContainer,
        VsRow,
        VsCol,
        BCard
    },
}
</script>

<style lang="scss" scoped>
    .vs-do-do-not {
        text-align: left;

        .card-body img {
            max-width: 100%;
        }
    }
</style>

<docs>
  ```jsx
    <DoDoNot>
        <p slot="thingToDo">
            This is a good suggestion, we'd recommend you go ahead with it
        </p>
        <p slot="thingToNotDo">
            This is absolutely not acceptable, you should not do this
        </p>
    </DoDoNot>
  ```
</docs>
