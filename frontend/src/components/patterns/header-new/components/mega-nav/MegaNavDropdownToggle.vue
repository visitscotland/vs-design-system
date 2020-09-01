<!-- eslint-disable -->
<template>
    <div class="vs-mega-nav__dropdown-toggle" ref="menuToggle">
        <VsButton
            class="vs-mega-nav__button"
            variant="transparent"
            aria-haspopup="true"
            :aria-expanded="showDropdown ? 'true' : 'false'"
            :aria-controls="controlId"
            :animate="false"
            :uppercase="false"
            @click.native="openMenu"
        >
            <slot name="toggle-btn" />
        </VsButton>

        <VsMegaNavDropdown v-show="showDropdown" :id="controlId">
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
        controlId: {
            type: String,
            default: '',
        },
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
            showDropdown: false,
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
                this.showDropdown = false;
            }
        },
        openMenu() {
            this.showDropdown = !this.showDropdown;
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
