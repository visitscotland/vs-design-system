<template>
    <div
        data-test="vs-stackla"
    >
        <VsContainer>
            <VsRow>
                <VsCol>
                    <!--
                        @slot Takes the dom element for the stackla widget, the script should be
                        loaded separately further down the page as if it is passed into the vue
                        template it will not be executed
                    -->
                    <slot name="stacklaWidget" />
                </VsCol>
            </VsRow>
        </VsContainer>
    </div>
</template>

<script>
import VsContainer from '@components/elements/layout/Container';
import VsCol from '@components/elements/layout/Col';
import VsRow from '@components/elements/layout/Row';

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
    },
};
</script>

<docs>
```jsx
    <VsModuleWrapper>
        <template slot="vsModuleWrapperHeading">
            Your Pictures Of Scottish Castles
        </template>

        <template slot="vsModuleWrapperIntro">
            Share your snaps with us by using #ScottishCastle or #VisitScotland
        </template>

        <VsStacklaWrapper>
            <template slot="stacklaWidget">
                Stackla Script Goes Here
            </template>
        </VsStacklaWrapper>
    </VsModuleWrapper>
```
</docs>
