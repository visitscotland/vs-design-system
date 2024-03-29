<template>
    <div
        class="vs-main-map-category"
        data-test="vs-main-map-category"
    >
        <VsButton
            class="vs-main-map-category__button"
            :class="`vs-main-map-category__button--${type}`"
            data-test="vs-main-map-category__button"
            variant="transparent"
            icon="internal-link"
            icon-position="right"
            size="sm"
            :uppercase="false"
            @click.native="selectCategory(type)"
            @mouseover.native="isHovered = true"
            @mouseout.native="isHovered = false"
            @focusin.native="isHovered = true"
            @focusout.native="isHovered = false"
        >
            <div>
                <VsMainMapWrapperIcon
                    class="vs-main-map-category__icon"
                    :id="type"
                    :is-hovered="isHovered"
                />
                {{ categoryName }}
            </div>
        </VsButton>
    </div>
</template>

<script>
import VsButton from '@components/elements/button/Button';
import VsMainMapWrapperIcon from './MainMapWrapperIcon';

export default {
    name: 'VsMainMapWrapperCategories',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsButton,
        VsMainMapWrapperIcon,
    },
    props: {
        /**
         * Name of category
         */
        categoryName: {
            type: String,
            required: true,
        },
        /**
         * Category type - for use with markers
         * and data
         */
        type: {
            type: String,
            required: true,
        },
        /**
         * If the category has a subcategory
         */
        hasSubCat: {
            type: Boolean,
            default: false,
        },
    },
    data() {
        return {
            isHovered: false,
        };
    },
    methods: {
        /**
         * Emits category type
         */
        selectCategory(type) {
            if (this.hasSubCat) {
                this.$parent.$emit('set-subcategory', type);
            } else {
                this.$parent.$emit('set-category', type);
                this.$parent.$emit('set-stage', 1);
            }
        },
    },
};
</script>

<style lang="scss">
    .vs-main-map-category {
        position: relative;
        display: flex;
        align-items: center;

        &::before {
            content: '';
            position: absolute;
            bottom: 0;
            left: 0;
            width: 100%;
            background: $color-gray-tint-5;
            height: 1px;
        }

        &__icon {
            vertical-align: middle;
            margin-right: $spacer-4;
        }

        @include map-button-themes;

        &__button.vs-button.btn-transparent {
            font-size: $font-size-5;
            font-weight: $font-weight-bold;
            text-transform: none;
            justify-content: space-between;
            width: 100%;
            text-align: left;
            padding: $spacer-4 $spacer-4;
            border: none;

            .vs-icon--internal-link {
                color: $color-pink;
            }

            &::after {
                display: none;
            }

            &:hover {
                color: $color-white;
                border: none;
                box-shadow: none;

                .vs-icon--internal-link {
                    color: $color-white;
                }

                &::after {
                    display: none;
                }
            }

            &:focus {
                box-shadow: inset 0px 0px 0px 2px $color-theme-primary;
                background: $color-white;
            }

            @include media-breakpoint-up(lg) {
                font-size: $font-size-6;
                font-weight: $font-weight-normal;
                padding: $spacer-2 $spacer-4;
            }
        }

        @include media-breakpoint-up(lg) {
            padding: $spacer-2 0;
        }
    }
</style>
