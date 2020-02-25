<template>
    <vs-prototype>
        <vs-icon name="share" variant="dark" size="sm" />
    </vs-prototype>
</template>

<script>
import VsIcon from "@components/elements/icon/Icon.vue"
import VsPrototype from "@components/elements/prototype/Prototype.vue"

/**
 * This is a placeholder component for now.
 * 
 * This component will allow users to share content to social media channels. Functionalities and styles still need to be coded.
 */
export default {
    name: "VsSocialShare",
    status: "prototype",
    release: "0.0.1",

    components: {
        VsIcon,
        VsPrototype
    }
}
</script>

<docs>
```jsx
    <bs-wrapper class="d-flex mb-4">
        <vs-social-share />
    </bs-wrapper>
```
</docs>