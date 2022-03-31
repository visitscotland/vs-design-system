<template>
    <div
        data-test="vs-input"
    >
        <p
            class="hint-text"
            :id="`hint-${fieldName}`"
            v-if="hintText"
        >
            {{ hintText }}
        </p>
        <template
            v-if="$v.inputVal.$anyError || invalid"
        >
            <span
                v-for="error in errorsList"
                :key="error"
                class="error"
                :id="`error-${fieldName}`"
            >
                {{ validationMessages[error] || genericValidation[error] }}
            </span>
        </template>
        <BFormInput
            ref="input"
            :type="type"
            class="vs-input mt-2"
            v-model="inputVal"
            :class="elementClass"
            :id="fieldName"
            :name="fieldName"
            :placeholder="placeholder"
            :required="isRequired"
            :size="size"
            :v="inputVal"
            :aria-invalid="$v.inputVal.$anyError || invalid"
            :aria-describedby="$v.inputVal.$anyError || invalid ? `error-${fieldName}` : ''"
        />
        <VsButton
            data-test="input-clear-button"
            v-if="showClearButton"
            class="vs-input__clear-button d-none d-lg-block"
            variant="transparent"
            icon-variant-override="secondary"
            icon="close"
            size="md"
            icon-only
            @click.native.prevent="clearInputAndFocus()"
        >
            <span class="sr-only">
                {{ clearButtonText }}
            </span>
        </VsButton>
    </div>
</template>

<script>
import Vue from 'vue';
import Vuelidate from 'vuelidate';
import { BFormInput } from 'bootstrap-vue';
import validateFormElementMixin from '../../../mixins/validateFormElementMixin';

Vue.use(Vuelidate);

/**
 * An input field for text, email, number etc
 *
 * @displayName Input
 */

export default {
    name: 'VsFormInput',
    status: 'prototype',
    release: '0.0.1',
    components: {
        BFormInput,
    },
    mixins: [
        validateFormElementMixin,
    ],
    props: {
        /**
         * Set the form field size.
         * `sm, md, lg`
         */
        size: {
            default: 'md',
            validator: (value) => value.match(/(sm|md|lg)/),
        },
        /**
         * Default value of the field
         */
        value: {
            type: String,
            default: '',
        },
        /**
         * Name of the field (for name and id attributes)
         */
        fieldName: {
            type: String,
            required: true,
        },
        /**
         * Type of input
         */
        type: {
            type: String,
            default: 'text',
        },
        /**
         * Rules for Vuelidate plugin
         */
        validationRules: {
            type: Object,
            default() {
                return {
                };
            },
        },
        /**
         * Prop to define invalid from parent
         */
        invalid: {
            type: Boolean,
            default: false,
        },
        /**
         * Prop to trigger manual validation
         */
        triggerValidate: {
            type: Boolean,
            default: false,
        },
        /**
         * Validation messages
         */
        validationMessages: {
            type: Object,
            default() {
                return {
                };
            },
        },
        /**
         * Fallback translated validation
         */
        genericValidation: {
            type: Object,
            default() {
                return {
                };
            },
        },
        /**
         * Content for hint text
         */
        hintText: {
            type: String,
            default: '',
        },
        /**
         * text for the 'clear' button
         * the existence of this will defined whether the button
         * also exists
         */
        clearButtonText: {
            type: String,
            default: '',
        },
        /**
         * Element placeholder text
         */
        placeholder: {
            type: String,
            default: '',
        },
    },
    computed: {
        /**
         * element type class plus error classes
         */
        elementClass() {
            const errorClass = this.$v.inputVal.$anyError || this.invalid ? 'vs-input--error' : '';
            const nameClass = `vs-input--${this.fieldName}`;

            return `${errorClass} ${nameClass}`;
        },
        showClearButton() {
            if (this.inputVal.length && this.clearButtonText !== '') {
                return true;
            }

            return false;
        },
        errorClass() {
            return this.$v.inputVal.$anyError || this.invalid ? 'vs-input--error' : '';
        },
    },
    watch: {
        /**
         * Watch for prop changing to allow parent
         * to trigger validation
         */
        triggerValidate() {
            this.manualValidate();
        },
        inputVal(newValue) {
            this.$emit('updated', {
                field: this.name,
                value: newValue,
            });
        },
        value(newValue) {
            this.inputVal = newValue;
        },
    },
    methods: {
        /**
         * Clear any text entered in the search input
         */
        clearInput() {
            this.inputVal = '';
            this.$emit('cleared');
        },
        /**
         * Focus on the input
         */
        focusOnInput() {
            this.$nextTick(() => {
                this.$refs.input.$el.focus();
            });
        },
        /**
         * Clears the search input on button click
         * and adds focus back to the input
         */
        clearInputAndFocus() {
            this.clearInput();
            this.focusOnInput();
        },
    },
    validations() {
        return this.rules;
    },
};
</script>

<style lang="scss">
@include forms-common;

.vs-input {
    @include form-element-styles;

    &.form-control-md {
        height: 50px;
    }

    &:focus {
        @include form-focus-state;
    }

    &--error {
        @include form-error-state;
    }

    &--site-search.form-control {
        @extend %reset-clear;
        font-size: $body-font-size;
        height: 50px;
        padding: $spacer-3 $spacer-7 $spacer-3 $spacer-6;
        margin: 0;
        border-color: $color-white;

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

    &__clear-button.vs-button.btn {
        position: absolute;
        right: $spacer-5;
        top: 50%;
        transform: translate(0, -50%);
        padding: $spacer-1;
    }
}
</style>

<docs>
```jsx
<BsWrapper>
    <label for="small">Small</label>
    <VsFormInput
        id="small"
        placeholder="Enter your name"
        class="mb-5"
        size="sm"
        field-name="input1"
    />
    <label for="medium">Medium (default)</label>
    <VsFormInput
        id="medium"
        placeholder="Enter your name"
        class="mb-5"
        size="md"
        field-name="input1"
        hint-text="This is some hint text"
    />
    <label for="large">Large</label>
    <VsFormInput
        id="large" placeholder="Enter your name" class="mb-5" size="lg" field-name="input3"
    />
    <label for="input-invalid">Invalid state</label>
    <VsFormInput
        id="input-invalid"
        :invalid="true"
        placeholder="Invalid"
        class="mb-5"
        field-name="input5"
    />
</BsWrapper>
```
</docs>
