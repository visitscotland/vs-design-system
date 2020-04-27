<template>
    <div class="social-credit">
        <VsIcon
            v-if="source"
            :name="source"
            variant="light"
            size="xs"
            :padding="1"
        />
        <VsLink
            class="social-credit__link"
            :href="socialPostUrl"
            external
        >
            {{ credit }}
        </VsLink>
    </div>
</template>

<script>
import VsLink from "@components/elements/link/Link"
import VsIcon from "@components/elements/icon/Icon"

export default {
    name: "VsSocialCreditLink",
    status: "prototype",
    release: "0.0.1",
    components: {
        VsLink,
        VsIcon,
    },
    props: {
        /**
         * The social credit
         */
        credit: {
            type: String,
            required: true,
        },

        /**
         * URL to social item
         */
        socialPostUrl: {
            type: String,
            required: true,
        },

        /**
         * Where the image came from
         * `instagram`
         */
        source: {
            type: String,
            default: "",
            validator: (value) => value.match(/(instagram)/),
        },
    },
}
</script>

<style lang="scss" scoped>
.social-credit {
    line-height: $line_height_xs;

    .social-credit__link {
        color: $color-white !important;
        font-weight: $font-weight-light;
        font-size: $small-font-size;
        line-height: $standard-line-height;
        margin-bottom: $spacer-0;

        &:hover,
        &:focus {
            text-decoration: underline !important;
        }

        &:focus {
            font-weight: $semibold-font-weight !important;
            outline: 0 !important;
        }

        & ::v-deep {
            svg.icon {
                fill: $color-white !important;
            }
        }
    }
}
</style>
