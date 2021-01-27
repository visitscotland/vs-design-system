<template>
    <BDropdown
        v-bind="$attrs"
        class="vs-dropdown"
        no-caret
    >
        <slot
            v-for="(_, name) in nonButtonContentSlots"
            :name="name"
            :slot="name"
        />
        <template #button-content>
            <slot name="button-content">
                {{ text }}
            </slot>
            <VsIcon
                name="chevron"
                orientation="down"
                variant="reverse-white"
                size="xxs"
                class="ml-1"
            />
        </template>
        <slot />
    </BDropdown>
</template>

<script>
import { BDropdown } from 'bootstrap-vue';
import { reject } from 'lodash';
import VsIcon from '@components/elements/icon';

/**
 * Dropdown component for lists of links for example.
 *
 * @displayName Dropdown
 */
export default {
    name: 'VsDropdown',
    components: {
        BDropdown,
        VsIcon,
    },
    props: {
        text: {
            type: String,
            default: '',
        },
    },
    computed: {
        nonButtonContentSlots() {
            return reject(this.$slots, {
                name: 'button-content',
            });
        },
    },
};
</script>

<style lang="scss">
.vs-dropdown {
  .dropdown-toggle {
    .icon-chevron {
      transition: all 150ms ease-in-out;
    }
  }
  &.show {
    .dropdown-toggle {
      .icon-chevron {
        transform: none;
      }
    }
  }
}
</style>

<docs>
  ```js
  <VsDropdown text="Dropdown">
    <VsDropdownItem href="https://www.google.com">Google</VsDropdownItem>
    <VsDropdownItem>bbbb</VsDropdownItem>
    <VsDropdownItem>feeeeee</VsDropdownItem>
  </VsDropdown>
  ```
</docs>
