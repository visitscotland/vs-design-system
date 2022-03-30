<template>
    <div
        class="vs-site-search-form"
        data-test="vs-site-search-form"
    >
        <VsContainer fluid="lg">
            <VsRow>
                <VsCol cols="12">
                    <BForm
                        role="search"
                        id="cludo-search-form"
                        class="d-flex align-items-start"
                        action
                        method="get"
                        :novalidate="true"
                        :validated="validated"
                        @focus="focusOnInput"
                        @submit="onSubmit"
                        tabindex="-1"
                    >
                        <div class="d-flex flex-column flex-grow-1 position-relative">
                            <label
                                for="search-input"
                                class="vs-site-search-form__label"
                            >
                                <span class="sr-only">
                                    {{ labelText }}
                                </span>
                                <VsIcon
                                    name="search"
                                    size="xxs"
                                    variant="secondary"
                                />
                            </label>

                            <VsFormInput
                                type="search"
                                class="vs-site-search-form__input search-input"
                                aria-label="Search"
                                :placeholder="labelText"
                                autocomplete="off"
                                ref="searchInput"
                                id="search-input"
                                @input.native="onInput"
                                @updated="updateFieldData"
                                @v-model="inputVal"
                                :clear-button-text="clearButtonText"
                                required="true"
                                field-name="site-search"
                                :invalid="!isValid && touched === true ? true : false"
                            />
                        </div>

                        <VsButton
                            type="submit"
                            class="vs-site-search-form__search-button search-button"
                            variant="primary"
                            size="lg"
                            id="search-button"
                        >
                            {{ submitButtonText }}
                        </VsButton>
                    </BForm>
                </VsCol>
            </VsRow>
        </VsContainer>

        <VsButton
            class="vs-site-search-form__close-button d-none d-lg-block"
            variant="transparent"
            icon-variant-override="dark"
            icon="close-circle"
            size="md"
            icon-only
            @click.native="closeSearchForm"
        >
            <span class="sr-only">
                {{ closeButtonText }}
            </span>
        </VsButton>
    </div>
</template>

<script>
import VsIcon from '@components/elements/icon/Icon';
import VsFormInput from '@components/elements/form-input/FormInput';
import VsButton from '@components/elements/button/Button';
import {
    VsCol, VsRow, VsContainer,
} from '@components/elements/grid';

import { BForm } from 'bootstrap-vue';

/**
 * Search form used for the global site search.
 *
 * @displayName Site Search Form
 */
export default {
    name: 'VsSiteSearchForm',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsIcon,
        BForm,
        VsFormInput,
        VsButton,
        VsCol,
        VsRow,
        VsContainer,
    },
    props: {
        /**
         * Text that renders in form label (sr-only) and input placeholder
         */
        labelText: {
            type: String,
            default: 'What are you looking for?',
        },
        /**
         * Text that renders inside the submit button
         */
        submitButtonText: {
            type: String,
            default: 'Search',
        },
        /**
         * Text that renders inside the clear button (sr-only)
         */
        clearButtonText: {
            type: String,
            default: 'Clear form',
        },
        /**
         * Text that renders inside the close button (sr-only)
         */
        closeButtonText: {
            type: String,
            default: 'Close search form',
        },
    },
    data() {
        return {
            searchTerm: '',
            validated: null,
            showSearchForm: true,
            triggerValidate: false,
            touched: false,
            inputVal: '',
        };
    },
    computed: {
        isValid() {
            if (this.searchTerm !== '' && this.touched) {
                return true;
            }

            return false;
        },
    },
    methods: {
        /**
         * Focus on the input
         */
        focusOnInput() {
            this.$refs.searchInput.$el.focus();
        },
        /**
         * Updates the data value for the field
         */
        updateFieldData(data) {
            this.searchTerm = data.value;
            if (this.searchTerm !== '') {
                this.isValid = true;
            } else {
                this.isValid = false;
            }
            this.touched = true;
        },
        /**
         * Validates the form and submits if true
         */
        onSubmit($event) {
            this.touched = true;
            this.triggerValidate = true;

            if (!this.isValid) {
                $event.preventDefault();
                this.touched = false;
                this.validated = false;
            }

            return true;
        },
        /**
         * Validates the form on input
         */
        onInput() {
            this.validated = this.isValid ? null : false;
        },
        /**
         * Closes the search form popover
         */
        closeSearchForm() {
            this.showSearchForm = !this.showSearchForm;
            this.$emit('toggleAction', this.showSearchForm);
        },
    },
};
</script>

<style lang="scss">

.vs-site-search-form {
    background-color: rgba(239, 239, 239, 0.5);
    backdrop-filter: blur(30px);
    padding: $spacer-7 0;
    position: absolute;
    width: 100%;

    @include media-breakpoint-up(xl) {
        padding: $spacer-9 0;
    }

    &__label {
        position: absolute;
        left: $spacer-2;
        top: 50%;
        transform: translate(0, -50%);

        @include media-breakpoint-up(lg) {
            left: $spacer-8;

            .vs-icon{
                height: $icon-size-lg;
                width: $icon-size-lg;
                font-size: $icon-size-lg;
            }
        }
    }

    &__input {
        .form-control {
            font-size: $body-font-size;
            height: 50px;
            padding: $spacer-3 $spacer-7 $spacer-3 $spacer-6;
            border: 1px $color-white solid;
            margin: 0;

            &:focus {
                box-shadow: $shadow-form-input inset;
                border-color: $color-pink;
            }

            @include media-breakpoint-up(lg) {
                padding: $spacer-4 $spacer-10 $spacer-4 $spacer-12;
                font-size: $display1-size;
                height: 79px;
            }

            @include media-breakpoint-up(xl) {
                font-size: $font-size-xxl;
                height: 94px;
            }
        }
    }

    &__search-button {
        height: 50px;
        padding: $spacer-3 $spacer-2;
        font-size: $small-font-size;

        @include media-breakpoint-up(lg) {
            height: 79px;
            padding: $spacer-3 $spacer-8;
            font-size: $font-size-lg;
        }

        @include media-breakpoint-up(xl) {
            height: 94px;
        }
    }

    &__clear-button.vs-button.btn {
        position: absolute;
        right: $spacer-5;
        top: 50%;
        transform: translate(0, -50%);
        padding: $spacer-1;
    }

    &__close-button.vs-button.btn {
        position: absolute;
        right: $spacer-2;
        top: $spacer-2;

        @include media-breakpoint-up(xl) {
            right: $spacer-4;
            top: $spacer-4;
        }
    }
}
</style>
