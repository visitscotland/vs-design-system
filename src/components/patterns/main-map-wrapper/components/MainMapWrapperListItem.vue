<template>
    <button
        class="vs-main-map-wrapper-list-item"
        :class="isActive ? 'vs-main-map-wrapper-list-item--hovered' : ''"
        data-test="vs-main-map-wrapper-list-item"
        @click="showItemDetail(formattedData.id)"
        @keyup.enter="showItemDetail(formattedData.id)"
        @mouseover="itemHover(formattedData.id)"
        @mouseleave="itemHover('')"
        @focusin="itemHover(formattedData.id)"
        @focusout="itemHover('')"
    >
        <div class="vs-main-map-wrapper-list-item__img-container">
            <VsImg
                v-if="typeof formattedData.image !== 'undefined'"
                :src="formattedData.image"
                class="vs-main-map-wrapper-list-item__img"
            />
        </div>
        <span
            class="vs-main-map-wrapper-list-item__text"
        >
            {{ formattedData.title }}
        </span>

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
import mapStore from '../../../../stores/map.store';
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
    inject: [
        'mapId',
    ],
    props: {
        /**
         * Name of item
         */
        itemData: {
            type: Object,
            required: true,
        },
        /** If the data source is from an API endpoint */
        fromEndpoint: {
            type: Boolean,
            default: false,
        },
    },
    data() {
        return {
            formattedData: {
            },
        };
    },
    computed: {
        isActive() {
            if (this.highlightedPlace === this.formattedData.id) {
                return true;
            }

            return false;
        },
        highlightedPlace() {
            return mapStore.getters.getHoveredStop(this.mapId);
        },
        selectedSubCategory() {
            return mapStore.getters.getSelectedSubcat;
        },
    },
    mounted() {
        if (!this.fromEndpoint) {
            this.formattedData = this.itemData;
        } else {
            this.formattedData = {
                ...this.formattedData,
                id: this.itemData.id,
                title: this.itemData.name,
            };

            if (typeof this.itemData.images !== 'undefined') {
                this.formattedData = {
                    ...this.formattedData,
                    image: this.itemData.images[0].mediaUrl,
                };
            }
        }
    },
    methods: {
        /**
         * Emits an event with the ID of the chosen
         * item as the payload
         */
        showItemDetail(id) {
            mapStore.dispatch('setActivePlace', {
                mapId: this.mapId,
                placeId: id,
            });

            this.$parent.$emit('show-item-detail', id);
            this.$parent.$emit('set-stage', 2);
        },
        /**
         * Emits an event with the ID of the chosen
         * item on hover
         */
        itemHover(id) {
            console.log('item hover');
            mapStore.dispatch('setHoveredPlace', {
                mapId: this.mapId,
                hoveredId: id,
            });
        },
    },
};
</script>

<style lang="scss">
    .vs-main-map-wrapper-list-item {
        display: flex;
        position: relative;
        width: 100%;
        height: 100px;
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

        &:hover,
        &--hovered {
            box-shadow: $shadow_card;

            .vs-main-map-wrapper-list-item__text{
                text-decoration: underline;
            }
        }

        &:focus {
            @extend %primary-button-focus;
            z-index: 2;
            outline: none;
        }

        &__img-container {
            flex-basis: 100px;
            flex-grow: 0;
            flex-shrink: 0;
            height: 90px;
            position: relative;
            overflow: hidden;
        }

        &__img {
           width: 120px;
           height: 90px;
           object-fit: cover;
        }

        &__icon,
        &__text {
            margin-left: $spacer-2;
        }

        @include media-breakpoint-up(lg) {
            &__img {
                margin-right: $spacer-5;
            }
        }
    }
</style>
