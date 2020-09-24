<template>
    <li class="vs-footer-social-item">
        <VsButtonSquareSocial
            :href="href"
            :icon="icon"
        />
    </li>
</template>

<script>

import VsButtonSquareSocial from '@components/patterns/button-square-social/ButtonSquareSocial';


/**
 * The FooterSocialItem is used inside the FooterSocialMenu to
 * show a button for a social media link.
 */

export default {
    name: 'VsFooterSocialItem',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsButtonSquareSocial,
    },
    props: {
        /**
         * The URL the link will point to
         */
        href: {
            type: String,
            required: true,
        },
        /**
         * Social icon to show, will also be used to choose hover colour
         * `facebook, instagram, twitter, youtube`
         */
        icon: {
            type: String,
            required: true,
            validator: (value) => value.match(/(facebook|instagram|twitter|youtube)/),
        },
    },
};
</script>

<style lang="scss">
.vs-footer-social-item {
    list-style: none;
    display: inline-block;
    text-decoration: none;
    padding: 0;
    margin-right: $spacer-3;
    margin-bottom: $spacer-3;
}
</style>

<docs>
  ```js
    <vs-footer>
        <vs-footer-nav-list break-point="md">
            <vs-col cols="12" md="6">
                <vs-accordion-item
                    :open-by-default="true"
                    variant="dark"
                    control-id="accordion_item_1"
                    class="border-left-0"
                >
                    <span slot="title">
                        Find us on
                    </span>

                    <span slot="icon-open">
                        <vs-icon name="chevron" variant="light" size="xs" />
                    </span>

                    <span slot="icon-closed">
                        <vs-icon name="chevron" orientation="right" variant="light" size="xs" />
                    </span>

                    <vs-list unstyled>
                        <vs-footer-social-item
                            href="#"
                            icon="facebook"
                        ></vs-footer-social-item>
                        <vs-footer-social-item
                            href="#"
                            icon="twitter"
                        ></vs-footer-social-item>
                        <vs-footer-social-item
                            href="#"
                            icon="youtube"
                        ></vs-footer-social-item>
                        <vs-footer-social-item
                            href="#"
                            icon="instagram"
                        ></vs-footer-social-item>
                    </vs-list>
                </vs-accordion-item>
            </vs-col>
        </vs-footer-nav-list>
    </vs-footer>
  ```
</docs>
