<template>
    <div
        :class="errorClass"
    >
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
        <div class="vs-form-select__container">
            <BFormSelect
                v-model="inputVal"
                :size="size"
                v-bind="$attrs"
                :options="options"
                :name="fieldName"
                :id="fieldName"
                @change="onChange"
                @blur="emitStatus"
                data-test="vs-form-select"
                class="vs-form-select__element"
                :required="isRequired"
                :aria-invalid="$v.inputVal.$anyError || invalid"
                :aria-describedby="`hint-${fieldName}`"
                :class="errorClass"
            />
            <span class="vs-form-select__focus" />
        </div>
    </div>
</template>

<script>
import Vue from 'vue';
import Vuelidate from 'vuelidate';
import { BFormSelect } from 'bootstrap-vue';
import validateFormElementMixin from '../../../mixins/validateFormElementMixin';

Vue.use(Vuelidate);

/**
 * A selecte element to be used in forms
 *
 * @displayName Form Select
 */

export default {
    name: 'VsFormSelect',
    status: 'prototype',
    release: '0.0.1',
    components: {
        BFormSelect,
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
         * Options for select element
         */
        options: {
            type: Array,
            required: true,
        },
        /**
         * Name of the field (for name and id attributes)
         */
        fieldName: {
            type: String,
            required: true,
        },
        /**
         * Default value of the field
         */
        value: {
            type: String,
            default: '',
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
         * Prop to define invalid from parent
         */
        invalid: {
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
    computed: {
        errorClass() {
            return this.$v.inputVal.$anyError || this.invalid ? 'hasError' : '';
        },
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
        return this.rules;
    },
};
</script>

<style lang="scss">

</style>

<docs>
```jsx
<BsWrapper>
    <VsFormSelect
        :options="[
          { value: null, text: 'Please select an option', selected: 'true' },
          { value: 'a', text: 'This is First option' },
          { value: 'b', text: 'Selected Option' },
          { value: { C: '3PO' }, text: 'This is an option with object value' },
          { value: 'd', text: 'This one is disabled', disabled: true }
        ]"
        name="select-example"
    />
</BsWrapper>
```
</docs>
