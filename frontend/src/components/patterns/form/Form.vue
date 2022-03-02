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
                    :for="field.name"
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
                :invalid="!recaptchaVerified && showErrorMessage"
            />

            <input
                type="submit"
                value="Submit"
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
            default: 'Submit',
        },
        /**
         * form ID
         */
        formId: {
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
            recaptchaVerified: false,
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
        window.MktoForms2.loadForm('//app-lon10.marketo.com', '830-QYE-256', this.formId);
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
        preSubmit(e) {
            e.preventDefault();
            console.log(window.grecaptcha);

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
            console.log(window.grecaptcha.getResponse());
            const myForm = window.MktoForms2.allForms()[0];
            myForm.addHiddenFields(this.form);
            myForm.addHiddenFields({
                lastReCAPTCHAUserFingerprint: window.grecaptcha.getResponse(),
                lastRecaptchaEnabledFormID: this.formId,
            });

            console.log(myForm.vals());

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
            dataUrl="https://static.visitscotland.com/forms/vs-3331/simpleForm.json"
            recaptchaKey="6LfqqfcZAAAAACbkbPaHRZTIFpKZGAPZBDkwBKhe"
            formId="90"
            submitText="Submit the form"
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
