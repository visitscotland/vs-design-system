<template>
    <BModal
        modal-class="vs-modal"
        data-test="vs-modal"
        :ref="modalId"
        :id="modalId"
        size="xl"
        hide-footer
        hide-header
    >
        <VsContainer>
            <VsRow>
                <VsCol
                    cols="12"
                    lg="1"
                    class="d-flex justify-content-end justify-content-lg-center
                    order-lg-1 align-items-start"
                >
                    <VsButton
                        class="vs-modal__close-btn mb-5 mb-lg-0"
                        data-test="vs-modal__close-btn"
                        variant="transparent"
                        :uppercase="false"
                        @click.native="hideModal"
                    >
                        <VsIcon
                            name="close-circle"
                            variant="light"
                            size="md"
                        />

                        {{ closeBtnText }}
                    </VsButton>
                </VsCol>
                <VsCol
                    cols="12"
                    lg="11"
                >
                    <slot />
                </VsCol>
            </VsRow>
        </VsContainer>
    </BModal>
</template>

<script>
import {
    VsCol, VsRow, VsContainer,
} from '@components/elements/layout';
import { BModal } from 'bootstrap-vue';
import VsButton from '@components/elements/button';
import VsIcon from '@components/elements/icon';

/**
 * The modal is used as a dialog prompt for users
 *
 * @displayName Modal
 */
export default {
    name: 'VsModal',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsCol,
        VsRow,
        VsContainer,
        VsButton,
        VsIcon,
        BModal,
    },
    props: {
        /**
         * The ID for the modal, each modal must have a
         * unique ID.
         */
        modalId: {
            type: String,
            required: true,
        },
        /**
         * Text to display on close button
         */
        closeBtnText: {
            type: String,
            required: true,
        },
    },
    methods: {
        /**
         * Closes modal window
         */
        hideModal() {
            this.$refs[this.modalId].hide();
        },
    },
};
</script>

<style lang="scss">
.vs-modal{
    .modal-dialog{
        margin: $spacer-8 auto;

        @include media-breakpoint-up(sm) {
            max-width: 528px;
            margin: $spacer-7 auto;
        }
    }

    .modal-body{
        padding: $spacer-4 $spacer-1;
    }

    @include media-breakpoint-up(lg) {
        .modal-lg, .modal-xl {
            max-width: 924px;
        }
    }

    @include media-breakpoint-up(xl) {
        .modal-xl {
            max-width: 1140px;
        }
    }

    .modal-content{
        background-color: $color-black;

        p{
            color: $color-white;
        }
    }

    &__close-btn.vs-button.btn{
        padding: 0 $spacer-1;
        letter-spacing: initial;
        text-decoration: underline;
        font-weight: $font-weight-normal;
        font-size: $small-font-size;
        line-height: $line_height_l;
        color: $color-white;

        svg {
            display: block;
            margin: 0 auto;
        }

        &:hover{
            color: $color-yellow;
        }
    }
}
</style>

<docs>
```jsx
    <VsButton
        id="toggle-btn"
        class="mb-4"
        ref="btnShow"
        @click.native="$root.$emit('bv::show::modal', 'videoModal', '#btnShow')"
    >
        Play Video
    </VsButton>

    <VsModal
        modalId="videoModal"
        closeBtnText="Close"
    >
        <VsRow>
            <VsCol cols="12">
                <VsVideo
                    video-id="c05sg3G4oA4"
                    class="mb-8"
                />
            </VsCol>

            <VsCol
                cols="12"
                sm="10"
                offset-sm="1"
            >
                <VsRichTextWrapper>
                    <p>
                        Discover our incredible castles from a new perspective.
                        This incredible drone footage shows castles from Dumfries &
                        Galloway to Wick on the north coastline.
                    </p>
                </VsRichTextWrapper>
            </VsCol>
        </VsRow>
    </VsModal>
```
</docs>
