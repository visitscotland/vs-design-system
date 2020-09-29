<template>
    <VsPrototype>
        <VsIcon
            name="share"
            variant="dark"
            size="md"
        />
    </VsPrototype>
</template>

<script>
import VsIcon from '@components/elements/icon/Icon';
import VsPrototype from '@components/elements/prototype/Prototype';

/**
 * This is a placeholder component for now.
 *
 * This component will allow users to share content to social
 * media channels. Functionalities and styles still need to be coded.
 */
export default {
    name: 'VsSocialShare',
    status: 'prototype',
    release: '0.0.1',

    components: {
        VsIcon,
        VsPrototype,
    },
};
</script>

<docs>
```jsx
    <bs-wrapper class="d-flex mb-4">
        <vs-social-share />
    </bs-wrapper>
```
</docs>
