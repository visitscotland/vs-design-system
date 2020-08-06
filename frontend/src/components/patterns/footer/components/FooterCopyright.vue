<template>
    <div class="vs-footer-copyright border-top border-secondary-light">
        <VsContainer>
            <VsRow>
                <VsCol
                    cols="12"
                    class="text-center"
                >
                    <a
                        class="vs-footer-copyright__logo mb-4"
                        :href="href"
                    >
                        <span class="sr-only">{{ logoAltText }}</span>
                        <VsSvg path="vs-logo" />
                    </a>

                    <p class="vs-footer-copyright__text">
                        &copy; {{ getCurrentYear }}

                        <!-- @slot contains translated copyright text -->
                        <slot name="copyright" />
                    </p>
                </VsCol>
            </VsRow>
        </VsContainer>
    </div>
</template>

<script>
import VsSvg from '@components/elements/svg/Svg';
import {
    VsRow, VsContainer, VsCol,
} from '@components/elements/layout';


/**
 * The VsFooterCopyright holds an image and copyright notices for the footer.
 */

export default {
    name: 'VsFooterCopyright',
    components: {
        VsSvg,
        VsRow,
        VsContainer,
        VsCol,
    },
    props: {
        /**
         * Accessiblity alt text for the logo button
         */
        logoAltText: {
            type: String,
            required: true,
        },
        /**
         * URL for the logo button
         */
        href: {
            type: String,
            required: true,
        },
    },
    data() {
        return {
            currentYear: '',
        };
    },
    computed: {
        getCurrentYear() {
            return new Date().getFullYear();
        },
    },
};
</script>

<style lang="scss">
.vs-footer-copyright {
    padding: $spacer-6 0;

    .vs-footer-copyright__logo {
        display: inline-block;
    }

    .vs-footer-copyright__text {
        color: $color-white;
        margin: 0;
        font-size: $font-size-sm;

        @include media-breakpoint-up(lg) {
            font-size: $small-font-size;
        }

        @include media-breakpoint-up(xl) {
            font-size: $font-size-base;
        }
    }
}
</style>

<docs>
  ```js
    <vs-footer>
        <vs-footer-copyright logo-alt-text="VisitScotland Home" href="/">
            <span slot="copyright">
               VisitScotland. All rights reserved.
            </span>
        </vs-footer-copyright>
    </vs-footer>

  ```
</docs>
