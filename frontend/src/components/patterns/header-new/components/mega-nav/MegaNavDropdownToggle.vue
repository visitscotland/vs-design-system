<!-- eslint-disable -->
<template>
    <div
        class="vs-mega-nav__dropdown-toggle"
        ref="menuToggle"
    >
        <VsButton
            class="vs-mega-nav__button"
            variant="transparent"
            aria-haspopup="true"
            :animate="false"
            :uppercase="false"
            :aria-expanded="isOpen ? 'true' : 'false'"
            @click.native="openMenu"
        >
            <slot name="toggle-btn" />
        </VsButton>

        <VsMegaNavDropdown v-show="isOpen">
            <slot name="cta-link" />
            <slot name="dropdown-nav" />
        </VsMegaNavDropdown>
    </div>
</template>

<script>
/**
 *  Mega nav top level menu button
 */

import VsButton from '@components/elements/button/Button';
import VsMegaNavDropdown from '@components/patterns/header-new/components/mega-nav/MegaNavDropdown';

export default {
    name: 'VsMegaNavDropdownToggle',
    status: 'prototype',
    release: '0.1.0',
    components: {
        VsButton,
        VsMegaNavDropdown,
    },
    props: {
        href: {
            type: String,
            default: '',
        },
        ctaText: {
            type: String,
            default: '',
        },
    },
    data() {
        return {
            isOpen: false,
        };
    },
    created() {
        window.addEventListener('click', this.resetMenu);
    },
    beforeDestroy() {
        window.removeEventListener('click', this.resetMenu);
    },
    methods: {
        resetMenu(e) {
            // Close dropdown when user clicks elsewhere
            if (!this.$refs.menuToggle.contains(e.target)) {
                this.isOpen = false;
            }
        },
        openMenu() {
            this.isOpen = !this.isOpen;
        },
    },
};
</script>

<style lang="scss">
@import '~bootstrap/scss/type';

.vs-mega-nav__dropdown-toggle {
    padding: 0;
}
</style>

<docs>
  ```jsx
  ```
</docs>
