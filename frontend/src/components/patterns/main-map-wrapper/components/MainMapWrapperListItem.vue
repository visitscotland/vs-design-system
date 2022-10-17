<template>
    <button
        class="vs-main-map-wrapper-list-item"
        data-test="vs-main-map-wrapper-list-item"
        @click="showItemDetail(itemData.id)"
        @keyup.enter="showItemDetail(itemData.id)"
    >
        <VsImg
            :src="itemData.image"
            class="vs-main-map-wrapper-list-item__img"
        />

        {{ itemData.title }}

        <VsIcon
            name="internal-link"
            variant="primary"
            size="xxs"
            class="vs-main-map-wrapper-list-item__icon"
        />
    </button>
</template>

<script>
import VsImg from '@components/elements/img/Img';
import VsIcon from '@components/elements/icon/Icon';
/**
 * Renders a list item for the map filter tool.
 * Consists of an image and text in the form of a link.
 *
 * @displayName Main Map Wrapper List Item
 */
export default {
    name: 'VsMainMapWrapperListItem',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsImg,
        VsIcon,
    },
    props: {
        /**
         * Name of item
         */
        itemData: {
            type: Object,
            required: true,
        },
    },
    methods: {
        /**
         * Emits an event with the ID of the chosen
         * item as the payload
         */
        showItemDetail(id) {
            this.$parent.$emit('show-item-detail', id);
            this.$parent.$emit('set-stage', 2);
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
        text-align: left;

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
