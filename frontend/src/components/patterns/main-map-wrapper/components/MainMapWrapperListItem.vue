<template>
    <button
        class="vs-main-map-wrapper-list-item"
        data-test="vs-main-map-wrapper-list-item"
        @click="showItemDetail(itemId)"
        @keyup.enter="showItemDetail(itemId)"
    >
        <img
            src="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
            alt="alt"
            class="vs-main-map-wrapper-list-item__img"
        >
        <!-- @slot Default slot for item name text -->
        <slot />

        <VsIcon
            name="internal-link"
            variant="primary"
            size="xxs"
            class="vs-main-map-wrapper-list-item__icon"
        />
    </button>
</template>

<script>
// import VsButton from '@components/elements/button/Button';
import VsIcon from '@components/elements/icon/Icon';
/**
 * Renders a list item for the map filter tool.
 * Consists of an image and text in the form of a link.
 *
 * @displayName Main Map Wrapper
 */
export default {
    name: 'VsMainMapWrapperListItem',
    status: 'prototype',
    release: '0.0.1',
    components: {
        // VsButton,
        VsIcon,
    },
    props: {
        /**
         * Name of item
         */
        itemName: {
            type: String,
            default: '',
        },
        /**
         * Id of item
         */
        itemId: {
            type: String,
            default: '',
        },
    },
    methods: {
        /**
         * Emits an event with the ID of the chosen
         * item as the payload
         */
        showItemDetail(id) {
            this.$emit('show-item-detail', id);
        },
    },
};
</script>

<style lang="scss">
    .vs-main-map-wrapper-list-item {
        display: flex;
        position: relative;
        width: 100%;
        align-items: center;
        outline: none;
        background: none;
        border: none;
        font-size: $font-size-h4;
        text-transform: none;
        font-family: $font-family-base;
        font-weight: $font-weight-bold;
        padding: $spacer-1;
        min-height: 88px;
        transition: box-shadow $duration-slowly;
        border-bottom: 1px solid $color-gray-tint-5;

        &:hover {
            box-shadow: $shadow_card;
            text-decoration: underline;
        }

        &:focus {
            @extend %primary-button-focus;
            z-index: 2;
            outline: none;
        }

        &__img {
            width: 120px;
            margin-right: $spacer-2;
        }

        &__icon {
            margin-left: $spacer-2;
        }

        @include media-breakpoint-up(lg) {
            &__img {
                margin-right: $spacer-5;
            }
        }
    }
</style>
