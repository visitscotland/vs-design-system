<template>
    <VsContainer
        class="vs-carbon-calculator"
        data-test="vs-carbon-calculator"
    >
        <VsRow
            class="vs-carbon-calculator__survey"
            v-if="formData && formData.fields"
        >
            <VsCol>
                <form
                    @submit.prevent="preSubmit"
                >
                    <fieldset>
                        <legend
                            class="vs-form__main-heading vs-heading--style-level-2"
                            data-test="vs-form__main-heading"
                        >
                            {{ getTranslatedContent('heading') }}
                        </legend>
                        <p>
                            {{ getTranslatedContent('intro') }}
                        </p>
                        <p
                            class="vs-carbon-calculator__progress-label"
                            v-if="currentQuestion"
                        >
                            {{ currentQuestion ? currentQuestion.stage : 4 }} of 4
                        </p>
                        <BProgress
                            max="4"
                        >
                            <BProgressBar
                                :value="currentQuestion ? currentQuestion.stage - 1 : 4"
                                :animated="true"
                            />
                        </BProgress>

                        <div
                            v-show="activeQuestion <= formData.fields.length"
                        >
                            <BFormGroup
                                v-for="(field, index) in formData.fields"
                                v-show="(index + 1) === activeQuestion"
                                :key="field.name"
                                :label="
                                    needsLabel(field) ?
                                        getTranslatedLabel(field.name, index)
                                        : ''
                                "
                                :label-for="needsLabel(field) ? field.name : ''"
                                :class="conditionalElementClass(field.name)"
                            >
                                <div
                                    :class="conditionalElementClass(field.name)"
                                >
                                    <template v-if="field.element === 'radio'">
                                        <VsHeading
                                            level="3"
                                        >
                                            {{ currentCategoryName }}
                                        </VsHeading>

                                        <!-- eslint-disable -->
                                        <label
                                            class="vs-carbon-calculator__question"
                                            :for="field.name"
                                        >
                                            {{ getTranslatedLabel(field, index) }}
                                        </label>
                                        <!-- eslint-enable -->

                                        <BFormRadioGroup
                                            :id="field.name"
                                        >
                                            <BFormRadio
                                                v-for="
                                                    (option, optionIndex) in
                                                        getTranslatedOptions(field.name, index)
                                                "
                                                :key="optionIndex"
                                                :value="option.value"
                                                :hint-text="getTranslatedHint(field.name, index)"
                                                :name="field.name + option.value"
                                                :id="field.name + option.value"
                                                @change="updateFieldData({
                                                    field: field.name,
                                                    value: option.value,
                                                })"
                                                class="vs-carbon-calculator__radio"
                                            >
                                                <div class="vs-carbon-calculator__radio-icon">
                                                    <VsIcon
                                                        :name="option.icon"
                                                        size="xl"
                                                    />
                                                </div>
                                                {{ option.text }}
                                            </BFormRadio>
                                        </BFormRadioGroup>
                                    </template>
                                </div>
                            </BFormGroup>
                        </div>
                    </fieldset>
                </form>
                <VsCarbonFormTip
                    v-if="currentTip"
                    :tip="currentTip.text"
                />
            </VsCol>
            <VsCol
                cols="12"
            >
                <VsCarbonFormRunningTotal
                    v-if="activeQuestion <= formData.fields.length"
                    :total-tons="totalTons"
                />
                <VsCarbonFormResults
                    v-if="activeQuestion > formData.fields.length"
                    :title="currentCategoryName"
                    :total-tons="totalTons"
                    :transport-tons="transportTons"
                    :food-tons="foodTons"
                    :transport-tip="transportTip"
                    :food-tip="foodTip"
                />
            </VsCol>
            <VsCol
                cols="12"
            >
                <VsButton
                    variant="primary"
                    type="submit"
                    class="vs-form__submit mt-9 float-left"
                    v-if="activeQuestion > 1"
                    @click.native="backwardPage()"
                >
                    Previous
                </VsButton>

                <VsButton
                    variant="primary"
                    type="submit"
                    class="vs-form__submit mt-9 float-right"
                    v-if="activeQuestion < formData.fields.length"
                    :disabled="!answerSet"
                    @click.native="forwardPage()"
                >
                    Next
                </VsButton>

                <VsButton
                    variant="primary"
                    type="submit"
                    class="vs-form__submit mt-9 float-right"
                    v-if="activeQuestion === formData.fields.length"
                    :disabled="!answerSet"
                    @click.native="forwardPage()"
                >
                    Results
                </VsButton>
            </VsCol>
        </VsRow>
    </VsContainer>
</template>

<script>
import Vue from 'vue';
import {
    BFormGroup,
    BFormRadioGroup,
    BFormRadio,
    BProgress,
    BProgressBar,
} from 'bootstrap-vue';
import {
    VsContainer, VsCol, VsRow,
} from '@components/elements/grid';
import VsButton from '../../elements/button/Button';
import VsCarbonFormResults from './components/CarbonFormResults';
import VsCarbonFormTip from './components/CarbonFormTip';
import VsCarbonFormRunningTotal from './components/CarbonFormRunningTotal';

const axios = require('axios');

/**
 * @displayName Carbon Calculator Form
 */

export default {
    name: 'VsCarbonForm',
    status: 'prototype',
    release: '0.0.1',
    components: {
        BFormGroup,
        BFormRadioGroup,
        BFormRadio,
        BProgress,
        BProgressBar,
        VsButton,
        VsContainer,
        VsCol,
        VsRow,
        VsCarbonFormResults,
        VsCarbonFormTip,
        VsCarbonFormRunningTotal,
    },
    props: {
        /**
         * The URL for the form data file
         */
        dataUrl: {
            type: String,
            required: true,
        },
        /**
         * URL for generic messaging config
         */
        messagingUrl: {
            type: String,
            required: true,
        },
        /**
         * URL for generic messaging config
         */
        countryListUrl: {
            type: String,
            default: '',
        },
        /**
         * Language indicator for content
         */
        language: {
            type: String,
            default: 'en',
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
            conditionalFields: {
            },
            inputVal: '',
            reAlertErrors: false,
            totalTons: 0,
            transportTons: 0,
            foodTons: 0,
            accomodationTons: 0,
            experiencesTons: 0,
            transportTip: null,
            foodTip: null,
            accomodationTip: null,
            experiencesTip: null,
            activeQuestion: 1,
            answerSet: false,
        };
    },
    computed: {
        showFormHeading() {
            if (!this.isUndefined(this.getTranslatedContent)
                && !this.isUndefined(this.getTranslatedContent('heading'))) {
                return true;
            }

            return false;
        },
        currentQuestion() {
            if (!this.formData) {
                return null;
            }

            return this.formData.fields[this.activeQuestion - 1];
        },
        currentTip() {
            let tip = null;

            if (this.formData.fields[this.activeQuestion - 1]) {
                switch (this.formData.fields[this.activeQuestion - 1].category) {
                case ('transport'):
                    tip = this.transportTip;
                    break;
                case ('accomodation'):
                    tip = this.accomodationTip;
                    break;
                case ('experiences'):
                    tip = this.experiencesTip;
                    break;
                case ('food'):
                    tip = this.foodTip;
                    break;
                default:
                    tip = null;
                    break;
                }
            }

            return tip;
        },
        currentCategoryName() {
            let name = 'Results';

            if (this.formData.fields[this.activeQuestion - 1]) {
                switch (this.formData.fields[this.activeQuestion - 1].category) {
                case ('transport'):
                    name = 'Transport';
                    break;
                case ('accomodation'):
                    name = 'Accomodation';
                    break;
                case ('experiences'):
                    name = 'Experiences';
                    break;
                case ('food'):
                    name = 'Food and Drink';
                    break;
                default:
                    name = null;
                    break;
                }
            }

            return name;
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

                    response.data.fields.forEach((field) => {
                        // create a data entry for each field
                        this.form[field.name] = '';

                        // ensure that hidden fields don't show on load
                        if (field.conditional) {
                            // Vue.set needed here to ensure reactivity of
                            // elements added to the object.
                            Vue.set(this.conditionalFields, field.name, false);
                        }
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

            if (this.showOptionalText(this.formData.fields[index])
                && !this.isUndefined(this.getMessagingData('optional', this.language))) {
                labelText = `${labelText} (${this.getMessagingData('optional', this.language)})`;
            }

            return labelText;
        },
        /**
         * get translated explanation if available
         */
        getTranslatedExplanation(fieldName, index) {
            const languageObj = this.getLanguageObj();
            let explanationText = '';

            if (this.language !== 'en'
                && !this.isUndefined(languageObj[fieldName])
                && !this.isUndefined(languageObj[fieldName].explanation)
            ) {
                explanationText = languageObj[fieldName].explanation;
            } else {
                explanationText = this.formData.fields[index].explanation;
            }

            if (this.showOptionalText(this.formData.fields[index])
                && !this.isUndefined(this.getMessagingData('optional', this.language))) {
                explanationText = `${explanationText} (${this.getMessagingData('optional', this.language)})`;
            }

            return explanationText;
        },
        /**
         * get translated label if available
         */
        getTranslatedLegend(fieldName, index) {
            const languageObj = this.getLanguageObj();
            let legendText = '';

            if (this.language !== 'en'
                && !this.isUndefined(languageObj[fieldName])
                && !this.isUndefined(languageObj[fieldName].descriptor)
            ) {
                legendText = languageObj[fieldName].descriptor;
            } else {
                legendText = this.formData.fields[index].descriptor;
            }

            return legendText;
        },
        /*
         * get translated info content if available
         */
        getTranslatedInfo(fieldName, index) {
            const languageObj = this.getLanguageObj();
            let infoText = '';

            if (this.language !== 'en'
                && !this.isUndefined(languageObj[fieldName])
                && !this.isUndefined(languageObj[fieldName].info)
            ) {
                infoText = languageObj[fieldName].info;
            } else {
                infoText = this.formData.fields[index].info;
            }

            return infoText;
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
         * get language appriopriate options for a select element
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

            if (typeof optionsArr === 'undefined') {
                optionsArr = [];
            }

            return optionsArr;
        },
        getTranslatedHint(fieldName, index) {
            const languageObj = this.getLanguageObj();
            let hintText = '';

            if (this.language !== 'en'
                && !this.isUndefined(languageObj[fieldName])
                && !this.isUndefined(languageObj[fieldName].hint)) {
                hintText = languageObj[fieldName].hint;
            } else if (!this.isUndefined(this.formData.fields[index].hint)) {
                hintText = this.formData.fields[index].hint;
            } else {
                hintText = '';
            }

            return hintText;
        },
        getTranslatedContent(type) {
            let text;
            const languageObj = this.getLanguageObj();

            if (this.language === 'en'
                && !this.isUndefined(this.formData.content)
                && !this.isUndefined(this.formData.content[type])
            ) {
                text = this.formData.content[type];
            } else if (!this.isUndefined(languageObj.content)
                && !this.isUndefined(languageObj.content[type])) {
                text = languageObj.content[type];
            } else {
                text = this.getMessagingData(type, this.language);
            }

            return text;
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

            if (data.value) {
                this.answerSet = true;
            } else {
                this.answerSet = false;
            }

            this.manageErrorStatus(data.field, data.errors);
            this.calculate();
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
        showOptionalText(field) {
            if (this.isUndefined(field.validation) || this.isUndefined(field.validation.required)) {
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
                || field.element === 'checkbox') {
                return false;
            }

            return true;
        },
        getFieldValue(fieldName, key) {
            if (!key) {
                return 0;
            }

            let field;

            for (let x = 0; x < this.formData.fields.length; x++) {
                if (this.formData.fields[x].name === fieldName) {
                    field = this.formData.fields[x];
                }
            }

            return field.values[key];
        },
        getTips(fieldName, key) {
            let field;

            for (let x = 0; x < this.formData.fields.length; x++) {
                if (this.formData.fields[x].name === fieldName) {
                    field = this.formData.fields[x];
                }
            }

            if (field.tips && field.tips[key]) {
                return (field.tips[key]);
            }

            return [];
        },
        /**
         * before calculating validate fields
         */
        preSubmit(e) {
            e.preventDefault();

            this.submitted = false;
            this.formIsInvalid = false;

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

            // check conditional fields - if they're not shown
            // then clear any value they may have
            Object.entries(this.conditionalFields).forEach(([key, value]) => {
                if (!value) {
                    this.form[key] = '';
                }
            });

            if (this.errorFields.length > 0) {
                this.formIsInvalid = true;
            }

            if (!this.formIsInvalid) {
                this.calculate();
            } else {
                this.showErrorMessage = true;
                this.reAlertErrors = true;

                setTimeout(() => {
                    this.reAlertErrors = false;
                }, 100);
            }
        },
        /**
         * TODO
         */
        calculate() {
            this.transportTons = 0;
            this.transportTip = null;
            let transportTips = [];

            this.foodTons = 0;
            this.foodTip = null;
            let foodTips = [];

            for (let x = 0; x < this.formData.fields.length; x++) {
                const currentField = this.formData.fields[x];

                switch (currentField.category) {
                case 'transport':
                    this.transportTons += this.getFieldValue(
                        currentField.name,
                        this.form[currentField.name]
                    );
                    transportTips = transportTips.concat(
                        this.getTips(currentField.name, this.form[currentField.name])
                    );
                    break;
                case 'food':
                    this.foodTons += this.getFieldValue(
                        currentField.name,
                        this.form[currentField.name]
                    );
                    foodTips = foodTips.concat(
                        this.getTips(currentField.name, this.form[currentField.name])
                    );
                    break;
                default:
                    break;
                }
            }

            this.totalTons = this.transportTons + this.foodTons;

            this.transportTip = transportTips[Math.floor(Math.random() * transportTips.length)];
            this.foodTip = foodTips[Math.floor(Math.random() * foodTips.length)];

            this.submitted = true;
        },
        /**
         * checks whether conditional fields meet the rules to show them
         */
        checkConditionalFields() {
            Object.keys(this.conditionalFields).forEach((field) => {
                // match the field to the form data
                const fieldData = this.formData.fields.find((o) => o.name === field);
                let showField = true;

                // iterate through rules in object
                Object.keys(fieldData.conditional).forEach((rule) => {
                    const conditions = fieldData.conditional[rule];
                    if (Array.isArray(conditions)) {
                        // if the rule is an array of values
                        // set the value to false if the field value isn't in the array
                        if (conditions.indexOf(this.form[rule]) === -1) {
                            showField = false;
                        }
                    } else if (this.form[rule] !== conditions) {
                        // if the rule is a string just check the field value
                        // against the string
                        showField = false;
                    }

                    if (showField) {
                        this.conditionalFields[field] = true;
                    } else {
                        this.conditionalFields[field] = false;
                        this.manageErrorStatus(field, []);
                    }
                });
            });
        },
        forwardPage() {
            this.activeQuestion += 1;
            this.checkNewAnswerSet();
        },
        backwardPage() {
            this.activeQuestion -= 1;
            this.checkNewAnswerSet();
        },
        checkNewAnswerSet() {
            if (this.activeQuestion > this.formData.fields.length) {
                this.answerSet = true;
                return;
            }

            const newQuestionKey = this.formData.fields[this.activeQuestion - 1].name;
            if (!this.form[newQuestionKey]) {
                this.answerSet = false;
            } else {
                this.answerSet = true;
            }
        },
        /**
         * return the correct class to show or hide
         * conditional elements
         */
        conditionalElementClass(fieldName) {
            return this.conditionalFields[fieldName] === true
                || typeof this.conditionalFields[fieldName] === 'undefined'
                ? '' : 'd-none';
        },
    },
};
</script>

<style lang='scss'>
    .vs-form {
        &__main-heading {
            @extend %heading-default-styles;
        }

        &__content {
            font-size: $font-size-6;
        }
        label {
            font-weight: $font-weight-semi-bold;
            margin-bottom: 0;
        }

        .error {
            font-size: $font-size-body;
            color: $color-theme-danger;
        }

        .hint-text {
            font-size: $font-size-body;
            color: $color-gray-shade-1;
            margin-bottom: 0;
        }

        .form-group {
            margin-bottom: $spacer-6;
        }

        &__no-js {
            display: none;
        }
    }

    .vs-carbon-calculator__question {
        width: 100%;
    }

    .vs-carbon-calculator__radio {
        display: inline-block;
        vertical-align: top;
        width: 50%;
        text-align: center;
        padding: $spacer-4 $spacer-2;
        cursor: pointer;

        * {
            cursor: pointer;
        }

        .vs-icon {
            width: 100%;
        }

        label {
            width: 100%;
        }

        .vs-carbon-calculator__radio-icon {
            aspect-ratio: 1/1;
            width: 100%;
            display: flex;
            align-items: center;
            justify-content: center;
            background-color: $color-gray-tint-7;
            margin-bottom: $spacer-3;
        }

        input {
            display: none;
        }

        input:checked ~ label {
            .vs-carbon-calculator__radio-icon {
                background-color: $color-pink;

                .vs-icon {
                    color: $color-white;
                }
            }
        }
    }

    @include media-breakpoint-up(md) {
        .vs-carbon-calculator__radio {
            width: calc(100% / 3);
        }
    }

    @include media-breakpoint-up(lg) {
        .vs-carbon-calculator__radio {
            width: 25%;
        }
    }

    .vs-carbon-calculator {
        .vs-carbon-calculator__progress-label {
            margin-bottom: $spacer-2;
            text-align: right;
            font-size: $font-size-2;
            color: $color-gray-tint-3;
        }

        .progress {
            width: 100%;
            margin: $spacer-2 $spacer-0 $spacer-4;
            background: $color-gray-tint-7;
            border-radius: $spacer-2;
            overflow: hidden;
        }

        .progress-bar {
            height: $spacer-4;
            background-color: $color-theme-secondary-teal;
            transition: width ease-out .5s;
        }
    }

    @include no-js {
        .vs-form {

            & > form {
                display: none;
            }

            &__no-js {
                display: block;
            }
        }
    }
</style>
