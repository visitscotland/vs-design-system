<template>
    <section class="vs-form">
        <div
            class="vs-form__form"
            v-if="!showSuccessMessage"
        >
            <form
                :id="`mktoForm_${formId}`"
            />

            <div
                class="g-recaptcha"
                data-sitekey="6LfqqfcZAAAAACbkbPaHRZTIFpKZGAPZBDkwBKhe"
                data-size=""
            />
        </div>

        <div
            class="vs-form__success-msg"
            v-if="showSuccessMessage"
        >
            <slot name="success-text" />
        </div>
    </section>
</template>

<script>
/**
 * A form embedded from Marketo
 */

export default {
    name: 'VsIconList',
    status: 'prototype',
    release: '0.0.1',
    props: {
        formId: {
            type: Number,
            required: true,
        },
    },
    data() {
        return {
            showSuccessMessage: false,
        };
    },
    mounted() {
        window.MktoForms2.loadForm('//e.visitscotland.com', '830-QYE-256', this.formId);

        window.MktoForms2.whenReady((form) => {
            const formEl = form.getFormElem()[0];
            const submitContainer = formEl.querySelector('.mktoButtonRow');
            const recaptchaEl = document.querySelector('.g-recaptcha');
            const formElId = form.getId();
            const { grecaptcha } = window;

            form.submittable(false); // force resize reCAPTCHA frame
            recaptchaEl.querySelector('iframe').setAttribute('height', '140'); // move reCAPTCHA inside form container

            formEl.insertBefore(recaptchaEl, submitContainer);

            form.onValidate((builtInValidation) => {
                // code to handle the recaptcha
                if (!builtInValidation) return;

                // calling the recaptcha
                const recaptchaResponse = grecaptcha.getResponse();
                if (!recaptchaResponse) {
                    recaptchaEl.classList.add('mktoInvalid');
                } else {
                    recaptchaEl.classList.remove('mktoInvalid');
                    form.addHiddenFields({
                        lastReCAPTCHAUserFingerprint: recaptchaResponse,
                        lastRecaptchaEnabledFormID: formElId,
                    });

                    form.submittable(true);
                }
            });
        });

        window.MktoForms2.whenRendered((form) => {
            this.destyleMktoForm(form);

            /* eslint-disable */
            const formEl = form.getFormElem()[0],
            arrayify = getSelection.call.bind([].slice);

            arrayify(formEl.querySelectorAll('SELECT'))
                .forEach(function(selectEl) {
                let currentOg;
                arrayify(selectEl.querySelectorAll('OPTION'))
                    .forEach(function(o, idx) {
                    if (o.value == '[OPTGROUP]') {
                        currentOg = document.createElement('OPTGROUP');
                        currentOg.label = o.textContent;
                        selectEl.appendChild(currentOg);
                        selectEl.removeChild(o);
                    } else {
                        if (currentOg) currentOg.appendChild(o);
                    }
                });
            });

            form.onSubmit(function(){
                form.submittable(false);
                // Get the form field values
                const vals = form.vals();
                const postcodeField = form.getFormElem().find("#PostalCode");
                const postcodeInputVal = form.getValues().PostalCode;
               
                const postcodeRegex = new RegExp('^(([A-Z][0-9]{1,2})|(([A-Z][A-HJ-Y][0-9]{1,2})|(([A-Z][0-9][A-Z])|([A-Z][A-HJ-Y][0-9]?[A-Z])))) ?[0-9][A-Z]{2}$', 'gi');

                if (postcodeInputVal.length > 0 && (postcodeRegex.test(postcodeInputVal))) {
                    console.log('submit')
                } else {
                    console.log('error');

                    form.showErrorMessage(
                        'Your postcode field is wrong',
                        postcodeField
                    )
                }
            });

            /* eslint-enable */

            form.onSuccess(() => {
                this.showSuccessMessage = true;
                return false;
            });
        });
    },
    methods: {
        destyleMktoForm: (mktoForm, moreStyles) => {
            /*
            * @author Sanford Whiteman
            * @version v1.104
            * @license MIT License: This license must appear with all
            * reproductions of this software.
            *
            * Create a completely barebones, user-styles-only Marketo form
            * by removing inline STYLE attributes and disabling STYLE and LINK elements
            */
            const formEl = mktoForm.getFormElem()[0];
            const arrayify = getSelection.call.bind([].slice);

            const styledEls = arrayify(formEl.querySelectorAll('[style]')).concat(formEl);

            styledEls.forEach((el) => {
                el.removeAttribute('style');
            });
            const styleSheets = arrayify(document.styleSheets);
            styleSheets.forEach((ss) => {
                if ([window.mktoForms2BaseStyle, window.mktoForms2ThemeStyle]
                    .indexOf(ss.ownerNode) !== -1 || formEl.contains(ss.ownerNode)) {
                    ss.disabled = true; // eslint-disable-line no-param-reassign
                }
            });

            if (!moreStyles) {
                formEl.setAttribute('data-styles-ready', 'true');
            }

            // move instructional text above field
            const instructionsEls = formEl.getElementsByClassName('mktoInstruction');

            instructionsEls.forEach((instruction) => {
                const instructionClone = instruction;
                const inputSibling = instruction.previousElementSibling;
                const parent = instruction.parentNode;
                parent.insertBefore(instructionClone, inputSibling);
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

    .g-recaptcha {
        visibility: hidden;
    }

    .mktoForm .g-recaptcha {
        visibility: visible;
    }

    .g-recaptcha > iframe {
        display: none !important;
    }

    .g-recaptcha.g-recaptcha {
        margin: 20px 0;
        max-height: 78px;
        overflow: hidden;
    }

    .g-recaptcha > div {
        // width: 168px !important;
        margin: 0 0 20px;
        overflow: hidden;
    }

    .g-recaptcha > div > div {
        // border-left: 5px solid transparent;
        // border-top: 5px solid transparent;
        // border-radius: 5px;
        transition: background-color 400ms ease-out;
    }

    .g-recaptcha.mktoInvalid > div > div {
        background-color: #F63C00;
        // transition: background-color 400ms ease-in;
    }

    .g-recaptcha-response {
        display: none !important;
    }
</style>
