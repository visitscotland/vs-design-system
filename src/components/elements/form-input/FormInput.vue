<template>
  <b-form-input
    class="vs-form-input"
    :class="{
      [focusStyleClass]: focusStyleClass,
      [focusColourClass]: focusColourClass,
    }"
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
      default: "underline",
    },
    focusColour: {
      default: "pink",
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/input-group";

.vs-form-input {
  @include vs-focus;
}
</style>

<docs>
```jsx
<div>
  <vs-form-input focus-colour="pink" focus-style="underline" />
</div>
```
</docs>
