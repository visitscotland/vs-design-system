<template>
  <b-form-input
    class="vs-form-input"
    :size="size"
    v-bind="$attrs"
    v-model="inputVal"
  ></b-form-input>
</template>

<script>
import { BFormInput } from "bootstrap-vue"

/**
 * TODO: Document usage
 */

export default {
  name: "VsFormInput",
  status: "prototype",
  release: "0.0.1",
  components: {
    BFormInput,
  },
  props: {
    size: {
      default: "sm",
    },
    value: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      inputVal: this.value,
    }
  },
  watch: {
    inputVal(newValue) {
      this.$emit("input", newValue)
    },
    value(newValue) {
      this.inputVal = newValue
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/forms";

.vs-form-input {
  &.form-control {
    border-color: $gray-tint-1;

    &:focus {
      border-color: $gray-tint-1;
    }

    &.is-invalid {
      background-image: none;
    }
  }
}
</style>

<docs>
```jsx
<div>
  <vs-form-input />
</div>
```
</docs>
