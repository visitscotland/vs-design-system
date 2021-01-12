<template>
    <section class="vs-form">
        <form
            v-if="!showSuccessMessage"
            class="vs-form__form"
            :id="`mktoForm_${formId}`"
        />

        <div
            class="vs-form__success-msg"
            v-if="showSuccessMessage"
        >
            Well done, the form was submitted
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
        window.MktoForms2.loadForm('//e.visitscotland.com', '638-HHZ-510', this.formId);
        window.MktoForms2.whenRendered((form) => {
            this.destyleMktoForm(form);

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
</style>
