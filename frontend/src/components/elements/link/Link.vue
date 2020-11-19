<template>
    <BLink
        class="vs-link"
        :class="[
            `vs-link--variant-${variant}`,
            { 'vs-link--external': external }
        ]"
        :href="href"
        :target="type === 'external' ? '_blank' : '_self'"
        :download="type === 'download'"
    >
        <slot />
        <VsIcon
            v-if="type !== null"
            :name="`${type}-link`"
            variant="primary"
            :size="iconSize"
            class="ml-1 vs-link__icon"
        />
    </BLink>
</template>

<script>
import { BLink } from 'bootstrap-vue';
import VsIcon from '@components/elements/icon/Icon';

/**
 * Links are used to point the user to locations in the
 * same page, documents, emails, or any kind of URL.
 *
 * External links will show an external icon.
 */

export default {
    name: 'VsLink',
    status: 'prototype',
    release: '0.0.2',
    components: {
        BLink,
        VsIcon,
    },
    props: {
        /**
         * The URL the link will point to
         */
        href: {
            type: String,
            default: null,
        },
        /**
         * Option to create link type which defines icon and whether it opens in a new tab
         * `external, internal, download`
         */
        type: {
            type: String,
            default: null,
            validator: (value) => value.match(/(external|internal|download)/),
        },
        /**
         * Option to choose a pre-defined style variant
         * `primary, dark`
         */
        variant: {
            type: String,
            default: 'primary',
            validator: (value) => value.match(/(primary|dark)/),
        },
        /**
        * Size of icon - defaults to 'xs'
        * `xxs, xs, sm, md, lg, xl`)
        */
        iconSize: {
            type: String,
            default: 'xs',
            validator: (value) => value.match(/(xxs|xs|sm|md|lg|xl)/),
        },
    },
    computed: {
        variantClass() {
            return `vs-link--variant-${this.variant}`;
        },
    },
};
</script>

<style lang="scss">
.vs-link {
    &.vs-link--variant-primary {
        color: $color_pink;

        &:focus {
            outline: 2px solid $color_pink;
        }
    }

    &.vs-link--variant-dark {
        color: $color_yellow;

        .vs-icon {
            fill: $color_yellow;
        }

        &:focus {
            outline: 2px solid $color_yellow;
        }
    }

    .vs-link__icon {
        vertical-align: baseline;
    }
}
</style>

<docs>
    ```jsx
        <bs-wrapper class="d-flex flex-wrap mb-4">
            <vs-link class="mb-2" href="#foo">
                Link
            </vs-link>
            <vs-link variant="dark" class="ml-6 mb-2" href="#foo">
                Dark Link
            </vs-link>
        </bs-wrapper>

        <VsHeading level="3">External links</VsHeading>
        <bs-wrapper class="d-flex flex-wrap mb-4">
            <vs-link class="mb-2" type="external" href="https://www.visitscotland.com">
                External Link
            </vs-link>
            <vs-link variant="dark" type="external" class="ml-6 mb-2" href="#foo">
                External Dark Link
            </vs-link>
            <vs-heading level="4" class="ml-6 mb-2">
                <vs-link href="https://www.visitscotland.com" type="external">
                    External Heading link
                </vs-link>
            </vs-heading>
        </bs-wrapper>

        <VsHeading level="3">Download links</VsHeading>
        <bs-wrapper class="d-flex flex-wrap mb-4">
            <vs-link class="mb-2" type="download" href="https://www.visitscotland.com">
                Download Link
            </vs-link>

            <vs-link variant="dark" type="download" class="ml-6 mb-2" href="#foo">
                Download Dark Link
            </vs-link>

            <vs-heading level="4" class="ml-6 mb-2">
                <vs-link href="https://www.visitscotland.com" type="download">
                    Download Heading link
                </vs-link>
            </vs-heading>
        </bs-wrapper>

        <VsHeading level="3">Internal links</VsHeading>
        <bs-wrapper class="d-flex flex-wrap mb-4">
            <vs-link class="mb-2" type="internal" href="https://www.visitscotland.com">
                Internal Link
            </vs-link>

            <vs-link variant="dark" type="internal" class="ml-6 mb-2" href="#foo">
                Internal Dark Link
            </vs-link>

            <vs-heading level="4" class="ml-6 mb-2">
                <vs-link href="https://www.visitscotland.com" type="internal">
                    Internal Heading link
                </vs-link>
            </vs-heading>
        </bs-wrapper>

        <bs-wrapper class="d-flex flex-wrap mb-4">
            <h3>Normal Paragraph (16px)</h3>
            <p>
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus
                 efficitur pretium ultricies. Phasellus placerat tellus gravida,
                 laoreet velit vitae, interdum ante. Praesent quis urna
                 <vs-link href="#foo">Link</vs-link> finibus, efficitur arcu vel,
                 fermentum dui. Praesent laoreet leo nonfelis porttitor, eu tempor
                 tellus lobortis. Nunc consectetur ornare laoreet. Curabitur
                 <vs-link type="external" href="https://www.visitscotland.com">External Link</vs-link>
                 ut sagittis quam. Praesent in quam ornare, ultricies odio vitae, aliquam nisl.
                 Curabitur et elit facilisis, egestas felis sed, dignissim eros. Curabitur
                 aliquam et lorem et accumsan. Fusce rutrum vel ex in posuere. Ut luctus
                 odio eu leo rutrum vestibulum.
            </p>
        </bs-wrapper>

        <bs-wrapper
            class="d-flex flex-wrap mb-4 p-6"
            style="background-color: #191919; color: white"
        >
            <h3>Lead Paragraph (18px)</h3>
            <VsLeadParagraph>
                Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                 Vivamus efficitur pretium ultricies. Phasellus placerat tellus
                 gravida, laoreet velit vitae, interdum ante. Praesent quis urna finibus,
                 efficitur arcu vel, fermentum dui. Praesent laoreet leo non
                 felis porttitor, eu tempor tellus lobortis. Nunc consectetur ornare laoreet.
                Praesent in quam ornare, ultricies odio vitae, <vs-link variant="dark" href="#foo">
                Dark Link</vs-link> aliquam nisl. Curabitur et elit facilisis,
                egestas felis sed, dignissim eros. Curabitur aliquam et lorem et
                accumsan. <vs-link variant="dark" type="external" href="#foo">External
                Dark Link</vs-link> Fusce rutrum vel ex in posuere. Ut luctus odio eu leo
                rutrum vestibulum.
                Nulla tortor ligula, consequat ac feugiat et, porta id eros.
            </VsLeadParagraph>
        </bs-wrapper>
    ```
</docs>
