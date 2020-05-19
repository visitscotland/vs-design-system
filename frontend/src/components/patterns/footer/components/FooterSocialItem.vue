<template>
    <li class="vs-footer-social-item">
        <VsLink :href="href" :external="true" :class="icon">
            <VsIcon :name="icon" variant="light" size="sm" />
        </VsLink>
    </li>
</template>

<script>
import VsLink from "@components/elements/link/Link"
import VsIcon from "@components/elements/icon/Icon"

/**
 * The FooterNavListItems is used inside the FooterNavList to
 * show an indivudal footer link.
 */

export default {
    name: "VsFooterSocialItem",
    components: {
        VsLink,
        VsIcon,
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
}
</script>

<style lang="scss">
.vs-footer-social-item {
    list-style: none;
    display: inline-block;
    text-decoration: none;
    padding: 0;
    margin-right: $spacer-2;
    margin-bottom: $spacer-2;

    .vs-link.primary {
        background: $color-gray-shade-1;
        box-shadow: 0 0 0 2px $color-gray-shade-7;
        border: 1px solid $color-gray-shade-7;
        border-radius: 0.5rem;
        display: block;
        height: 45px;
        width: 45px;
        transition: all .2s;

        &.facebook {
            &:hover {
                background: $color-facebook;
            }
        }
        &.instagram {
            &:hover {
                background: $color-instagram;
            }
        }
        &.twitter {
            &:hover {
                background: $color-twitter;
            }
        }
        &.youtube {
            &:hover {
                background: $color-youtube;
            }
        }

        &:hover {
            text-decoration: underline;
        }

        &:focus {
            outline: none;
            border: 1px solid $color-pink;
            box-shadow: 0 0 0 2px $color-pink-tint-5;
        }

        .icon {
            &.icon-sm {
                height: 100%;
                width: 24px;
                padding: 0;
                margin: 0 auto;
                fill: $color-white;
                display: block;
            }

            &.icon-external-link {
                display: none;
            }
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
                    <vs-accordion-item
                        :visible="false"
                        variant="dark"
                        aria-control-id="1"
                        class="border-left-0"
                    >
                        <span slot="title">
                            Find us on
                        </span>

                        <span slot="icon-open">
                            <vs-icon name="chevron-up" variant="light" size="xs" />
                        </span>

                        <span slot="icon-closed">
                            <vs-icon name="chevron-right" variant="light" size="xs" />
                        </span>

                        <vs-list unstyled class="pb-2">
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
            </vs-row>
        </vs-footer-nav-list>
    </vs-footer>

  ```
</docs>
