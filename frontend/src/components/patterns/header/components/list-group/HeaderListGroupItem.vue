<template>
    <BListGroupItem
        :href="href"
        :active="active"
        :target="external ? '_blank' : null"
        :data-vs-track="trackingId"
        tabindex="0"
        class="vs-header__list-group-item text-white text-nowrap text-justify align-items-center d-flex px-lg-2"
    >
        <slot />
        <VsIcon
            v-if="external"
            class="d-lg-none ml-1"
            name="external-link"
            size="xxs"
            variant="reverse-white"
        />
    </BListGroupItem>
</template>

<script>
import { BListGroupItem } from "bootstrap-vue"

export default {
    name: "VsHeaderListGroupItem",
    status: "prototype",
    release: "0.1.0",
    components: {
        BListGroupItem,
    },
    props: {
        href: {
            required: true,
            type: String,
        },
        external: {
            type: [Boolean, String],
            default: false,
        },
        active: {
            type: [Boolean, String],
            default: false,
        },
        trackingId: {
            type: String,
        },
    },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/list-group";
@import "../../styles/placeholders";

.vs-header__list-group-item.list-group-item {
  @extend %header-nav-item;
}
</style>

<docs>
  ```jsx
  ```
</docs>
