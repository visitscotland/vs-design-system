<template>
    <div>
        <BFormCheckbox
            v-if="fieldName"
            v-model="inputVal"
            class="vs-form-checkbox mr-4"
            :class="errors.length > 0 ? 'vs-form-checkbox__invalid' : ''"
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
        <template
            v-if="errors.length > 0"
        >
            {{ validationMessages.required }}
        </template>
    </div>
</template>

<script>
import Vue from 'vue';
import Vuelidate from 'vuelidate';
// eslint-disable-next-line
import { required, sameAs } from 'vuelidate/lib/validators';
import { BFormCheckbox } from 'bootstrap-vue';

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
    },
    data() {
        return {
            inputVal: '',
            errors: [],
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
                        sameAs: sameAs(() => true),
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
        isRequired() {
            if (typeof required !== 'undefined' && 'required' in this.validationRules) {
                return true;
            }

            return false;
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
            if (this.rules.required && !this.inputVal) {
                this.errors.push('required');
            } else {
                for (let i = 0; i < this.errors.length; i++) {
                    if (this.errors[i] === 'required') {
                        this.errors.splice(i, 1);
                    }
                }
            }

            this.$emit('status-update', {
                field: this.fieldName,
                value: this.inputVal,
                errors: this.errors,
            });
        },
        /**
         * Emit status of input - for automatic updating
         */
        emitStatus() {
            setTimeout(() => {
                if (this.rules.required && !this.inputVal) {
                    this.errors.push('required');
                } else {
                    for (let i = 0; i < this.errors.length; i++) {
                        if (this.errors[i] === 'required') {
                            this.errors.splice(i, 1);
                        }
                    }
                }

                this.$emit('status-update', {
                    field: this.fieldName,
                    value: this.inputVal,
                    errors: this.errors,
                });
                this.touched = true;
                this.$v.$touch();
            }, 50);
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
        name="checkbox-example"
        value="accepted"
        id="checkbox-example"
        label="I accept the terms and conditions"
    />
</BsWrapper>
```
</docs>
