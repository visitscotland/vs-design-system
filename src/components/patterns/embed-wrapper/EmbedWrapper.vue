<template>
    <div
        data-test="vs-embed-wrapper"
        class="vs-embed-wrapper"
    >
        <VsContainer>
            <VsRow>
                <VsCol
                    cols="12"
                    sm="10"
                    offset-sm="1"
                    md="6"
                    offset-md="3"
                >
                    <VsRichTextWrapper
                        class="vs-module-wrapper__intro vs-embed-wrapper__intro"
                        v-if="!!$slots['embedIntroCopy']
                            && cookiesInitStatus !== 'error'
                            || noCookiesRequired"
                        data-test="vs-module-wrapper__intro"
                    >
                        <!-- @slot Slot to contain intro text -->
                        <slot name="embedIntroCopy" />
                    </VsRichTextWrapper>
                </VsCol>
                <VsCol
                    cols="12"
                >
                    <div
                        class="vs-embed-wrapper__container"
                        :class="requiredCookiesExist || noCookiesRequired? '' : 'd-none'"
                        key="embeddedContent"
                    >
                        <!--
                            @slot Takes the dom element for the embedded javascript widget, any
                            associated script tags can't be passed into the vue element and should
                            be loaded further down the page outside of the vue components
                        -->
                        <slot name="embedWidget" />
                    </div>
                    <div
                        v-if="showError"
                        key="fallback"
                    >
                        <VsWarning
                            :type="cookiesInitStatus === true ? 'cookie' : 'normal'"
                            theme="light"
                            data-test="vs-embed-wrapper__error"
                            class="vs-embed-wrapper__error"
                        >
                            {{ warningText }}

                            <template
                                v-if="!requiredCookiesExist
                                    && cookiesInitStatus === true"
                                slot="button-text"
                            >
                                <slot name="embedButtonText" />
                            </template>
                        </VsWarning>
                    </div>

                    <VsWarning
                        type="normal"
                        theme="light"
                        data-test="vs-embed-wrapper__error--no-js"
                        class="vs-embed-wrapper__error vs-embed-wrapper__error--no-js"
                    >
                        {{ noJsText }}
                    </VsWarning>
                </VsCol>
            </VsRow>
        </VsContainer>
    </div>
</template>

<script>
import {
    VsContainer, VsRow, VsCol,
} from '@components/elements/grid';
import VsRichTextWrapper from '@components/elements/rich-text-wrapper/RichTextWrapper';
import VsWarning from '@components/patterns/warning/Warning';
import verifyCookiesMixin from '../../../mixins/verifyCookiesMixin';
import requiredCookiesData from '../../../utils/required-cookies-data';

const cookieValues = requiredCookiesData.embed;

/**
 * This component acts as a wrapper for an embedded javascript widget and provides
 * no-js and no-cookies functionality for that widget
 *
 * @displayName Embed Wrapper
 */
export default {
    name: 'VsEmbedWrapper',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsContainer,
        VsRow,
        VsCol,
        VsRichTextWrapper,
        VsWarning,
    },
    mixins: [
        verifyCookiesMixin,
    ],
    props: {
        /**
         * Set to true if the embedded code does not require a cookie check
         */
        noCookiesRequired: {
            type: Boolean,
            default: false,
        },
        /**
         * Text to display when cookies have been rejected
         */
        noCookieText: {
            type: String,
            required: true,
        },
        /**
         * Text to display when there is an error displaying the content
         */
        errorText: {
            type: String,
            required: true,
        },
        /**
         * Text to display when javascript has been turned off
         */
        noJsText: {
            type: String,
            required: true,
        },
    },
    computed: {
        showError() {
            if (this.noCookiesRequired) {
                return false;
            };

            if ((!this.requiredCookiesExist
                && this.cookiesInitStatus === true)
                || this.cookiesInitStatus === 'error') {
                return true;
            }

            return false;
        },
        warningText() {
            let text = '';

            if (this.cookiesInitStatus === 'error') {
                text = this.errorText;
            }

            if (!this.requiredCookiesExist
                && this.cookiesInitStatus === true) {
                text = this.noCookieText;
            }

            return text;
        },
        requiredCookies() {
            if (this.noCookiesRequired) {
                return null;
            }
            return cookieValues;
        },
    },
};
</script>

<style lang="scss">
    .vs-embed-wrapper {
        &__error-image {
            max-width: 100%;
        }

        .vs-embed-wrapper__no-js, .vs-embed-wrapper__no-cookies {
            display: none;
        }

        &__error--no-js {
            display: none;
        }
    }

    @include no-js {
        .vs-embed-wrapper {
            &__container {
                display: none;
            }

            &__intro {
                display: none;
            }

            &__error {
                display: none;

                &--no-js {
                    display: block;
                }
            }
        }
    }
</style>
