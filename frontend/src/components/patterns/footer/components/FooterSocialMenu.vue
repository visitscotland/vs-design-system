<template>
    <div class="vs-footer-social-menu px-0 py-6 py-md-8 py-lg-0 px-lg-3">
        <h4 class="vs-footer-social-menu__title mx-3 mx-sm-6 m-md-0 mb-lg-6">
            <!-- @slot Put the menu title here  -->
            <slot name="title" />
        </h4>

        <!-- @slot The default slot is the content for the menu social items -->
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
    border-left: 1px solid $color-gray-shade-2;
    background: $color-gray-shade-7;

    .vs-footer-social-menu__title{
        background: $color-gray-shade-7;
        color: $color-white;
        line-height: $line-height-xs;
        font-weight: 500;
    }

    @include media-breakpoint-down(md) {
        border-left: 0;

        .vs-list {
            display: flex;
            justify-content: center;

            .vs-footer-social-item{
                margin-bottom: 0;
            }
        }
    }
}
</style>

<docs>
  ```js
    <vs-footer>
        <vs-footer-nav-list>
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
        </vs-footer-nav-list>
    </vs-footer>
  ```
</docs>
