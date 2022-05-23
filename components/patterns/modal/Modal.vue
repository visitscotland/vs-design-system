<template>
    <BModal
        modal-class="vs-modal"
        data-test="vs-modal"
        :ref="modalId"
        :id="modalId"
        size="xl"
        hide-footer
        hide-header
        :static="isVideoModal"
    >
        <VsContainer>
            <VsRow>
                <VsCol
                    cols="12"
                    lg="1"
                    class="d-flex justify-content-end align-items-start order-lg-1"
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
import { BModal } from 'bootstrap-vue';

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
        /**
         * Whether or not the modal contains an embedded
         * video
         */
        isVideoModal: {
            type: Boolean,
            default: false,
        },
    },
    mounted() {
        if (this.isVideoModal) {
            this.$root.$on('bv::modal::shown', (bvEvent, modalId) => {
                this.$root.$emit('video-controls', 'play', modalId, 'modal');
            });
        }
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
            max-width: none;
            width: 100%;
        }
    }

    .modal-body{
        padding: $spacer-5 $spacer-0;

        @include media-breakpoint-up(sm) {
            padding: $spacer-5 $spacer-2;
        }
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
        font-size: $font-size-3;
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