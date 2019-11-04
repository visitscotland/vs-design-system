<template>
  <b-form-input
    class="vs-form-input"
    :class="{
      [focusStyleClass]: focusStyleClass,
      [focusColourClass]: focusColourClass,
    }"
    :size="size"
    v-bind="$attrs"
    v-model="inputVal"
  ></b-form-input>
</template>

<script>
import { BFormInput } from "bootstrap-vue"
import focusStylesMixin from "@/mixins/focusStyles.js"

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
  mixins: [focusStylesMixin],
  props: {
    focusStyle: {
      default: "outset",
    },
    focusColour: {
      default: "pink",
    },
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
  @include vs-focus;

  &.form-control {
    border: none;
    box-shadow: inset 0 0 0 2px $color-gray-tint-3;
  }
}
</style>

<docs>
```jsx
<div>
  <vs-form-input focus-colour="pink" focus-style="underline" />
</div>
```
</docs>
