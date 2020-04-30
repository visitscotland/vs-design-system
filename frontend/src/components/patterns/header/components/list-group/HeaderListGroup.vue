<template>
    <BListGroup
        v-bind="$attrs"
        class="vs-header__list-group"
        :class="{ 'vs-header__list-group--top': isTop }"
        horizontal
    >
        <slot />
    </BListGroup>
</template>

<script>
import { BListGroup } from "bootstrap-vue"

export default {
    name: "VsHeaderListGroup",
    components: {
        BListGroup,
    },
    props: {
        section: {
            type: String,
            default: "bottom",
            validator: (value) => value.match(/(top|bottom)/),
        },
    },
    computed: {
        isTop() {
            return this.section === "top"
        },
    },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/list-group";
@import "../../styles/placeholders";

.vs-header__list-group--top ::v-deep {
  .vs-header__list-group-item.list-group-item {
    @extend %header-nav-item-top;

    @include media-breakpoint-up(md) {
      padding-bottom: 8px;
      padding-top: 8px;
    }
  }
}
</style>

<docs>
  ```js
  ```
</docs>
