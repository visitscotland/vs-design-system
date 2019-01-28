<template>
  <select :id="id" :class="selectClass" @change="handleChange" :value="value">
    <option v-for="option in options" :value="optionValue(option)">{{
      optionLabel(option)
    }}</option>
  </select>
</template>

<script>
import { get, find } from "lodash"

export default {
  name: "Dropdown",
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
    id: String,
    selectClass: String,
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
    <Dropdown
      :options="[ { label: 'option 1', value: 1 }, { label: 'option 2', value: 2 } ]"
      :value="null"
    ></Dropdown>
  </div>
  ```
</docs>
