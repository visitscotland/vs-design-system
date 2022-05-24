<template>
    <div
        class="vs-warning"
        data-test="vs-warning"
    >
        <VsIcon
            class="vs-warning__icon"
            name="review"
            custom-colour="#FCCA1B"
        />
        <p
            class="vs-warning__message"
        >
            {{ warningMessage }}
        </p>
        <VsLink
            v-if="warningLink && warningLink.url"
            data-test="vs-warning__link"
            :href="warningLink.url"
            variant="dark"
        >
            {{ warningLink.label }}
        </VsLink>
    </div>
</template>
<script>

import VsIcon from '@components/elements/icon';
import VsLink from '@components/elements/link';

/**
 * Test
 *
 * @displayName Warning
 */
export default {
    name: 'VsWarning',
    status: 'prototype',
    release: '0.1.0',
    components: {
        VsIcon,
        VsLink,
    },
    props: {
        /**
        * The warning message to display to the user
        */
        warningMessage: {
            type: String,
            required: true,
        },
        /**
        * An optional link to a page that the user can go to to try and
        * correct the issue
        */
        warningLink: {
            type: Object,
            default: null,
        },
    },
};
</script>

<style lang="scss">
    .vs-warning {
        padding: $spacer-4;
        width: 100%;
        height: 100%;
        display: flex;
        flex-direction: column;
        align-items: center;
        text-align: center;
        justify-content: center;
        background-color: rgba($color-black, 0.8);
        color: $color-white;
    }

    .vs-warning__icon {
        width: 4rem !important;
        height: 4rem !important;
        margin-bottom: 1rem;
    }

    .vs-warning__message {
        margin-bottom: $spacer-0;
    }
</style>
