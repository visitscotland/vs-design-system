<template>
    <BListGroupItem
        :href="href"
        :active="active"
        :target="external ? '_blank' : null"
        :data-vs-track="trackingId"
        tabindex="0"
        class="vs-drawer__list-item
        text-white text-nowrap
        text-justify align-items-center
        d-flex px-lg-2"
        :class="{ 'vs-drawer__list-item--full-width': fullWidth }"
    >
        <slot />
        <VsIcon
            v-if="href && external"
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
    name: "VsDrawerListItem",
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
            default: "",
        },
        fullWidth: {
            type: Boolean,
        },
    },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/list-group";

.vs-drawer__list-item.list-group-item {
  background-color: transparent;
  margin-bottom: 0;

  &:hover,
  &:focus {
    outline: none;
    background-color: $color-gray-shade-6;
    border-color: transparent;
    box-shadow: inset 0 -3px 0 0 $white;
  }

  &:focus {
    box-shadow: inset 0 -3px 0 0 $white, inset 0 3px 0 0 white;
  }

  &.vs-drawer__list-item--full-width {
    padding-left: $grid-gutter-width / 2;
    padding-right: $grid-gutter-width / 2;

    @include media-breakpoint-up(sm) {
      $padding-x: calc((100% - #{$max-container-width-sm} + #{$grid-gutter-width}) / 2);
      padding-left: $padding-x;
      padding-right: $padding-x;
    }

    @include media-breakpoint-up(md) {
      $padding-x: calc((100% - #{$max-container-width-md} + #{$grid-gutter-width}) / 2);
      padding-left: $padding-x;
      padding-right: $padding-x;
    }

    @include media-breakpoint-up(lg) {
      $padding-x: calc((100% - #{$max-container-width-lg} + #{$grid-gutter-width}) / 2);
      padding-left: $padding-x;
      padding-right: $padding-x;
    }

    @include media-breakpoint-up(xl) {
      $padding-x: calc((100% - #{$max-container-width-xl} + #{$grid-gutter-width}) / 2);
      padding-left: $padding-x;
      padding-right: $padding-x;
    }

    @include media-breakpoint-up(xxl) {
      $padding-x: calc((100% - #{$max-container-width-xxl} + #{$grid-gutter-width}) / 2);
      padding-left: $padding-x;
      padding-right: $padding-x;
    }
  }
}
</style>

<docs>
  ```jsx
  ```
</docs>
