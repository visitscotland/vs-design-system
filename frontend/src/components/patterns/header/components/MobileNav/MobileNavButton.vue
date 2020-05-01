<template>
    <VsButton
        class="vs-mobile-nav__button p-0 p-sm-1"
        id="mobilenavbutton"
        v-b-toggle.collapse-mobile-nav
        variant="transparent"
        focus-style="underline"
        focus-colour="pink"
        @click.native="handleClick()"
    >
        <span class="sr-only"><slot /></span>
        <VsIcon
            v-if="show"
            name="close"
            size="sm"
            variant="dark"
        />
        <VsIcon
            v-else
            name="bars-mobile-menu"
            size="sm"
            variant="dark"
        />
    </VsButton>
</template>

<script>
import { VBToggle } from "bootstrap-vue"
import VsIcon from "../../../../elements/icon/Icon"

export default {
    name: "VsMobileNavButton",
    status: "prototype",
    release: "0.0.1",
    components: {
        VsIcon,
    },
    directives: {
        "b-toggle": VBToggle,
    },
    props: {
        /**
         * The html element name used for the component
         */
        name: {
            type: String,
            default: "",
        },
    },
    data() {
        return {
            show: false,
        }
    },
    methods: {
        handleClick() {
            this.show = !this.show
        },
    },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";
@import "../../styles/placeholders";
@import "../../styles/animations";
</style>

<docs>
  ```jsx

  <div>
    <vs-mobile-nav-button name="Mobile navigation">Toggle Main Navigation</vs-mobile-nav-button>
  </div>
  ```
</docs>
