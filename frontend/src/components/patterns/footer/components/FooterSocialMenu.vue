<template>
    <div class="vs-footer-social-menu">
        <h4 class="vs-footer-social-menu__title">
            <!-- @slot Put the title here  -->
            <slot name="title" />
        </h4>

        <!-- @slot The default slot is the content for the menu items -->
        <slot />
    </div>
</template>

<script>

/**
 * The FooterSocialMenu is used inside the FooterNavList.
 * It's a wrapper for social media links in the footer.
 */

export default {
    name: "VsFooterSocialMenu",
}
</script>

<style lang="scss">
.vs-footer-social-menu {
    border-bottom: 0;
    border-left: 1px solid #666666;
    padding: 0 0.75rem;
    background: #191919;

    .vs-footer-social-menu__title{
        background: #191919;
        color: white;
        margin: 0.25rem 0 0.75rem 0;
        line-height: 1;
        font-weight: 500;
    }

    @include media-breakpoint-down(md) {
        border-left: 0;
        border-top: 1px solid $color-gray-shade-2;
        padding: $spacer-8 0 0 0;

        .vs-footer-social-menu__title {
            margin: 0;
        }

        .vs-list {
            display: flex;
            justify-content: center;
        }
    }

    @include media-breakpoint-down(sm) {
        border-top: 0;
        padding: 0;

        .vs-footer-social-menu__title {
            margin: $spacer-6 $spacer-3 0;
        }
    }
}
</style>

<docs>
  ```js
    <vs-footer>
        <vs-footer-nav-list>
            <vs-row>
                <vs-col cols="12" md="6">
                    <vs-footer-social-menu>
                        <span slot="title">
                            Find us on
                        </span>

                        <vs-list unstyled class="mt-5 mt-md-0 mt-lg-5 pr-lg-8 pr-xl-4">
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
                    </vs-footer-social-menu>
                </vs-col>
            </vs-row>
        </vs-footer-nav-list>
    </vs-footer>

  ```
</docs>
