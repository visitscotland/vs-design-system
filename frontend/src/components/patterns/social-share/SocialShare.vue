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

            <VsRow>
                <slot />
            </VsRow>
        </BPopover>
    </div>
</template>

<script>
import VsIcon from '@components/elements/icon/Icon';
import VsButton from '@components/elements/button/Button';
import { VsRow } from '@components/elements/layout';
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
        VsRow,
    },
    props: {
        /**
         * Page URL to share
         */
        pageUrl: {
            type: String,
            required: true,
        },
        /**
         * Page URL to share
         */
        pageTitle: {
            type: String,
            default: '',
        },
    },
    /**
     * Provides URL properties injected to child component SocialShareItem
     */
    provide() {
        return {
            pageUrl: this.pageUrl,
            pageTitle: this.pageTitle,
        };
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
        max-width: 800px;
        width: 800px;
        font-size: inherit;
        text-align: center;

        .popover-body{
            padding: $spacer-9 $spacer-12;

            h3.vs-heading{
                margin-bottom: $spacer-9;
            }
        }
    }
}
</style>

<docs>
```jsx
    <BsWrapper class="d-flex justify-content-end my-3 mx-3">
        <VsSocialShare page-url="http://www.visitscotland.com" page-title="VisitScotland - Scotland's National Tourist Organisation">
            <VsSocialShareItem
                name="facebook"
                link-text="Facebook"
            />
            <VsSocialShareItem
                name="pinterest"
                link-text="Pinterest"
            />
            <VsSocialShareItem
                name="whatsapp"
                link-text="WhatsApp"
            />
            <VsSocialShareItem
                name="twitter"
                link-text="Twitter"
            />
            <VsSocialShareItem
                name="email"
                link-text="Email"
            />
            <VsSocialShareItem
                name="link"
                link-text="Copy Link"
            />
        </VsSocialShare>
    </BsWrapper>
```
</docs>
