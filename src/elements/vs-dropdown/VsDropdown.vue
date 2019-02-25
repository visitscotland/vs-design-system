<template>
  <component :is="wrapper" :class="wrapperClass">
    <label :for="id" v-if="label">{{ label }}</label>
    <select :id="id" :class="normalisedInputClass" @change="handleChange" :value="value">
      <option v-for="option in options" :value="optionValue(option)">
        {{ optionLabel(option) }}
      </option>
    </select>
  </component>
</template>

<script>
import { get, find } from "lodash"
import isUndefined from "lodash/isUndefined"

export default {
  name: "VsDropdown",
  props: {
    /**
     * The currently selected value
     */
    value: {
      type: String,
      default: null,
    },
    updateValue: Function,
    labelKey: {
      type: String,
      default: "label",
    },
    valueKey: {
      type: String,
      default: "value",
    },
    options: Array,
    disabled: Boolean,
    /**
     * Unique identifier of the select control.
     */
    id: {
      type: String,
      default: null,
    },
    /**
     * The label of the form textarea.
     */
    label: {
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
      default: "form-group",
    },
  },
  computed: {
    normalisedInputClass() {
      return isUndefined(this.inputClass) ? "form-control" : this.inputClass
    },
  },
  methods: {
    optionValue: function(option) {
      return get(option, this.valueKey)
    },
    optionLabel: function(option) {
      return get(option, this.labelKey)
    },
    handleChange: function(event) {
      let value = get(event, "target.value")
      let selectedOption = this.findOptionByValue(value)

      this.updateValue(value, selectedOption)
    },
    findOptionByValue: function(value) {
      return find(this.options, { [this.valueKey]: value })
    },
  },
}
</script>

<style></style>

<docs>
  ```jsx
  <div>
    <vs-dropdown
      label="choose one"
      :options="[ { label: 'option 1', value: 1 }, { label: 'option 2', value: 2 } ]"
      :value="null"
    />
  </div>
  ```
</docs>
