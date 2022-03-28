<template>
    <div
        class="vs-form-select"
        :class="errorClass"
    >
        <p
            class="hint-text"
            :id="`hint-${fieldName}`"
        >
            {{ hintText }}
        </p>
        <BFormSelect
            v-model="inputVal"
            :size="size"
            v-bind="$attrs"
            :options="options"
            :name="fieldName"
            :id="fieldName"
            @change="emitStatus"
            @blur="emitStatus"
            data-test="vs-form-select"
            class="vs-form-select"
            :required="isRequired"
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
        <div class="vs-form-select__container">
            <BFormSelect
                v-model="inputVal"
                :size="size"
                v-bind="$attrs"
                :options="options"
                :name="fieldName"
                :id="fieldName"
                @change="emitStatus"
                @blur="emitStatus"
                data-test="vs-form-select"
                class="vs-form-select__element"
                :required="isRequired"
                :aria-invalid="$v.inputVal.$anyError || invalid"
                :aria-describedby="`hint-${fieldName}`"
                :class="$v.inputVal.$anyError || invalid ? 'vs-form-select__element--error' : ''"
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
 * A select element
 *
 * @displayName Select
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
            touched: false,
        };
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
    .vs-form-select {
        &__container {
            position: relative;
            width: 100%;
            cursor: pointer;
            height: 50px;
            border-radius: 0;
            margin-top: $spacer-2;

            &::after {
                content: "";
                width: 1.5rem;
                height: 1.5rem;
                border: 1px solid $color-black;
                border-radius: 1000px;
                background-image: url('~@/assets/svg/icons/chevron.svg');
                display: block;
                position: absolute;
                top: calc(50% - 0.75rem);
                right: $spacer-4;
                background-repeat: no-repeat;
                background-size: 60% 60%;
                background-position: center center;
                transform: rotate(180deg);
                pointer-events: none;
            }
        }

        &__element {
            // A reset of styles, including removing the default dropdown arrow
            appearance: none;
            background-color: transparent;
            border: none;
            padding: 0 $spacer-4 0;
            margin: 0;
            width: 100%;
            font-family: inherit;
            font-size: inherit;
            cursor: inherit;
            line-height: inherit;
            height: 50px;
            border: $color-gray-shade-3 1px solid;

            &--error {
                border: 2px solid $color-theme-danger;
            }

            &:focus {
                outline: none;
                box-shadow: none;

                & + .vs-form-select__focus {
                    position: absolute;
                    top: -1px;
                    left: -1px;
                    right: -1px;
                    bottom: -1px;
                    border: $color-pink 4px solid;
                }
            }
        }
    }
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
