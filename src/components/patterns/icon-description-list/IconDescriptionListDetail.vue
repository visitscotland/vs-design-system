<template>
    <dd class="vs-icon-description-list__detail">
        <vs-icon :name="icon" variant="dark" size="sm" />
        {{ label }}
    </dd>
</template>

<script>
import VsIcon from "@components/elements/icon/Icon"

/**
 * An icon description list detail is a child component used within icon description list components.
 * The component takes in an icon name and label that should be displayed under the icon as props.
 * They're displayed with an inline-table layout setting.
 */

export default {
    name: "VsIconDescriptionListDetail",
    status: "prototype",
    release: "0.0.1",
    components: {
        VsIcon,
    },
    props: {
        /**
         * Label displayed under the icon
         */
        label: {
            type: String,
            required: true,
        },
        /**
         * The icon name
         */
        icon: {
            type: String,
            required: true,
        },
    },
}
</script>

<style lang="scss" scoped>
.vs-icon-description-list__detail {
    display: inline-table;
    vertical-align: top;
    text-align: center;
    width: 90px;
    position: relative;

    svg {
        display: block;
        margin: 0 auto;
        width: 100%;
    }
}
</style>
