<template>
    <div class="vs-footer-copyright border-top border-secondary-light">
        <VsContainer>
            <VsRow>
                <VsCol
                    cols="12"
                    class="text-center"
                >
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
import {
    VsRow, VsContainer, VsCol,
} from "@components/elements/layout"

/**
 * The VsFooterCopyright holds an image and copyright notices for the footer.
 */

export default {
    name: "VsFooterCopyright",
    components: {
        VsRow,
        VsContainer,
        VsCol,
    },
    data() {
        return {
            currentYear: "",
        }
    },
    computed: {
        getCurrentYear() {
            return new Date().getFullYear()
        },
    },
}
</script>

<style lang="scss">
.vs-footer-copyright {
    padding: $spacer-6 0;

    .vs-footer-copyright__text{
        color: $color-white;
        margin: 0;
    }

}
</style>

<docs>
  ```js
    <vs-footer>
        <vs-footer-copyright>
            <span slot="copyright">
               VisitScotland. All rights reserved.
            </span>
        </vs-footer-copyright>
    </vs-footer>

  ```
</docs>
