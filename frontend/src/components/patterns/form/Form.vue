<template>
    <div
        class="vs-form"
        data-test="vs-form"
    >
        <!-- element into which the (completely empty) form is embedded invisibly -->
        <form
            style="display:none"
        />

        <form
            v-if="!submitted"
            @submit.prevent="preSubmit"
        >
            <BFormGroup
                v-for="field in formData.fields"
                :key="field.name"
            >
                <label
                    v-if="field.element === 'input'"
                    :for="field.name"
                    :key="field.name"
                >
                    {{ getTranslatedLabel(field.name) }}
                    <span v-if="showRequiredText(field)">
                        ({{ requiredText }})
                    </span>
                    <VsFormInput
                        :ref="field.name"
                        @status-update="updateFieldData"
                        :field-name="field.name"
                        :type="field.type"
                        :validation-rules="field.validation || {}"
                        :validation-messages="getTranslatedValidation(field.name) || {}"
                        :invalid="errorFields.indexOf(field.name) > -1 ? true : false"
                        :trigger-validate="triggerValidate"
                        :generic-validation="messagingData.validation[language]"
                    />
                </label>

                <label
                    v-if="field.element === 'select'"
                    :for="field.name"
                    :key="field.name"
                >
                    {{ field.label }}
                    ({{ requiredText }})
                    <VsFormSelect
                        :options="getTranslatedOptions(field.name)"
                        :ref="field.name"
                        @status-update="updateFieldData"
                        :field-name="field.name"
                        :validation-rules="field.validation || {}"
                        :validation-messages="getTranslatedValidation(field.name) || {}"
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
                        :validation-messages="getTranslatedValidation(field.name) || {}"
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
                :invalid="!recaptchaVerified && showErrorMessage"
            />

            <input
                type="submit"
                :value="submitText || messagingData.submit[language]"
                class="formSubmit"
                @click.prevent="preSubmit"
                @keyup.prevent="preSubmit"
            >
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
 * A form that results in a user posting data to Marketo.
 * The form is created using an external json config file which is
 * defined in a prop.
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
        /**
         * submit text
         */
        submitText: {
            type: String,
            default: '',
        },
        /**
         * if the form should use sandbox or live Marketo details
         */
        isProd: {
            type: Boolean,
            default: false,
        },
        /**
         * munchkin ID for the Marketo environment
         */
        munchkinId: {
            type: String,
            required: true,
        },
        /**
         * Marketo instance URL for the form
         */
        marketoInstance: {
            type: String,
            required: true,
        },
        /**
         * language indicator for content
         */
        language: {
            type: String,
            default: 'en',
        },
        /**
         * URL for generic messaging config
         */
        messagingUrl: {
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
            messagingData: {
            },
            form: {
            },
            formIsInvalid: false,
            showErrorMessage: false,
            errorFields: [
            ],
            triggerValidate: false,
            recaptchaVerified: false,
        };
    },
    computed: {
        formId() {
            return this.isProd ? this.formData.formLiveId : this.formData.formSandboxId;
        },
    },
    created() {
        axios.get(this.dataUrl)
            .then((response) => {
                this.formData = response.data;

                if (window.MktoForms2) {
                    window.MktoForms2.loadForm(this.marketoInstance, this.munchkinId, this.formId);
                }

                // window.MktoForms2.loadForm('//e.visitscotland.com', '638-HHZ-510', this.formId);
                // ('//app-lon10.marketo.com', '830-QYE-256', this.formId);

                response.data.fields.forEach((field) => {
                    this.form[field.name] = '';
                });
            });

        axios.get(this.messagingUrl)
            .then((response) => {
                this.messagingData = response.data;
            });
    },
    methods: {
        /**
         * get translated label if available
         */
        getTranslatedLabel(fieldName) {
            const languageObj = this.formData[this.language] || undefined;

            if (this.language !== 'en'
                && typeof languageObj[fieldName] !== 'undefined'
                && typeof languageObj[fieldName].label !== 'undefined'
            ) {
                return languageObj[fieldName].label;
            }

            return fieldName;
        },
        getTranslatedValidation(fieldName) {
            const languageObj = this.formData[this.language] || undefined;
            let validationObj = {
            };

            if (this.language !== 'en'
                && typeof languageObj[fieldName] !== 'undefined'
                && typeof languageObj[fieldName].validationMessages !== 'undefined') {
                validationObj = languageObj[fieldName].validationMessages;
            } else {
                this.formData.fields.forEach((field, index) => {
                    if (field.name === fieldName) {
                        validationObj = this.formData.fields[index].validationMessages;
                    }
                });
            }

            return validationObj;
        },
        getTranslatedOptions(fieldName) {
            const languageObj = this.formData[this.language] || undefined;
            let optionsObj = {
            };

            if (this.language !== 'en'
                && typeof languageObj[fieldName] !== 'undefined'
                && typeof languageObj[fieldName].options !== 'undefined') {
                optionsObj = languageObj[fieldName].options;
            } else {
                this.formData.fields.forEach((field, index) => {
                    if (field.name === fieldName) {
                        optionsObj = this.formData.fields[index].options;
                    }
                });
            }

            return optionsObj;
        },
        /**
         * update field data and error status
         */
        updateFieldData(data) {
            this.form[data.field] = data.value || '';

            if (Array.isArray(data.errors)) {
                this.formIsInvalid = data.errors.length > 0;
            } else {
                this.formIsInvalid = data.errors;
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
        preSubmit(e) {
            e.preventDefault();

            function isRequired(value) {
                return value.validation && value.validation.required;
            }

            if (window.grecaptcha.getResponse() !== '') {
                this.recaptchaVerified = true;
            } else {
                this.recaptchaVerified = false;
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

            if (!this.formIsInvalid && this.recaptchaVerified) {
                this.marketoSubmit();
            } else {
                this.showErrorMessage = true;
            }
        },
        marketoSubmit() {
            const myForm = window.MktoForms2.allForms()[0];
            myForm.addHiddenFields(this.form);
            myForm.addHiddenFields({
                lastReCAPTCHAUserFingerprint: window.grecaptcha.getResponse(),
                lastRecaptchaEnabledFormID: this.formId,
            });

            myForm.submit(() => {
                this.submitting = true;
            });
            /* eslint-ignore-next-line */
            myForm.onSuccess(() => {
                this.submitting = false;
                this.submitted = true;
                return false;
            });
        },
        onRecaptchaVerify() {
            if (window.grecaptcha.getResponse() !== '') {
                this.recaptchaVerified = true;
            } else {
                this.recaptchaVerified = false;
            }
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
            dataUrl="http://172.28.74.142:5000/simpleForm.json"
            messagingUrl="https://static.visitscotland.com/forms/messaging.json"
            recaptchaKey="6LfqqfcZAAAAACbkbPaHRZTIFpKZGAPZBDkwBKhe"
            marketo-instance="//app-lon10.marketo.com"
            munchkin-id="830-QYE-256"
            :is-prod="true"
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
