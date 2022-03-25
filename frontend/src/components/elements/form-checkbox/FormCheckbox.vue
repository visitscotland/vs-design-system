<template>
    <div>
        <BFormCheckbox
            v-if="fieldName"
            v-model="inputVal"
            class="vs-form-checkbox mr-4"
            :class="(errorsList.length > 0 && $v.inputVal.$anyDirty) || invalid
                ? 'vs-form-checkbox__invalid' : ''"
            :size="size"
            :name="fieldName"
            :id="fieldName"
            :value="value"
            :unchecked_value="`not_${value}`"
            v-bind="$attrs"
            data-test="vs-form-checkbox"
            @change="emitStatus"
            :required="isRequired"
        >
            {{ label }}
            <span v-if="isRequired">
                ({{ requiredText }})
            </span>
        </BFormCheckbox>
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
import { BFormCheckbox } from 'bootstrap-vue';
import validateFormElementMixin from '../../../mixins/validateFormElementMixin';

Vue.use(Vuelidate);

/**
 * https://bootstrap-vue.js.org/docs/components/form-input
 * https://getbootstrap.com/docs/4.3/components/forms/
 *
 * @displayName Form Checkbox
 */

export default {
    name: 'VsFormCheckbox',
    status: 'prototype',
    release: '0.0.1',
    components: {
        BFormCheckbox,
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
         * Name of the input
         */
        fieldName: {
            type: String,
            required: true,
        },
        /**
         * Value when checked
         */
        value: {
            type: String,
            default: '',
        },
        /**
         * Label for checkbox
         */
        label: {
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
         * Prop to trigger manual validation
         */
        triggerValidate: {
            type: Boolean,
            default: false,
        },
        /**
         * text for `required`
         */
        requiredText: {
            type: String,
            default: 'required',
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
         * Prop to define invalid from parent
         */
        invalid: {
            type: Boolean,
            default: false,
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
    data() {
        return {
            inputVal: '',
            errors: [],
        };
    },
    watch: {
        inputVal(newValue) {
            this.$emit('updated', {
                field: this.name,
                value: newValue,
            });
        },
        value(newValue) {
            this.inputVal = newValue;
        },
        /**
         * Watch for prop changing to allow parent
         * to trigger validation
         */
        triggerValidate() {
            this.manualValidate();
        },
    },
    validations() {
        return {
            inputVal: this.rules,
        };
    },
};
</script>

<style lang="scss">
    .vs-form-checkbox {
        input[type="checkbox"] {
            width: 15px;
            height: 15px;
            margin-right: $spacer-4;
        }

        &__invalid {
            border: red 2px solid;
        }
    }
</style>

<docs>
```jsx
<BsWrapper>
    <VsFormCheckbox
        field-name="checkbox-example"
        value="accepted"
        id="checkbox-example"
        label="I accept the terms and conditions"
    />
</BsWrapper>
```
</docs>
