<template>
    <li class="vs-footer-nav-list-item">
        <VsLink
            :href="href"
            :external="external"
            variant="dark"
        >
            {{ linkText }}
        </VsLink>
    </li>
</template>

<script>
import VsLink from "@components/elements/link/Link"

/**
 * The FooterNavListItems is used inside the FooterNavList to
 * show an indivudal footer link.
 */

export default {
    name: "VsFooterNavListItem",
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
         * Option to create external link which will open URL in blank target and add icon
         */
        external: {
            type: Boolean,
            default: false,
        },
        /**
         * Text to show for the link
         */
        linkText: {
            type: String,
            default: "",
        },
    },
}
</script>

<style lang="scss">
.vs-footer-nav-list-item {
    list-style: none;
    font-size: $small-font-size;
    line-height: $line-height-s;
    padding: $spacer-3 $spacer-8;

    .vs-link.dark {
        color: $color-white;
        text-decoration: none;

        &:hover {
            text-decoration: underline;
        }

        .icon {
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

<docs>
  ```js
    <vs-footer>
        <vs-footer-nav-list break-point="md">
            <vs-row>
                <vs-col cols="12" md="6">
                    <vs-footer-accordion-item
                        :openByDefault="false"
                        variant="dark"
                        aria-control-id="1"
                        class="border-left-0"
                    >
                        <span slot="title">
                            Visitor information
                        </span>

                        <span slot="icon-open">
                            <vs-icon name="chevron-up" variant="light" size="xs" />
                        </span>

                        <span slot="icon-closed">
                            <vs-icon name="chevron-right" variant="light" size="xs" />
                        </span>

                        <vs-list unstyled class="pb-2">
                            <vs-footer-nav-list-item
                                href="#"
                                link-text="Brochures"
                            ></vs-footer-nav-list-item>
                            <vs-footer-nav-list-item
                                href="#"
                                link-text="VisitScotland iCentres"
                            ></vs-footer-nav-list-item>
                            <vs-footer-nav-list-item
                                href="#"
                                link-text="iKnow Scotland Community"
                            ></vs-footer-nav-list-item>
                            <vs-footer-nav-list-item
                                href="#"
                                link-text="VisitScotland Awards"
                            ></vs-footer-nav-list-item>
                        </vs-list>
                    </vs-footer-accordion-item>
                </vs-col>
            </vs-row>
        </vs-footer-nav-list>
    </vs-footer>

  ```
</docs>
