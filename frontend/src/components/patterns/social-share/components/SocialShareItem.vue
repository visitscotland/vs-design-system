<template>
    <VsCol
        cols="4"
        class="vs-social-share-item"
        data-test="vs-social-share-item"
    >
        <VsLink
            :href="shareUrl"
            class="vs-social-share-item__link"
            @click.native="copyToClipboard"
            :id="`vs-share-${name}`"
        >
            <VsIcon
                v-if="name !== 'whatsapp'"
                :name="name"
                :custom-colour="iconColour"
                variant="dark"
                size="xl"
                small-size="lg"
            />

            <VsSvg
                v-if="name === 'whatsapp'"
                class="svg-logo"
                :path="name"
            />
            {{ linkText }}
        </VsLink>

        <BTooltip
            :show.sync="show"
            triggers="manual"
            target="vs-share-link"
            placement="bottom"
        >
            Link copied!
        </BTooltip>
    </VsCol>
</template>

<script>
import VsLink from '@components/elements/link/Link';
import VsIcon from '@components/elements/icon/Icon';
import VsSvg from '@components/elements/svg/Svg';
import { VsCol } from '@components/elements/layout';
import { BTooltip } from 'bootstrap-vue';

/**
 * This component allows users to share content to social
 * media channels.
 *
 * @displayName Social Share Item
 */
export default {
    name: 'VsSocialShareItem',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsCol,
        VsLink,
        VsSvg,
        VsIcon,
        BTooltip,
    },
    props: {
        /**
        * Icon name to display
        */
        name: {
            type: String,
            required: true,
            validator: (value) => value.match(/(facebook|twitter|pinterest|link|email|whatsapp)/),
        },
        /**
        * Text to display within the link
        */
        linkText: {
            type: String,
            required: true,
        },
    },
    inject: {
        /**
        * Page URL to share - provided by parent SocialShare
        */
        pageUrl: {
            default: '',
        },
        /**
        * Page Title to share - provided by parent SocialShare
        */
        pageTitle: {
            default: '',
        },
    },
    data() {
        return {
            show: false,
        };
    },
    computed: {
        iconColour() {
            let colour = '';

            switch (this.name) {
            case 'facebook':
                colour = '#1877f2';
                break;
            case 'twitter':
                colour = '#08A0E9';
                break;
            case 'pinterest':
                colour = '#E60023';
                break;
            default:
                colour = '#000000';
            };
            return colour;
        },
        shareUrl() {
            let url = '';

            switch (this.name) {
            case 'facebook':
                url = `https://www.facebook.com/sharer/sharer.php?u=${this.pageUrl}&t=${this.pageTitle}`;
                break;
            case 'twitter':
                url = `https://twitter.com/intent/tweet?text=${this.pageTitle}&url=${this.pageUrl}`;
                break;
            case 'pinterest':
                url = `https://www.pinterest.com/pin/create/button/?url=${this.pageUrl}`;
                break;
            case 'email':
                url = `mailto:?body=${this.pageTitle}%20-%20${this.pageUrl}&subject=${this.pageTitle}`;
                break;
            case 'whatsapp':
                url = `https://wa.me/?text=${this.pageTitle}%20-%20${this.pageUrl}`;
                break;
            case 'link':
                url = '#';
                break;
            default:
                url = '#';
            };
            return url;
        },
    },
    methods: {
        copyToClipboard() {
            if (this.name === 'link') {
                // create hidden input to copy from
                const hiddenInput = document.createElement('textarea');
                hiddenInput.setAttribute('readonly', '');
                hiddenInput.style.position = 'absolute';
                hiddenInput.style.left = '-9999px';

                // set value and append to body
                hiddenInput.value = this.pageUrl;
                document.body.appendChild(hiddenInput);

                // copy contents and remove from DOM
                hiddenInput.select();
                document.execCommand('copy');
                document.body.removeChild(hiddenInput);

                // show success tooltip on button
                this.show = true;
                setTimeout(() => { this.show = false; }, 2000);
            }
        },
    },
};
</script>

<style lang="scss">
.vs-social-share-item{

    &__link.vs-link.vs-link--variant-primary{
        border: 1px solid $color-secondary-gray-tint-4;
        display: block;
        margin-bottom: $spacer-2;
        padding: $spacer-4 $spacer-2;

        @include media-breakpoint-up(md) {
            padding: $spacer-6;
            margin-bottom: $spacer-6;
        }

        &:focus, &:focus-visible{
            outline-offset: 0px;
            border: 1px solid $color-pink;
            outline: 3px solid rgba($color-pink, .3);
        }

        .vs-icon, .svg-logo{
            display: block;
            margin: 0 auto $spacer-3;
        }

        .svg-logo{
            height: 30px;
            width: 30px;

            @include media-breakpoint-up(md) {
                height: 40px;
                width: 40px;
            }
        }
    }

}
</style>

<docs>
```jsx
    <BsWrapper class="d-flex justify-content-end my-3 mx-3">
        <VsSocialShareItem
            name="twitter"
            link-text="Twitter"
        />
    </BsWrapper>
```
</docs>
