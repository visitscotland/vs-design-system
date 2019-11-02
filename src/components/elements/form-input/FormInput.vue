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
      default: "md",
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
    transition: $transition-base;

    &:focus {
      border-color: $gray-tint-1;
      box-shadow: 0 0 0 0.2rem rgba(187, 38, 132, 0.5); // primary rgb equivalent
    }

    &.is-invalid {
      background-image: none;
    }
  }
}
</style>

<docs>
```jsx
<bs-wrapper>
  <label for="small">Small</label>
  <vs-form-input id="small" class="mb-5" size="sm" />
  <label for="medium">Medium</label>
  <vs-form-input id="medium" class="mb-5" size="md" />
  <label for="large">Large</label>
  <vs-form-input id="large" class="mb-5" size="lg" />
</bs-wrapper>
```
</docs>
