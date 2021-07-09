<template>
    <div
        data-test="vs-stackla-wrapper"
        class="vs-stackla-wrapper"
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
                        class="vs-module-wrapper__intro vs-stackla-wrapper__intro"
                        v-if="!!this.$slots['stacklaIntroCopy']"
                        data-test="vs-module-wrapper__intro"
                    >
                        <!-- @slot Slot to contain intro text -->
                        <slot name="stacklaIntroCopy" />
                    </VsRichTextWrapper>
                    <VsRichTextWrapper
                        class="vs-module-wrapper__intro vs-stackla-wrapper__no-js"
                        v-if="!!this.$slots['stacklaIntroCopyNoJs']"
                        data-test="vs-module-wrapper__intro"
                    >
                        <!-- @slot Slot to contain intro text if js is disabled -->
                        <slot name="stacklaIntroCopyNoJs" />
                    </VsRichTextWrapper>
                    <VsRichTextWrapper
                        class="vs-module-wrapper__intro vs-stackla-wrapper__no-cookies"
                        v-if="!!this.$slots['stacklaIntroCopyNoCookies']"
                        data-test="vs-module-wrapper__intro"
                    >
                        <!-- @slot Slot to contain intro text if cookies aren't enabled -->
                        <slot name="stacklaIntroCopyNoCookies" />
                    </VsRichTextWrapper>
                </VsCol>
                <VsCol
                    cols="12"
                >
                    <div class="vs-stackla-wrapper__container">
                        <!--
                            @slot Takes the dom element for the stackla widget, the script should be
                            loaded separately further down the page as if it is passed into the vue
                            template it will not be executed
                        -->
                        <slot name="stacklaWidget" />
                    </div>
                    <div class="vs-stackla-wrapper__no-js">
                        <VsSvg
                            class="vs-stackla-wrapper__error-image"
                            path="no-js-coo"
                        />
                    </div>
                    <div class="vs-stackla-wrapper__no-cookies">
                        <VsSvg
                            class="vs-stackla-wrapper__error-image"
                            path="cookie-coo"
                        />
                    </div>
                </VsCol>
            </VsRow>
        </VsContainer>
    </div>
</template>

<script>
import VsContainer from '@components/elements/layout/Container';
import VsCol from '@components/elements/layout/Col';
import VsRow from '@components/elements/layout/Row';
import VsSvg from '@components/elements/svg/Svg';

/**
 * This component acts as a wrapper for a stackla widget embed and provides
 * no-js and no-cookies functionality for that widget
 *
 * @displayName Stackla Wrapper
 */
export default {
    name: 'VsStacklaWrapper',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsContainer,
        VsRow,
        VsCol,
        VsSvg,
    },
};
</script>

<style lang="scss">
    .vs-stackla-wrapper {
        &__no-js {
            display: none;
        }

        &__no-cookies {
            display: none;
        }

        &__error-image {
            max-width: 100%;
        }
    }

    .no-cookies {
        .vs-stackla-wrapper {
            &__container {
                display: none;
            }

            &__intro {
                display: none;
            }

            &__no-cookies {
                display: block;
            }
        }
    }

    @include no-js {
        .vs-stackla-wrapper {
            &__container {
                display: none;
            }

            &__intro {
                display: none;
            }

            &__no-cookies {
                display: none;
            }

            &__no-js {
                display: block;
            }
        }
    }
</style>

<docs>
```jsx
    <VsModuleWrapper>
        <template slot="vsModuleWrapperHeading">
            Your Pictures Of Scottish Castles
        </template>

        <VsStacklaWrapper>
            <template slot="stacklaIntroCopy">
                Share your snaps with us by using #ScottishCastle or #VisitScotland
            </template>

            <template slot="stacklaIntroCopyNoJs">
                <p>JavaScript needs to be enabled to see social media images for this place.
You can turn this on in your browser settings.</p>
            </template>

            <template slot="stacklaIntroCopyNoCookies">
                <p>Cookies are needed to see social media images from this place.</p>
                <p><a target="_blank" href="#">Update my cookie settings</a></p>
            </template>

            <template slot="stacklaWidget">
                Stackla Script Goes Here
            </template>
        </VsStacklaWrapper>
    </VsModuleWrapper>
```
</docs>
