<template>
    <li
        class="vs-summary-box-list-item position-relative"
        data-test="vs-summary-box-list-item"
    >
        <div
            class="vs-summary-box-list-item-display position-absolute d-block text-center w-100"
            data-test="vs-summary-box-list-item-display"
            v-if="text"
        >
            <span>{{ text }}</span>
        </div>

        <div
            class="vs-summary-box-list-item-icon-with-label d-flex align-items-end
            position-absolute justify-content-center w-100"
            data-test="vs-summary-box-list-item-icon-with-label"
            v-if="icon"
        >
            <div class="icon-wrapper text-right">
                <VsIcon
                    slot="icon"
                    :name="icon"
                    variant="dark"
                    size="xl"
                />
            </div>
            <div class="text-wrapper d-block w-auto mx-1">
                <span>{{ iconLabel }}</span>
            </div>
        </div>

        <div
            class="text-center vs-summary-box-list-item-label d-block position-absolute w-100"
            data-test="vs-summary-box-list-item-label"
            v-if="label"
        >
            <strong>{{ label }}</strong>
        </div>
    </li>
</template>

<script>
import VsIcon from '@components/elements/icon/Icon';

/**
 * Summary Box List Item
 *
 * @displayName Summary Box List Item
 */
export default {
    name: 'VsSummaryBoxListItem',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsIcon,
    },
    props: {
        text: {
            type: String,
            default: '',
        },
        label: {
            type: String,
            default: '',
        },
        icon: {
            type: String,
            default: '',
        },
        iconLabel: {
            type: String,
            default: '',
        },
    },
};
</script>

<style lang="scss" scoped>
.vs-summary-box-list-item {
    flex-grow: 1;
    flex-basis: calc(50% - #{$spacer-4});
    margin: $spacer-2;
    @include media-breakpoint-up(md) {
        flex-grow: 1;
        flex-basis: calc(50% - #{$spacer-6});
        margin: $spacer-3;
    }

    &::after {
        border: 1px solid $color-theme-dark;
        content: "";
        display: block;
        padding-bottom: 100%;
    }
}

.vs-summary-box-list-item-label {
    bottom: 5px;
    left: 0;
    line-height: $line_height_xs;

    strong {
        display: block;
        font-size: $small-font-size;
        line-height: $line_height_standard;
        @include media-breakpoint-up(lg) {
            font-size: $lead-font-size
        }
    }
}

.vs-summary-box-list-item-display {
    top: 20%;
    font-family: $headings-font-family;
    font-size: 1.625rem;
    .divider {
        font-family: $headings-font-family-thin;
    }
    @include media-breakpoint-up(lg) {
        font-size: $spacer-7;
    }
}

.vs-summary-box-list-item-icon-with-label {
    bottom: 35%;

    .icon-wrapper {
        max-width: 40%;
        width: auto;
    }
    .text-wrapper {
        font-size: $small-font-size;
        line-height: $line_height_xs;
        max-width: 50%;
        word-wrap: break-word;
        @include media-breakpoint-up(lg) {
            font-size: $spacer-4;
            line-height: $line_height_standard;
        }
    }
}
</style>

<docs>
  ```jsx
    <ul style="width: 200px; list-style-type: none;">
        <VsSummaryBoxListItem
            :text=itineraries.sampleItinerary.totalDays
            label="Days"
        >
        </VsSummaryBoxListItem>
        <VsSummaryBoxListItem
            :icon=itineraries.sampleItinerary.transport.key
            :iconLabel=itineraries.sampleItinerary.transport.value
        >
        </VsSummaryBoxListItem>
    </ul>
  ```
</docs>
