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
 * Dropdown component for lists of links for example.
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
}
</style>

<docs>
  ```js
  <vs-footer-nav-list>
    <vs-accordion-item :visible="false" variant="dark" index="1">
        <span slot="title">
            Visitor information
        </span>

        <span slot="icon-open">
            <VsIcon name="chevron-up" variant="light" size="xs" />
        </span>

        <span slot="icon-closed">
            <VsIcon name="chevron-right" variant="light" size="xs" />
        </span>

        <vs-list unstyled class="pb-2">
            <vs-footer-nav-list-item
                href="#"
                linkText="Brochures"
            ></vs-footer-nav-list-item>
            <vs-footer-nav-list-item
                href="#"
                linkText="VisitScotland iCentres"
            ></vs-footer-nav-list-item>
            <vs-footer-nav-list-item
                href="#"
                linkText="iKnow Scotland Community"
            ></vs-footer-nav-list-item>
            <vs-footer-nav-list-item
                href="#"
                linkText="VisitScotland Awards"
            ></vs-footer-nav-list-item>
        </vs-list>
    </vs-accordion-item>

    <vs-accordion-item :visible="false" variant="dark" index="2">
        <span slot="title">
            Get in touch
        </span>

        <span slot="icon-open">
            <VsIcon name="chevron-up" variant="light" size="xs" />
        </span>

        <span slot="icon-closed">
            <VsIcon name="chevron-right" variant="light" size="xs" />
        </span>

        <vs-list unstyled class="pb-2">
            <vs-footer-nav-list-item
                href="#"
                linkText="Contact Information"
            ></vs-footer-nav-list-item>
            <vs-footer-nav-list-item
                href="#"
                linkText="Feedback & Complaints"
            ></vs-footer-nav-list-item>
        </vs-list>
    </vs-accordion-item>
    <vs-accordion-item :visible="false" variant="dark" index="3">
        <span slot="title">
            Our other sites
        </span>

        <span slot="icon-open">
            <VsIcon name="chevron-up" variant="light" size="xs" />
        </span>

        <span slot="icon-closed">
            <VsIcon name="chevron-right" variant="light" size="xs" />
        </span>

        <vs-list unstyled class="pb-2">
            <vs-footer-nav-list-item
                href="#"
                linkText="Business Events"
                external
            ></vs-footer-nav-list-item>
            <vs-footer-nav-list-item
                href="#"
                linkText="Travel Trade"
                external
            ></vs-footer-nav-list-item>
            <vs-footer-nav-list-item
                href="#"
                linkText="Media Centre"
                external
            ></vs-footer-nav-list-item>
        </vs-list>
    </vs-accordion-item>
</vs-footer-nav-list>

  ```
</docs>
