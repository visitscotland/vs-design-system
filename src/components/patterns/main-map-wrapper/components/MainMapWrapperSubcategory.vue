<template>
    <div
        class="vs-main-map-subcategory"
        data-test="vs-main-map-subcategory"
    >
        <BFormGroup
            label="Using options array:"
            v-slot="{ ariaDescribedby }"
        >
            <BFormCheckboxGroup
                id="checkbox-group-1"
                v-model="selected"
                :aria-describedby="ariaDescribedby"
                :name="data.name"
            >
                <BFormCheckbox
                    v-for="item in data"
                    :key="item.id"
                    :value="item.id"
                >
                    {{ item.name }}
                </BFormCheckbox>
            </BFormCheckboxGroup>
        </BFormGroup>

        <div class="vs-main-map-subcategory__controls">
            <VsButton
                class="vs-main-map-subcategory__clear-selection"
                data-test="vs-main-map-subcategory__clear-selection"
                size="sm"
                variant="secondary"
                @click="clearSelection"
            >
                {{ clearSelectionText }}
            </VsButton>
            <VsButton
                class="vs-main-map-subcategory__apply-filters"
                data-test="vs-main-map-subcategory__apply-filters"
                size="sm"
                @click="checkboxesChangeSubmit()"
            >
                {{ applyFiltersText }}
            </VsButton>
        </div>
    </div>
</template>

<script>
import {
    BFormGroup,
    BFormCheckboxGroup,
    BFormCheckbox,
} from 'bootstrap-vue';
import VsButton from '@/components/elements/button/Button';

export default {
    name: 'VsMainMapWrapperSubCategories',
    status: 'prototype',
    release: '0.0.1',
    components: {
        BFormGroup,
        BFormCheckboxGroup,
        BFormCheckbox,
        VsButton,
    },
    inject: [
        'clearSelectionText',
        'applyFiltersText',
    ],
    props: {
        /** Data for subcategory */
        data: {
            type: Array,
            required: true,
        },
    },
    data() {
        return {
            selected: [],
        };
    },
    methods: {
        checkboxesChangeSubmit() {
            this.$parent.$emit('subcategories-filtered', this.selected);
        },
        clearSelection() {
            this.selected = [];
        },
    },
};
</script>

<style lang="scss">
    .vs-main-map-subcategory {
        height: 100%;

        .custom-checkbox {
            display: flex;
            flex-direction: row-reverse;
            justify-content: space-between;

            input[type="checkbox"] {
                width: 30px;
                height: 30px;
            }
        }

        &__controls {
            position: absolute;
            bottom: 0;
            left: 0;
            width: 100%;
            display: flex;
            justify-content: space-around;
            padding: $spacer-4;
            border-top: $color-gray-tint-4 solid 1px;
        }
    }
</style>
