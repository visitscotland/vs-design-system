<template>
    <div
        data-test="vs-input"
    >
        <p
            class="hint-text"
            :id="`hint-${fieldName}`"
        >
            {{ hintText }}
        </p>
        <template
            v-if="$v.inputVal.$anyError || invalid"
        >
            <span
                v-for="error in errors"
                :key="error"
                class="error"
            >
                {{ validationMessages[error] }}
            </span>
        </template>
        <BFormInput
            :type="type"
            class="vs-form-input"
            v-model="inputVal"
            :class="$v.inputVal.$anyError || invalid ? 'vs-form-input--error' : ''"
            @blur="emitStatus"
            :id="fieldName"
            :name="fieldName"
            :required="isRequired"
            :size="size"
            :v="inputVal"
            :aria-invalid="$v.inputVal.$anyError || invalid"
        />
    </div>
</template>

<script>
// eslint-disable-next-line
import Vue from 'vue';
import Vuelidate from 'vuelidate';
// eslint-disable-next-line
import { required, email, minLength, maxLength } from 'vuelidate/lib/validators';
import { BFormInput } from 'bootstrap-vue';

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
    data() {
        return {
            inputVal: this.value,
        };
    },
    computed: {
        /**
         * set rules object for validation
         * needed because `required`, `email` and other values
         * can't be key value pairs
         */
        rules() {
            let rulesObj = {
            };

            // eslint-disable-next-line
            for (const [key, value] of Object.entries(this.validationRules)) {
                // rules have to be either a function defined by
                // https://vuelidate-next.netlify.app/validators.html
                if (key === 'required') {
                    rulesObj = {
                        ...rulesObj,
                        required,
                    };
                } else if (key === 'email') {
                    rulesObj = {
                        ...rulesObj,
                        email,
                    };
                } else if (key === 'minLength') {
                    rulesObj = {
                        ...rulesObj,
                        minLength: minLength(value),
                    };
                } else if (key === 'maxLength') {
                    rulesObj = {
                        ...rulesObj,
                        maxLength: maxLength(value),
                    };
                }
            }

            if (typeof rulesObj !== 'undefined') {
                return {
                    inputVal: rulesObj,
                };
            }

            return {
            };
        },
        isRequired() {
            if (typeof required !== 'undefined' && 'required' in this.validationRules) {
                return true;
            }

            return false;
        },
        errors() {
            const errorsArray = [];
            const rulesKeys = Object.keys(this.rules.inputVal);

            rulesKeys.forEach((key) => {
                if (!this.$v.inputVal[key]) {
                    errorsArray.push(key);
                }
            });

            return errorsArray;
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
         * manually run validation and emit to parent
         */
        manualValidate() {
            this.$emit('status-update', {
                field: this.fieldName,
                value: this.inputVal,
                errors: this.$v.$invalid,
            });
        },
        /**
         * Emit status of input - for automatic updating
         */
        emitStatus() {
            this.$emit('status-update', {
                field: this.fieldName,
                value: this.inputVal,
                errors: this.$v.$anyError,
            });
            this.$v.$touch();
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
