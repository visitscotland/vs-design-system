<template>
    <div>
        <p
            class="hint-text"
            :id="`hint-${fieldName}`"
        >
            {{ hintText }}
        </p>

        <!-- eslint-disable vue/no-v-html -->
        <div
            v-if="infoText !== ''"
            v-html="infoText"
        />
        <!-- eslint-enable-vue/no-v-html -->

        <div
            role="alert"
            aria-live="assertive"
            v-if="$v.inputVal.$anyError || invalid"
            :id="`error-${fieldName}`"
        >
            <p
                v-for="error in errorsList"
                v-show="!reAlertErrors"
                :key="error"
                class="error mb-0"
            >
                <span class="sr-only">{{ fieldName }}</span>
                {{ validationMessages[error] || genericValidation[error] }}
            </p>
        </div>

        <BFormCheckbox
            v-if="fieldName"
            v-model="inputVal"
            class="vs-checkbox mr-4"
            :class="errorClass"
            :name="fieldName"
            :id="fieldName"
            :value="value"
            :unchecked_value="`not_${value}`"
            v-bind="$attrs"
            data-test="vs-checkbox"
            @change="emitStatus"
            :required="isRequired"
            :aria-invalid="$v.inputVal.$anyError || invalid"
            :aria-describedby="$v.inputVal.$anyError || invalid ?
                `error-${fieldName}` : `hint-${fieldName}`"
        >
            <span class="vs-checkbox__label">{{ label }}</span>
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
 * Checkboxes allow a user to select multiple options from in a
 * list or mark an individual item as selected.
 *
 * @displayName Checkbox
 */

export default {
    name: 'VsCheckbox',
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
         * Prop to trigger manual validation. Used by a parent
         * component to trigger validation eg. when the submit
         * button is clicked.
         */
        triggerValidate: {
            type: Boolean,
            default: false,
        },
        /**
         * Specific validation messages for different
         * types of validation
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
         * Fallback translated validation - this is a set of
         * validation messages to be used when no specific
         * validation message is needed, eg. "This field is required"
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
         * Content for info text
         */
        infoText: {
            type: String,
            default: '',
        },
        /**
         * Whether the parent form has just been submitted, if so all errors
         * need to be wiped from then re-added to the DOM to inform screen
         * readers that they should be re-declared
         */
        reAlertErrors: {
            type: Boolean,
            default: false,
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
                ? 'vs-checkbox--error' : '';
        },
    },
    watch: {
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
    @include forms-common;

    .vs-checkbox {
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
                    @include form-focus-state
                }
            }

            &:checked {
                &:before {
                    font-family: "Font Awesome Kit";
                    content: "\e001";
                    color: $color-pink;
                    text-align: center;
                    line-height: 2;
                    font-size: $font-size-6;
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
                    @include form-error-state;
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
