<template>
    <BFormGroup
        :class="$v.inputVal.$anyError || invalid ? 'hasError' : ''"
    >
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
        />
    </BFormGroup>
</template>

<script>
import Vue from 'vue';
import Vuelidate from 'vuelidate';
// eslint-disable-next-line
import { required } from 'vuelidate/lib/validators';
import { BFormSelect, BFormGroup } from 'bootstrap-vue';

Vue.use(Vuelidate);

/**
 * https://bootstrap-vue.js.org/docs/components/form-input
 * https://getbootstrap.com/docs/4.3/components/forms/
 *
 * @displayName Form Select
 */

export default {
    name: 'VsFormSelect',
    status: 'prototype',
    release: '0.0.1',
    components: {
        BFormSelect,
        BFormGroup,
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
    },
    data() {
        return {
            inputVal: this.value,
            touched: false,
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
                if (key === 'required') {
                    rulesObj = {
                        ...rulesObj,
                        required,
                    };
                } else {
                    rulesObj[key] = value;
                }
            }

            if (typeof rulesObj !== 'undefined') {
                return rulesObj;
            }

            return {
            };
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
            this.touched = true;
            this.$v.$touch();
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
    >
</BsWrapper>
```
</docs>
