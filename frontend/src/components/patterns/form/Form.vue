<template>
    <div
        class="vs-form"
        data-test="vs-form"
    >
        <!-- element into which the (completely empty) form is embedded invisibly -->
        <form
            class="d-none"
        />

        <form
            v-if="!submitted"
            @submit.prevent="preSubmit"
        >
            <BFormGroup
                v-for="(field, index) in formData.fields"
                :key="field.name"
                :label="needsLabel(field) ? getTranslatedLabel(field.name, index) : ''"
                :label-for="needsLabel(field) ? field.name : ''"
                class="mb-6"
            >
                <template v-if="field.element === 'input'">
                    <VsFormInput
                        :ref="field.name"
                        @status-update="updateFieldData"
                        @v-model="inputVal"
                        :field-name="field.name"
                        :type="field.type"
                        :validation-rules="field.validation || {}"
                        :validation-messages="getTranslatedValidation(field.name, index) || {}"
                        :generic-validation="getMessagingData('validation', language)"
                        :invalid="errorFields.indexOf(field.name) > -1 ? true : false"
                        :trigger-validate="triggerValidate"
                        :hint-text="getTranslatedHint(field.name, index)"
                    />
                </template>

                <template v-if="field.element === 'select'">
                    <VsFormSelect
                        :options="getTranslatedOptions(field.name, index)"
                        :ref="field.name"
                        @status-update="updateFieldData"
                        :field-name="field.name"
                        :validation-rules="field.validation || {}"
                        :validation-messages="getTranslatedValidation(field.name, index) || {}"
                        :generic-validation="getMessagingData('validation', language)"
                        :invalid="errorFields.indexOf(field.name) > -1 ? true : false"
                        :trigger-validate="triggerValidate"
                        :hint-text="getTranslatedHint(field.name, index)"
                    />
                </template>

                <template v-if="field.element === 'checkbox'">
                    <VsFormCheckbox
                        :key="field.name"
                        :ref="field.name"
                        :name="field.name"
                        :value="field.value"
                        :id="field.name"
                        :label="field.descriptor"
                        @status-update="updateFieldData"
                        :field-name="field.name"
                        :validation-rules="field.validation || {}"
                        :validation-messages="getTranslatedValidation(field.name, index) || {}"
                        :generic-validation="getMessagingData('validation', language)"
                        :invalid="errorFields.indexOf(field.name) > -1 ? true : false"
                        :trigger-validate="triggerValidate"
                        :required-text="getMessagingData('required', language)"
                        :hint-text="getTranslatedHint(field.name, index)"
                    />
                </template>
            </BFormGroup>

            <VsRecaptcha
                @verified="onRecaptchaVerify"
                :site-key="recaptchaKey"
                :invalid="!recaptchaVerified && showErrorMessage"
                :language="language"
                :error-msg="getMessagingData('recaptchaError', language)"
            />

            <p v-if="errorFields.length > 0">
                <slot name="invalid" />
            </p>

            <VsButton
                variant="primary"
                type="submit"
                class="vs-form__submit mt-9"
                @click.native="preSubmit"
                @keyup.native="preSubmit"
            >
                {{ getTranslatedSubmitText }}
            </VsButton>
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
import VsButton from '../../elements/button/Button';

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
        VsButton,
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
         * recaptcha site key string
         */
        recaptchaKey: {
            type: String,
            required: true,
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
            inputVal: '',
        };
    },
    computed: {
        formId() {
            return this.isProd ? this.formData.formLiveId : this.formData.formSandboxId;
        },
        getTranslatedSubmitText() {
            let text;
            const languageObj = this.getLanguageObj();

            if (this.language === 'en') {
                text = this.formData.submit;
            } else if (typeof languageObj.submit !== 'undefined') {
                text = languageObj.submit;
            } else {
                text = this.getMessagingData('submit', this.language);
            }

            return text;
        },
    },
    created() {
        this.getFormData();
        this.getGlobalMessaging();
    },
    methods: {
        /**
         * Axios call to retrieve form data
         */
        getFormData() {
            axios.get(this.dataUrl)
                .then((response) => {
                    this.formData = response.data;

                    if (window.MktoForms2) {
                        window.MktoForms2
                            .loadForm(this.marketoInstance, this.munchkinId, this.formId);
                    }

                    response.data.fields.forEach((field) => {
                        this.form[field.name] = '';
                    });
                });
        },
        /**
         * Axios call to retrieve global messaging data
         */
        getGlobalMessaging() {
            axios.get(this.messagingUrl)
                .then((response) => {
                    this.messagingData = response.data;
                });
        },
        /**
         * get appropriate language object
         */
        getLanguageObj() {
            let languageObj;

            if (!this.isUndefined(this.formData[this.language])) {
                languageObj = this.formData[this.language] || undefined;
            } else {
                languageObj = {
                };
            }

            return languageObj;
        },
        /**
         * get translated label if available
         */
        getTranslatedLabel(fieldName, index) {
            const languageObj = this.getLanguageObj();
            let labelText = '';

            if (this.language !== 'en'
                && !this.isUndefined(languageObj[fieldName])
                && !this.isUndefined(languageObj[fieldName].label)
            ) {
                labelText = languageObj[fieldName].label;
            } else {
                labelText = this.formData.fields[index].label;
            }

            // To be added if 'required' needs to be added to label
            // I will check this and remove if not needed - JH

            // if (!this.isUndefined(this.formData.fields[index].validation)
            //     && !this.isUndefined(this.formData.fields[index].validation.required)
            //     && this.formData.fields[index].validation.required) {
            //     labelText = `${labelText} (${this.getMessagingData('required', this.language)})`;
            // }

            return labelText;
        },
        /**
         * get translated validation messages
         */
        getTranslatedValidation(fieldName, index) {
            const languageObj = this.getLanguageObj();

            let validationObj;

            if (this.language === 'en'
                && !this.isUndefined(this.formData.fields[index].validationMessages)) {
                validationObj = this.formData.fields[index].validationMessages;
            } else if (!this.isUndefined(languageObj[fieldName])
                && !this.isUndefined(languageObj[fieldName].validationMessages)) {
                validationObj = languageObj[fieldName].validationMessages;
            } else if (this.language === 'en') {
                validationObj = this.formData.fields[index].validationMessages;
            }

            return validationObj;
        },
        /**
         * get translated options for select elements
         */
        getTranslatedOptions(fieldName, index) {
            const languageObj = this.getLanguageObj();

            let optionsArr = [];

            if (this.language !== 'en'
                && !this.isUndefined(languageObj[fieldName])
                && !this.isUndefined([fieldName].options)) {
                optionsArr = languageObj[fieldName].options;
            } else {
                optionsArr = this.formData.fields[index].options;
            }

            return optionsArr;
        },
        getTranslatedHint(fieldName, index) {
            const languageObj = this.getLanguageObj();
            let hintText = '';

            if (this.language === 'en') {
                hintText = this.formData.fields[index].hint;
            } else if (typeof languageObj.submit !== 'undefined') {
                hintText = languageObj.hint;
            } else {
                hintText = '';
            }

            return hintText;
        },
        /**
         * check messaging data exists and then pass value back
         */
        getMessagingData(type, lang) {
            if (Object.keys(this.messagingData).length > 0) {
                const dataType = this.messagingData[lang];
                return dataType[type];
            }

            if (type === 'validation') {
                return {
                };
            }

            return '';
        },
        /**
         * check if value is undefined
         */
        isUndefined(value) {
            if (typeof value === 'undefined') {
                return true;
            }

            return false;
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
            } else if (errors && errors.length > 0) {
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
         * whether or not an element should have a label defined (for Bootstrap Vue)
         */
        needsLabel(field) {
            if (field.element === 'radio'
                || field.element === 'submit'
                || field.element === 'checkbox-group') {
                return false;
            }

            return true;
        },
        /**
         * before submitting validate fields and recaptcha
         * if successful run the Marketo submit method
         */
        preSubmit(e) {
            e.preventDefault();

            function isRequired(value) {
                return value.validation && value.validation.required;
            }

            this.onRecaptchaVerify();

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
        /**
         * adds form data to Marketo payload and sets submitted status
         */
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
        /**
         * listens to recaptcha response to check if it's verified
         */

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
</style>

<docs>
    ```jsx
        // https://static.visitscotland.com/forms/vs-3331/simpleForm.json
        <VsContainer>
            <VsRow>
                <VsCol>
                    <VsForm
                        dataUrl="http://172.28.74.123:5555/simpleForm.json"
                        messagingUrl="http://172.28.74.123:5555/messaging.json"
                        recaptchaKey="6LfqqfcZAAAAACbkbPaHRZTIFpKZGAPZBDkwBKhe"
                        marketo-instance="//app-lon10.marketo.com"
                        munchkin-id="830-QYE-256"
                        language="en"
                        :is-prod="false"
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
                </VsCol>
            </VsRow>
        </VsContainer>
    ```
</docs>
