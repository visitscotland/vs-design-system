<template>
    <div>
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
        <BFormCheckbox
            v-if="fieldName"
            v-model="inputVal"
            class="vs-form-checkbox mr-4"
            :class="errorClass"
            :size="size"
            :name="fieldName"
            :id="fieldName"
            :value="value"
            :unchecked_value="`not_${value}`"
            v-bind="$attrs"
            data-test="vs-form-checkbox"
            @change="emitStatus"
            :required="isRequired"
            :aria-invalid="$v.inputVal.$anyError || invalid"
        >
            <span class="vs-form-checkbox__label">{{ label }}</span>
        </BFormCheckbox>
    </div>
</template>

<script>
import Vue from 'vue';
import Vuelidate from 'vuelidate';
import { BFormCheckbox } from 'bootstrap-vue';
import validateFormElementMixin from '../../../mixins/validateFormElementMixin';

Vue.use(Vuelidate);

/**
 * A checkbox input
 *
 * @displayName Checkbox
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
    computed: {
        errorClass() {
            return (this.errorsList.length > 0 && this.$v.inputVal.$anyDirty) || this.invalid
                ? 'vs-form-checkbox--error' : '';
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
        return {
            inputVal: this.rules,
        };
    },
};
</script>

<style lang="scss">
    .vs-form-checkbox {
        display: flex;
        align-items: center;

        input[type="checkbox"] {
            outline: $color-gray-shade-3 1px solid;
            border: none;
            width: 38px;
            height: 38px;
            margin: $spacer-2 $spacer-4 0 0;
            align-self: flex-start;
            position: relative;

             &:before {
                content: '';
                width: 100%;
                height: 100%;
                background-color: $color-white;
                position: absolute;
                top: 0;
                left: 0;
            }

            &:after {
                content: '';
                position: absolute;
                width: 100%;
                height: 100%;
            }

            &:focus {
                outline: none;

                &:after {
                    border: $color-pink 4px solid;
                }
            }

            &:checked {
                &:before {
                    background-image: url('~@/assets/svg/checkbox-check.svg');
                    background-size: 60% 60%;
                    background-position: center center;
                    background-repeat: no-repeat;
                }
            }
        }

        label {
            flex: 1;
            margin-bottom: 0;
        }

        &--error {
            input[type="checkbox"] {
                outline: none;

                &:after {
                    content: '';
                    position: absolute;
                    width: 100%;
                    height: 100%;
                    border: 2px solid $color-theme-danger;
                }
            }
        }

        &__label {
            display: inline-block;
            font-size: $font-size-body;
            font-weight: $font-weight-normal;
        }
    }
</style>

<docs>
```jsx
<BsWrapper>
    <BFormGroup>
        <VsFormCheckbox
            field-name="checkbox-example"
            value="accepted"
            id="checkbox-example"
            label="I accept the terms and conditions"
            class="mb-6"
        />
    </BFormGroup>
    <BFormGroup>
        <VsFormCheckbox
            field-name="checkbox-example-2"
            value="second"
            id="checkbox-example-2"
            label="By ticking this box you are indicating your consent for VisitScotland
            to use your email address to send you our e-newsletter on a regular basis.
            You can unsubscribe at any time via the link in the email. We will process
            your details in accordance with our privacy policy"
        />
    </BFormGroup>
</BsWrapper>
```
</docs>
