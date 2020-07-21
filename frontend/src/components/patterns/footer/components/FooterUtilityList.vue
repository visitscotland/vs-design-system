<template>
    <div class="border-top border-secondary-light vs-footer-utility-list">
        <VsContainer>
            <VsRow>
                <VsCol
                    cols="12"
                    sm="6"
                    md="12"
                >
                    <VsList unstyled>
                        <!-- @slot The default slot is the content for the menu social items -->
                        <slot />
                    </VsList>
                </VsCol>
            </VsRow>
        </VsContainer>
    </div>
</template>

<script>
import VsList from "@components/elements/list/List"
import {
    VsRow, VsContainer, VsCol,
} from "@components/elements/layout"

/**
 * The FooterUtilityList is a list of FooterNavListItems
 * that hold general utility links for the footer.
 */

export default {
    name: "VsFooterUtilityList",
    components: {
        VsList,
        VsRow,
        VsContainer,
        VsCol,
    },
}
</script>

<style lang="scss">
.vs-footer-utility-list {
    @include media-breakpoint-down(sm) {
        .container {
            max-width: 100%;
            padding: 0;
        }
    }

    .vs-list.unstyled {
        justify-content: space-between;
        display: flex;
        flex-wrap: wrap;
        padding: $spacer-3;

        @include media-breakpoint-up(sm) {
            padding: $spacer-3 $spacer-6;
        }

        @include media-breakpoint-up(md) {
            padding: $spacer-3 0;
        }

        @include media-breakpoint-up(lg) {
            justify-content: center;
            padding: $spacer-8 0;
        }

        @include media-breakpoint-up(xl) {
            padding: $spacer-9 0;
        }

        @include media-breakpoint-up(xxl) {
            justify-content: space-evenly;
            padding: $spacer-9 0;
        }

        .vs-footer-nav-list-item {
            display: inline-block;
            font-size: $font-size-sm;
            padding: $spacer-3;
            flex: 1 0 50%;

            @include media-breakpoint-down(sm) {
                &:nth-child(odd){
                    padding-left: 0;
                }
            }

            @include media-breakpoint-up(sm) {
                &:first-child {
                    padding-left: 0;
                }

                &:last-child {
                    padding-right: 0;
                }
            }

            @include media-breakpoint-up(md) {
                flex: none;
            }
            @include media-breakpoint-up(xl) {
                font-size: $font-size-base;
            }
        }
    }
}
</style>

<docs>
  ```js
    <vs-footer>
        <vs-footer-utility-list>
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

        </vs-footer-utility-list>
    </vs-footer>

  ```
</docs>
