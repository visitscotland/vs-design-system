<template>
    <li
        class="vs-footer-nav-list-item"
        data-test="vs-footer-nav-list-item"
        role="menuitem"
    >
        <VsLink
            :href="href"
            :type="type"
            variant="on-dark"
            data-test="vs-footer-nav-list-item__link"
        >
            {{ linkText }}
        </VsLink>
    </li>
</template>

<script>
import VsLink from '@components/elements/link/Link';

/**
 * The FooterNavListItems is used inside the FooterNavList to
 * show an indivudal footer link.
 *
 * @displayName Footer Nav List Item
 */

export default {
    name: 'VsFooterNavListItem',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsLink,
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
        &--variant-on-dark {
            color: $color-white;
            text-decoration: none;
        }

        &:hover {
            text-decoration: underline;
        }

        .vs-icon {
            color: $color-white;
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

<docs>
  ```js
    <VsFooter>
        <VsFooterNavList break-point="md">
            <VsCol cols="12" md="6">
                <VsFooterAccordionItem
                    title="Visitor information"
                    variant="dark"
                    control-id="footer_accordion_item_1"
                    class="border-left-0"
                >
                    <span slot="icon-open">
                        <VsIcon name="chevron" variant="light" size="xs" />
                    </span>

                    <span slot="icon-closed">
                        <VsIcon name="chevron" orientation="down" variant="light" size="xs" />
                    </span>

                    <VsList unstyled>
                        <VsFooterNavListItem
                            href="#"
                            link-text="Brochures"
                        ></VsFooterNavListItem>
                        <VsFooterNavListItem
                            href="#"
                            link-text="VisitScotland iCentres"
                        ></VsFooterNavListItem>
                        <VsFooterNavListItem
                            href="#"
                            link-text="iKnow Scotland Community"
                        ></VsFooterNavListItem>
                        <VsFooterNavListItem
                            href="#"
                            type="external"
                            link-text="VisitScotland Awards"
                        ></VsFooterNavListItem>
                    </VsList>
                </VsFooterAccordionItem>
            </VsCol>
        </VsFooterNavList>
    </VsFooter>

  ```
</docs>
