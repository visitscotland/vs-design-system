<template>
    <div
        data-test="vs-form-input"
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
            :type="type"
            class="vs-form-input"
            v-model="inputVal"
            :class="errorClass"
            @blur="emitStatus"
            :id="fieldName"
            :name="fieldName"
            :required="isRequired"
            :size="size"
            :v="inputVal"
            :aria-invalid="$v.inputVal.$anyError || invalid"
            :aria-describedby="$v.inputVal.$anyError || invalid ? `error-${fieldName}` : ''"
        />
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
        /**
         * Content for hint text
         */
        hintText: {
            type: String,
            default: '',
        },
    },
    computed: {
        errorClass() {
            return this.$v.inputVal.$anyError || this.invalid ? 'hasError' : '';
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
    border: $color-gray-shade-3 1px solid;
    margin-top: $spacer-2;
    // transition: box-shadow $duration-base;

    &.form-control-md {
        height: 50px;
    }

    &:focus {
        // border-color: $color-gray-tint-1;
        // box-shadow: 0 0 0 0.2rem rgba(187, 38, 132, 0.5);
        border: $color-pink 4px solid;
        outline: none;
        box-shadow: none;
    }

    &--error {
        border: 2px solid $color-theme-danger;
    }

    &[type="search"] {
        @extend %reset-clear;
    }
}

//     &.form-control {
//         border-color: $color-gray-tint-1;
//         transition: box-shadow $duration-base;

//         &:focus {
//         border-color: $color-gray-tint-1;
//         box-shadow: 0 0 0 0.2rem rgba(187, 38, 132, 0.5); // primary rgb equivalent
//         }

//         &[type="search"] {
//             @extend %reset-clear;
//         }
//     }

//     &::placeholder {
//             color: $color-secondary-gray;
//         }

//     &:focus {
//         border-color: $color-gray-tint-1;
//         box-shadow: $shadow-form-input;
//     }

//     &[type='search'] {
//         @extend %reset-clear;
//     }

//     &.is-valid{
//         border-color: $color-theme-success!important;
//     }

//     &.is-invalid{
//         border-color: $color-theme-danger!important;
//     }
// }

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
        id="medium" placeholder="Enter your name" class="mb-5" size="md" field-name="input2"
    />
    <label for="large">Large</label>
    <VsFormInput
        id="large" placeholder="Enter your name" class="mb-5" size="lg" field-name="input3"
    />
    <label for="input-invalid">Invalid state</label>
    <VsFormInput
        id="input-invalid"
        invalid="true"
        placeholder="Invalid"
        class="mb-5"
        field-name="input5"
    />
</BsWrapper>
```
</docs>
