<template>
    <VsDrawerToggle
        :tag="tag"
        v-bind="$attrs"
        class="vs-header__drawer-toggle"
    >
        <slot />
    </VsDrawerToggle>
</template>

<script>
import VsDrawerToggle from "@components/patterns/drawer/DrawerToggle"
import VsHeaderButton from "../button/HeaderButton"

/**
 * This component is used to generate a VsDrawerToggle component with
 * the relevant styles for inclusion in VsHeader.
 */
export default {
    name: "VsHeaderDrawerToggle",
    components: {
        VsDrawerToggle,
        VsHeaderButton,
    },
    props: {
        tag: {
            type: String,
            default: "vs-header-button",
        },
    },
}
</script>

<style lang="scss" scoped></style>
