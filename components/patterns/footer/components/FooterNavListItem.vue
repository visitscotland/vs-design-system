<template>
    <li
        class="vs-footer-nav-list-item"
        data-test="vs-footer-nav-list-item"
        role="menuitem"
    >
        <VsLink
            :href="href"
            :type="type"
            variant="dark"
            data-test="vs-footer-nav-list-item__link"
        >
            {{ linkText }}
        </VsLink>
    </li>
</template>

<script>
/**
 * The FooterNavListItems is used inside the FooterNavList to
 * show an indivudal footer link.
 *
 * @displayName Footer Nav List Item
 */

export default {
    name: 'VsFooterNavListItem',
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
            default: 'default',
            validator: (value) => value.match(/(default|external|internal|download)/),
        },
        /**
         * Text to show for the link
         */
        linkText: {
            type: String,
            default: '',
        },
    },
};
</script>

<style lang="scss">
.vs-footer-nav-list-item {
    list-style: none;
    font-size: $font-size-3;
    line-height: $line-height-s;
    padding: $spacer-3 $spacer-8;

    .vs-link {
        &--variant-dark {
            color: $color-white;
            text-decoration: none;
        }

        &:hover {
            text-decoration: underline;
        }

        .vs-icon {
            fill: $color-white;
        }
    }

    @include media-breakpoint-up(md) {
        padding: $spacer-2 0;

        &:last-child {
            padding-bottom: $spacer-1;
        }
    }
}
</style>
