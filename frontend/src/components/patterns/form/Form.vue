<template>
    <div
        class="vs-form"
        data-test="vs-form"
    >
        <!-- element into which the (completely empty) form is embedded invisibly -->
        <form
            style="display:none"
        />

        <form v-if="!submitted">
            <BFormGroup
                v-for="field in formData.fields"
                :key="field.name"
            >
                <label
                    v-if="field.element === 'input'"
                    for="field.name"
                    :key="field.name"
                >
                    {{ field.label }}
                    <span v-if="showRequiredText(field)">
                        ({{ requiredText }})
                    </span>
                    <VsFormInput
                        :ref="field.name"
                        @status-update="updateFieldData"
                        :field-name="field.name"
                        :type="field.type"
                        :validation-rules="field.validation || {}"
                        :invalid="errorFields.indexOf(field.name) > -1 ? true : false"
                        :trigger-validate="triggerValidate"
                    />
                </label>

                <label
                    v-if="field.element === 'select'"
                    for="field.name"
                    :key="field.name"
                >
                    {{ field.label }}
                    ({{ requiredText }})
                    <VsFormSelect
                        :options="field.options"
                        :ref="field.name"
                        @status-update="updateFieldData"
                        :field-name="field.name"
                        :validation-rules="field.validation || {}"
                        :invalid="errorFields.indexOf(field.name) > -1 ? true : false"
                        :trigger-validate="triggerValidate"
                    />
                </label>

                <template v-if="field.element === 'checkbox'">
                    <VsFormCheckbox
                        :key="field.name"
                        :ref="field.name"
                        :name="field.name"
                        :value="field.value"
                        :id="field.name"
                        :label="field.label"
                        @status-update="updateFieldData"
                        :field-name="field.name"
                        :validation-rules="field.validation || {}"
                        :invalid="errorFields.indexOf(field.name) > -1 ? true : false"
                        :trigger-validate="triggerValidate"
                        :required-text="requiredText"
                    />
                </template>
            </BFormGroup>

            <p v-if="showErrorMessage && errorFields.length > 0">
                <slot name="invalid" />
            </p>

            <VsRecaptcha
                @verified="onRecaptchaVerify"
                :site-key="recaptchaKey"
            />

            <button
                @click.stop="preSubmit"
                @keydown.stop="preSubmit"
                type="button"
                class="formSubmit"
            >
                Submit
            </button>
        </form>

        <p v-if="submitting">
            <slot name="submitting" />
        </p>

        <p v-if="submitted">
            <slot name="submitted" />
        </p>

        <p v-if="submitError">
            <slot name="submitError" />
        </p>
    </div>
</template>

<script>
import { BFormGroup } from 'bootstrap-vue';
import VsFormInput from '../../elements/form-input/FormInput';
import VsFormSelect from '../../elements/form-select/FormSelect';
import VsFormCheckbox from '../../elements/form-checkbox/FormCheckbox';
import VsRecaptcha from '../../elements/recaptcha/Recaptcha';

const axios = require('axios');

/**
 * An icon list can be used where there is a list icons with a caption with optional heading.
 * An example use is to create a list of key facilities for a product.
 *
 * @displayName Form
 */

export default {
    name: 'VsForm',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsFormInput,
        VsFormSelect,
        VsFormCheckbox,
        BFormGroup,
        VsRecaptcha,
    },
    props: {
        /**
         * the url for the form data file
         */
        dataUrl: {
            type: String,
            required: true,
        },
        /**
         * text for `required`
         */
        requiredText: {
            type: String,
            default: 'required',
        },
        /**
         * recaptcha site key string
         */
        recaptchaKey: {
            type: String,
            required: true,
        },
    },
    data() {
        return {
            submitted: false,
            submitting: false,
            submitError: false,
            formData: {
            },
            form: {
            },
            formIsInvalid: false,
            showErrorMessage: false,
            errorFields: [
            ],
            triggerValidate: false,
        };
    },
    created() {
        axios.get(this.dataUrl)
            .then((response) => {
                this.formData = response.data;

                response.data.fields.forEach((field) => {
                    this.form[field.name] = '';
                });
            });
    },
    mounted() {
        // window.MktoForms2.loadForm('//e.visitscotland.com', '638-HHZ-510', this.formId);
        window.MktoForms2.loadForm('//app-lon10.marketo.com', '830-QYE-256', 90);
    },
    methods: {
        /**
         * update field data and error status
         */
        updateFieldData(data) {
            this.form[data.field] = data.value || '';

            if (data.errors.length > 0) {
                this.formIsInvalid = true;
            } else {
                this.formIsInvalid = false;
            }

            this.manageErrorStatus(data.field, data.errors);
        },
        /**
         * update error status of fields for validation feedback
         */
        manageErrorStatus(field, errors) {
            const index = this.errorFields.indexOf(field);

            if (index !== -1) {
                if (!errors || errors.length < 1) {
                    this.errorFields.splice(index, 1);
                }
            } else if (errors) {
                this.errorFields.push(field);
            }
        },
        showRequiredText(field) {
            if (typeof field.validation !== 'undefined' && field.validation.required) {
                return true;
            }

            return false;
        },
        /**
         * submit form
         */
        preSubmit() {
            function isRequired(value) {
                return value.validation && value.validation.required;
            }

            this.triggerValidate = true;

            const fieldIsRequired = this.formData.fields.filter(isRequired);

            if (fieldIsRequired.length === 0) {
                this.formIsInvalid = false;
            } else {
                fieldIsRequired.forEach((field) => {
                    if (this.form[field.name] === '') {
                        this.formIsInvalid = true;
                    }
                });
            }

            this.showErrorMessage = this.formIsInvalid.length > 1;

            if (!this.formIsInvalid) {
                console.log('submitted');
                this.marketoSubmit();
            } else {
                this.showErrorMessage = true;
            }
        },
        marketoSubmit() {
            const myForm = window.MktoForms2.allForms()[0];
            myForm.addHiddenFields(this.form);
            myForm.submit(() => {
                this.submitting = true;
            });
            /* eslint-ignore-next-line */
            myForm.onSuccess(() => {
                console.log('data submitted');
                this.submitting = false;
                this.submitted = true;
                return false;
            });
        },
        onRecaptchaVerify() {
            console.log('verified by recaptcha');
        },
    },
};
</script>

<style lang='scss'>
    .vs-form {
        input {
            border: 2px solid $color-theme-primary;
        }

        .mktoAsterix {
            display: none;
        }
    }
</style>

<docs>
    ```jsx
        <VsForm
            requiredText="required"
            dataUrl="http://127.0.0.1:5050/marketoTest.json"
            recaptchaKey="6LfqqfcZAAAAACbkbPaHRZTIFpKZGAPZBDkwBKhe"
        >
            <template slot="invalid">
                You have invalid fields - please check the form.
            </template>

            <template slot="submitError">
                We're sorry there's been a problem, please try again later.
            </template>

            <template slot="submitting">
                We're just submitting your form
            </template>

            <template slot="submitted">
                Thank you for your details, your form has been submitted
            </template>
        </VsForm>
    ```
</docs>
