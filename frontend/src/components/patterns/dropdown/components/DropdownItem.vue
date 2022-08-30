<template>
    <BDropdownItem v-bind="$attrs">
        <slot />
    </BDropdownItem>
</template>

<script>
import { BDropdownItem } from 'bootstrap-vue';
import removeAttrsMixin from '../../../../mixins/removeAttrsMixin';

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
    mixins: [
        removeAttrsMixin,
    ],
};
</script>
