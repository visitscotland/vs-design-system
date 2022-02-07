<template>
    <div class="vs-form">
        <!-- element into which the (completely empty) form is embedded invisibly -->
        <form
            id="mktoForm_90"
            style="display:none"
        />

        <form v-if="!submitted">
            <template v-for="field in formData.fields">
                <label
                    v-if="field.element === 'input'"
                    for="field.name"
                    :key="field.name"
                >
                    {{ field.label }}
                    <VsFormInput
                        :type="field.type"
                        :label="field.label"
                        size="md"
                    />
                </label>
            </template>
            <button
                @click.stop="marketoSubmit"
                @keydown.stop="marketoSubmit"
                type="button"
                class="formSubmit"
            >
                Submit
            </button>
        </form>

        <p v-if="submitting">
            We're just submitting your form
        </p>

        <p v-if="submitted">
            Thank you for your details, your form has been submitted
        </p>
    </div>
</template>

<script>
import VsFormInput from '../../elements/form-input/FormInput';

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
    },
    props: {
        formId: {
            type: Number,
            default: 90,
        },
        dataUrl: {
            type: String,
            default: './data/simpleForm.json',
        },
    },
    data() {
        return {
            firstname: '',
            lastname: '',
            email: '',
            submitted: false,
            submitting: false,
            formData: {
            },
        };
    },
    created() {
        axios.get('http://172.28.74.161:5050/simpleForm.json')
            .then((response) => {
                console.log(response.data);
                this.formData = response.data;
            });
    },
    mounted() {
        // window.MktoForms2.loadForm('//e.visitscotland.com', '638-HHZ-510', this.formId);
        window.MktoForms2.loadForm('//app-lon10.marketo.com', '830-QYE-256', 90);
    },
    methods: {
        marketoSubmit(event) {
            const firstName = this.firstname;
            const lastName = this.lastname;
            const emailAddress = this.email;

            const data = {
                Email: emailAddress,
                FirstName: firstName,
                LastName: lastName,
            };

            console.log(event);

            const myForm = window.MktoForms2.allForms()[0];
            myForm.addHiddenFields(data);
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
            :formId = 123
        />
    ```
</docs>
