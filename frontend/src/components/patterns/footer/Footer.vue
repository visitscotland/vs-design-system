<template>
    <div class="vs-footer pt-sm-6 pt-md-9">
        <slot />
    </div>
</template>

<script>

/**
 * This component is the main Footer wrapper for the bottom of the page.
 */

export default {
    name: "VsFooter",
    status: "prototype",
    release: "0.1.0",
}
</script>

<style lang="scss" scoped>
.vs-footer {
    background: $color-theme-dark;

    @include media-breakpoint-down(sm) {
        .vs-footer-social-menu__wrapper{
            max-width: 100%;
            padding: 0;

            & ::v-deep {
                .row{
                    margin-right: 0;
                    margin-left: 0;

                    > .col,
                    > [class*="col-"]  {
                        padding-right: 0;
                        padding-left: 0;
                    }
                }
            }
        }
    }
}
</style>

<docs>
  ```js
    <vs-footer>
        <vs-footer-nav-list break-point="md">
            <vs-col cols="12" md="4" lg="3">
                <vs-footer-accordion-item
                    :openByDefault="false"
                    variant="dark" control-id="1"
                    class="border-left-0 pl-md-0"
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

                    <vs-list unstyled>
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

            <vs-col cols="12" md="4" lg="3">
                <vs-footer-accordion-item
                    :openByDefault="false"
                    variant="dark"
                    control-id="2"
                >
                    <span slot="title">
                        Get in touch
                    </span>

                    <span slot="icon-open">
                        <vs-icon name="chevron-up" variant="light" size="xs" />
                    </span>

                    <span slot="icon-closed">
                        <vs-icon name="chevron-right" variant="light" size="xs" />
                    </span>

                    <vs-list unstyled>
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

            <vs-col cols="12" md="4" lg="3">
                <vs-footer-accordion-item
                    :openByDefault="false"
                    variant="dark"
                    control-id="3"
                    class="border-bottom-0"
                >
                    <span slot="title">
                        Our other sites
                    </span>

                    <span slot="icon-open">
                        <vs-icon name="chevron-up" variant="light" size="xs" />
                    </span>

                    <span slot="icon-closed">
                        <vs-icon name="chevron-right" variant="light" size="xs" />
                    </span>

                    <vs-list unstyled>
                        <vs-footer-nav-list-item
                            href="#"
                            link-text="Brochures"
                            external
                        ></vs-footer-nav-list-item>
                        <vs-footer-nav-list-item
                            href="#"
                            link-text="VisitScotland iCentres"
                            external
                        ></vs-footer-nav-list-item>
                        <vs-footer-nav-list-item
                            href="#"
                            link-text="iKnow Scotland Community"
                            external
                        ></vs-footer-nav-list-item>
                        <vs-footer-nav-list-item
                            href="#"
                            link-text="VisitScotland Awards"
                            external
                        ></vs-footer-nav-list-item>
                    </vs-list>
                </vs-footer-accordion-item>
            </vs-col>

            <vs-col cols="12" lg="3" xl="2" class="d-none d-lg-block">
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
        <div class="border-top border-secondary-light">
            <vs-container class="vs-footer-social-menu__wrapper">
                <vs-row>
                    <vs-col cols="12" class="d-block d-lg-none">
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
            </vs-container>
        </div>
    </vs-footer>
  ```
</docs>
