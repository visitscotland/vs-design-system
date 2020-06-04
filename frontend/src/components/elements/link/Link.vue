<template>
    <BLink
        class="vs-link"
        :class="[variant, { 'vs-link--external': external }]"
        :href="href"
        :target="external ? '_blank' : '_self'"
    >
        <slot />
        <VsIcon
            v-if="external"
            name="external-link"
            variant="primary"
            size="xs"
            :padding="0"
            class="ml-1"
        />
    </BLink>
</template>

<script>
import { BLink } from "bootstrap-vue"
import VsIcon from "@components/elements/icon/Icon"

/**
 * Links are used to point the user to locations in the
 * same page, documents, emails, or any kind of URL.
 *
 * External links will show an external icon.
 */

export default {
    name: "VsLink",
    status: "prototype",
    release: "0.0.2",
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
         * Option to create external link which will open URL in blank target and add icon
         */
        external: {
            type: Boolean,
            default: false,
        },
        /**
         * Option to choose a pre-defined style variant
         * `primary, dark`
         */
        variant: {
            type: String,
            default: "primary",
            validator: (value) => value.match(/(primary|dark)/),
        },
    },
}
</script>

<style lang="scss" scoped>
.vs-link {
    &.primary {
        color: $color_pink;

        .icon {
            fill: $color_pink;
        }

        &:focus {
            outline: 2px solid $color_pink;
        }
    }

    &.dark {
        color: $color_yellow;

        .icon {
            fill: $color_yellow;
        }

        &:focus {
            outline: 2px solid $color_yellow;
        }
    }
}
</style>

<docs>
    ```jsx
        <bs-wrapper class="d-flex flex-wrap mb-4">
            <vs-link class="mb-2" href="#foo">
                Link
            </vs-link>

            <vs-link class="ml-6 mb-2" external href="https://www.visitscotland.com">
                External Link
            </vs-link>

            <vs-link variant="dark" class="ml-6 mb-2" href="#foo">
                Dark Link
            </vs-link>

            <vs-link variant="dark" external class="ml-6 mb-2" href="#foo">
                External Dark Link
            </vs-link>
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
                 <vs-link external href="https://www.visitscotland.com">External Link</vs-link>
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
                 accumsan. <vs-link variant="dark" external href="#foo">External Dark Link</vs-link>
                 Fusce rutrum vel ex in posuere. Ut luctus odio eu leo rutrum vestibulum.
                Nulla tortor ligula, consequat ac feugiat et, porta id eros.
            </VsLeadParagraph>
        </bs-wrapper>
    ```
</docs>
