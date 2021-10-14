<template>
    <div class="vs-psr-module">
        <VsContainer>
            <VsRow no-gutters>
                <VsCol
                    class="vs-psr-module__col-yellow"
                    md="8"
                >
                    test 1
                </VsCol>
                <VsCol
                    class="vs-psr-module__col-grey"
                    md="4"
                >
                    test 2
                </VsCol>
            </VsRow>
        </VsContainer>
    </div>
</template>

<script>
import {
    VsContainer,
    VsRow,
    VsCol,
} from '@components/elements/layout';

/**
 * A module housing the PSR widget, a title and optional text
 *
 * @displayName PSR Module
 */

export default {
    name: 'VsPsrModule',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsContainer,
        VsRow,
        VsCol,
    },
};
</script>

<style lang="scss">
    .vs-psr-module {
        &__col-yellow {
            background: yellow;
        }

        &__col-grey {
            background: gray;
        }
    }
</style>

<docs>
```jsx
    <VsContainer>
        <VsRow>
            <vs-psr-module />
        </VsRow>
    </VsContainer>
```
</docs>
