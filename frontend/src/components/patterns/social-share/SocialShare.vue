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
            ref="popover"
        >
            <VsHeading
                thin
                level="3"
            >
                <slot name="shareHeading" />
            </VsHeading>

            <VsRow>
                <slot />
            </VsRow>

            <VsButton
                variant="transparent"
                class="vs-social-share__close-button"
                @click.native="onClose"
            >
                <span class="sr-only">
                    {{ closeAltText }}
                </span>
                <VsIcon
                    name="close"
                    variant="dark"
                    size="md"
                />
            </VsButton>
        </BPopover>
    </div>
</template>

<script>
import VsIcon from '@components/elements/icon/Icon';
import VsButton from '@components/elements/button/Button';
import VsHeading from '@components/elements/heading/Heading';
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
        VsHeading,
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
        /**
         * Accessiblity alt text for the close button
         */
        closeAltText: {
            type: String,
            required: true,
        },
    },
    methods: {
        onClose() {
            this.$refs.popover.$emit('close');
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
        width: 45px;
        height: 50px;
        padding: 0;
        letter-spacing: initial;
        text-decoration: underline;
        font-weight: $font-weight-normal;
        font-size: $small-font-size;
        line-height: $line_height_l;

        &:hover{
            color: $color-pink;
        }
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

    &__close-button.vs-button.btn{
        position: absolute;
        right: $spacer-4;
        top: $spacer-4;
        border: 0;
        padding: .25rem;

        &:hover{
            .vs-icon.vs-icon--variant-dark{
                fill: $color-pink;
            }
        }
    }
}
</style>

<docs>
```jsx
    <BsWrapper class="d-flex justify-content-end my-3 mx-3">
        <VsSocialShare
            page-url="http://www.visitscotland.com"
            page-title="VisitScotland - Scotland's National Tourist Organisation"
            close-alt-text="Close"
        >
            <template slot="shareHeading">
                Share On
            </template>

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
