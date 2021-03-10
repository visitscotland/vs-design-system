<template>
    <div class="vs-stretched-link-panels">
        <div
            class="vs-stretched-link-panels__panel vs-stretched-link-panels__panel--days"
            data-test="vs-stretched-link-panel--days"
        >
            <p data-test>
                <span class="vs-stretched-link-panels__days">{{ days }} </span>{{ daysLabel }}
            </p>
        </div>

        <div
            class="vs-stretched-link-panels__panel"
            data-test="vs-stretched-link-panel"
        >
            <VsIcon
                :name="transport"
                variant="reverse-white"
                class="vs-stretched-link-panels__icon"
            />
            <p data-test="vs-stretched-link-panel__icon">
                {{ transportName }}
            </p>
        </div>
    </div>
</template>

<script>
import VsIcon from '@components/elements/icon/Icon';
/**
 * Panels to show information in the stretched link component
 */
export default {
    name: 'VsStretchedLinkPanels',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsIcon,
    },
    props: {
        /**
        * Optional prop for number of days
        */
        days: {
            type: String,
            default: '',
        },
        /**
        * Optional prop for transport type (will show a the transport icon if used)
        */
        transport: {
            type: String,
            default: '',
        },
        /**
        * Text to use for 'days' label - to allow for CMS translation
        */
        daysLabel: {
            type: String,
            default: 'days',
        },
    },
    computed: {
        transportName() {
            if (this.transport === 'public') {
                return 'public transport';
            }

            return this.transport;
        },
    },
};
</script>

<style lang="scss">
    .vs-stretched-link-panels {
        position: absolute;
        right: $spacer-3;
        top: $spacer-3;
        display: flex;
        flex-direction: row;

        &__panel {
            width: 55px;
            height: 55px;
            background: $color-black;
            margin-left: $spacer-1;
            color: $color-white;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;

            &--days {
                background: $color-secondary-teal-shade-2;
            }

            p {
                margin-bottom: 0;
                text-align: center;
                line-height: $line_height_xs;
                font-size: $xs-font-size;
            }
        }

        &__days {
            font-size: $font-size-lg;
            font-weight: bold;
            display: block;
            margin-bottom: $spacer-1;
        }

        &__icon {
            height: 20px;
            width: auto;
            margin-bottom: $spacer-1;
        }

        @include media-breakpoint-up(sm) {
            right: $spacer-4;
            top: $spacer-4;

            &__panel {
                margin-left: $spacer-2;
            }
        }
    }
</style>

<docs>
    ```
        <vs-container>
            <vs-row>
                <vs-col md="6">
                    <vs-stretched-link-panels
                        days="14"
                        transport="car"
                        daysLabel="days"
                    >
                        <template slot="stretchedLinkPanelDays">days</template>
                    </vs-stretched-link-panels>
                </vs-col>
            </vs-row>
        </vs-container>
    ```
</docs>
