<template>
    <div
        class="vs-social-share"
        data-test="vs-social-share"
    >
        <VsButton
            variant="transparent"
            :uppercase="false"
            class="vs-social-share__button"
            id="vs-social-share-popover"
        >
            <VsIcon
                name="share"
                variant="dark"
                size="md"
            />

            Share
        </VsButton>
        <BPopover
            custom-class="vs-social-share__popover"
            target="vs-social-share-popover"
            triggers="click"
            placement="leftbottom"
        >
            <VsHeading
                thin
                level="3"
            >
                Share on
            </VsHeading>
            <slot />
        </BPopover>
    </div>
</template>

<script>
import VsIcon from '@components/elements/icon/Icon';
import VsButton from '@components/elements/button/Button';
import { BPopover } from 'bootstrap-vue';

/**
 * This component allows users to share content to social
 * media channels.
 *
 * @displayName Social Share Button
 */
export default {
    name: 'VsSocialShare',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsIcon,
        VsButton,
        BPopover,
    },
};
</script>

<style lang="scss">
.vs-social-share{
    &__button.vs-button.btn{
        max-width: 65px;
        padding: $spacer-2 $spacer-2 $spacer-1;
        letter-spacing: initial;
        text-decoration: underline;
        font-weight: $font-weight-normal;
        font-size: $small-font-size;
        line-height: $line_height_l;
    }

    &__popover{
        max-width: 600px;
        text-align: center;
    }
}
</style>

<docs>
```jsx
    <BsWrapper class="d-flex justify-content-center mb-4">
        <VsSocialShare>
            Test
        </VsSocialShare>
    </BsWrapper>
```
</docs>
