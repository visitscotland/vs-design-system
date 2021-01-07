<template>
    <BDropdownItem v-bind="$attrs">
        <slot />
    </BDropdownItem>
</template>

<script>
import { BDropdownItem } from 'bootstrap-vue';

/**
 * TODO: Document usage
 *
 * @displayName Dropdown Item
 */
export default {
    name: 'VsDropdownItem',
    components: {
        BDropdownItem,
    },
};
</script>

<style lang="scss">
@import "~bootstrap/scss/dropdown";
@import "~bootstrap-vue/src/components/dropdown/dropdown";
</style>
