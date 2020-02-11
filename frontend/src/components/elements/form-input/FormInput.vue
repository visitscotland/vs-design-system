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
 * https://bootstrap-vue.js.org/docs/components/form-input
 * https://getbootstrap.com/docs/4.3/components/forms/
 */

export default {
  name: "VsFormInput",
  status: "prototype",
  release: "0.0.1",
  components: {
    BFormInput,
  },
  props: {
    /**
     * Set the form field size.
     * `sm, md, lg`
     */
    size: {
      default: "md",
      validator: value => {
        return value.match(/(sm|md|lg)/)
      },
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
    border-color: $color-gray-tint-1;
    transition: box-shadow 250ms ease;

    &:focus {
      border-color: $color-gray-tint-1;
      box-shadow: 0 0 0 0.2rem rgba(187, 38, 132, 0.5); // primary rgb equivalent
    }

    &[type="search"] {
      @extend %reset-clear;
    }
  }
}
</style>

<docs>
```jsx
<bs-wrapper>
  <label for="small">Small</label>
  <vs-form-input id="small" placeholder="Enter your name" class="mb-5" size="sm" />
  <label for="medium">Medium (default)</label>
  <vs-form-input id="medium" placeholder="Enter your name" class="mb-5" size="md" />
  <label for="large">Large</label>
  <vs-form-input id="large" placeholder="Enter your name" class="mb-5" size="lg" />

  <label for="input-none">No State</label>
  <vs-form-input id="input-none" :state="null" placeholder="No validation" class="mb-5"/>
  <label for="input-valid">Valid state</label>
  <vs-form-input id="input-valid" :state="true" placeholder="Valid" class="mb-5" />
  <label for="input-invalid">Invalid state</label>
  <vs-form-input id="input-invalid" :state="false" placeholder="Invalid" class="mb-5" />
</bs-wrapper>
```
</docs>
