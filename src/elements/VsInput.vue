<template>
  <component :is="wrapper" :class="normalisedWrapperClass">
    <label :for="id" v-if="label && type !== 'checkbox'">{{ label }}</label>
    <input
      :id="id"
      :disabled="disabled"
      :type="type"
      :value="value"
      :class="normalisedInputClass"
      :placeholder="placeholder"
      @input="onInput($event.target.value)"
      @focus="onFocus($event.target.value)"
    />
    <label :for="id" v-if="label && type === 'checkbox'">{{ label }}</label>
  </component>
</template>

<script>
import isUndefined from "lodash/isUndefined"

export default {
  name: "VsInput",
  status: "ready",
  release: "1.0.0",
  props: {
    /**
     * The type of the form input field.
     * `text, number, email, checkbox`
     */
    type: {
      type: String,
      default: "text",
      validator: value => {
        return value.match(/(text|number|email|checkbox)/)
      },
    },
    /**
     * Text value of the form input field.
     */
    value: {
      type: String,
      default: null,
    },
    /**
     * The base class of the input element (defaults to form-control/form-check-input)
     */
    inputClass: {
      type: String,
    },
    /**
     * The html element name used for the wrapper.
     * `div, section`
     */
    wrapper: {
      type: String,
      default: "div",
      validator: value => {
        return value.match(/(div|section)/)
      },
    },
    /**
     * The class of the wrapper element (defaults to form-group/form-check)
     */
    wrapperClass: {
      type: String,
    },
    /**
     * The placeholder value for the form input field.
     */
    placeholder: {
      type: String,
      default: null,
    },
    /**
     * The label of the form input field.
     */
    label: {
      type: String,
      default: null,
    },
    /**
     * Unique identifier of the form input field.
     */
    id: {
      type: String,
      default: null,
    },
    /**
     * Whether the form input field is disabled or not.
     * `true, false`
     */
    disabled: {
      type: Boolean,
      default: false,
    },
    /**
     * Manually trigger various states of the input.
     * `hover, active, focus`
     */
    state: {
      type: String,
      default: null,
      validator: value => {
        return value.match(/(hover|active|focus)/)
      },
    },
  },
  methods: {
    onInput(value) {
      this.$emit("change", value)
    },
    onFocus(value) {
      this.$emit("focus", value)
    },
    isCheckbox() {
      return this.type === "checkbox"
    },
  },
  computed: {
    normalisedInputClass() {
      let constructed = !isUndefined(this.inputClass)
        ? this.inputClass
        : this.isCheckbox()
        ? "form-check-input"
        : "form-control"

      return this.state ? constructed + " " + this.state : constructed
    },
    normalisedWrapperClass() {
      if (!isUndefined(this.wrapperClass)) {
        return this.wrapperClass
      }

      return this.isCheckbox() ? "form-check" : "form-group"
    },
  },
}
</script>

<style lang="scss" scoped>
// Design Tokens with local scope
</style>

<docs>
  ```jsx
  <div>
    <vs-input label="Default input" placeholder="Write your text" />
    <vs-input label=":hover" state="hover" placeholder="Write your text" />
    <vs-input label=":focus" state="focus" placeholder="Write your text" />
    <vs-input label="[disabled]" disabled value="Write your text" />
    <vs-input label="check me" type="checkbox" wrapper-class="form-check" />
  </div>
  ```
</docs>
