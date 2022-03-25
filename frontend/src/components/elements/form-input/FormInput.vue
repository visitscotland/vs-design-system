<template>
    <div data-test="vs-input">
        <BFormInput
            :type="type"
            class="vs-input"
            v-model="inputVal"
            :class="$v.inputVal.$anyError || invalid ? 'hasError' : ''"
            @blur="emitStatus"
            :id="fieldName"
            :name="fieldName"
            :required="isRequired"
            :size="size"
            :v="inputVal"
        />

        <span
            v-for="error in errorsList"
            :key="error"
            class="error"
        >
            <template
                v-if="$v.inputVal.$anyError || invalid"
            >
                {{ validationMessages[error] || genericValidation[error] }}
            </template>
        </span>
    </div>
</template>

<script>
import Vue from 'vue';
import Vuelidate from 'vuelidate';
import { BFormInput } from 'bootstrap-vue';
import validateFormElementMixin from '../../../mixins/validateFormElementMixin';

Vue.use(Vuelidate);

/**
 * https://bootstrap-vue.js.org/docs/components/form-input
 * https://getbootstrap.com/docs/4.3/components/forms/
 *
 * @displayName Form Input
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
            required: true,
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
    validations() {
        return this.rules;
    },
};
</script>

<style lang="scss">
.vs-form-input {
    &.form-control {
        border-color: $color-gray-tint-1;
        transition: box-shadow $duration-base;

        &:focus {
        border-color: $color-gray-tint-1;
        box-shadow: 0 0 0 0.2rem rgba(187, 38, 132, 0.5); // primary rgb equivalent
        }

        &[type="search"] {
        @extend %reset-clear;
        }
    }
}

.hasError {
    border: red 3px solid !important;
}
</style>

<docs>
```jsx
<BsWrapper>
    <label for="small">Small</label>
    <VsFormInput
        id="small" placeholder="Enter your name" class="mb-5" size="sm" field-name="input1"
    />
    <label for="medium">Medium (default)</label>
    <VsFormInput
        id="medium" placeholder="Enter your name" class="mb-5" size="md" field-name="input2"
    />
    <label for="large">Large</label>
    <VsFormInput
        id="large" placeholder="Enter your name" class="mb-5" size="lg" field-name="input3"
    />

    <label for="input-none">No State</label>
    <VsFormInput
        id="input-none" :state="null" placeholder="No validation" class="mb-5" field-name="input4"
    />
    <label for="input-valid">Valid state</label>
    <VsFormInput
        id="input-valid" :state="true" placeholder="Valid" class="mb-5" field-name="input5"
    />
    <label for="input-invalid">Invalid state</label>
    <VsFormInput
        id="input-invalid" :state="false" placeholder="Invalid" class="mb-5" field-name="input5"
    />
</BsWrapper>
```
</docs>
