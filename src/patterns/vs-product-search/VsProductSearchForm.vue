<template>
  <form @submit.prevent="submit" class="row">
    <vs-dropdown
      class="col"
      :label="text('search_for')"
      :options="productTypes"
      :updateValue="partial(handleInputChange, 'productType')"
      :value="values.productType"
      labelKey="text"
    />

    <div class="form-group hidden-xs">
      <label>&nbsp;</label>
      <div class="m-2">{{ text("in") }}</div>
    </div>

    <vs-dropdown
      class="col"
      :label="text('location')"
      :options="regions"
      :updateValue="partial(handleInputChange, 'location')"
      :value="values.locations"
    />

    <vs-input class="col" :value="text('search')" type="submit" :label="'\xa0'" />
  </form>
</template>

<script>
import { get, find, matchesProperty, partial, isString } from "lodash"
import Vue from "vue"
import URI from "urijs"
import VsDropdown from "../../elements/vs-dropdown/VsDropdown"
import VsInput from "../../elements/VsInput"

export default {
  name: "VsProductSearchForm",
  components: { VsDropdown },
  props: {
    initialProductType: String,
    productTypes: Array,
    regions: Array,
    copy: {
      type: Object,
      default: function() {
        return {
          search_for: "default text",
          search_for_sub: "default text",
          location: "default text",
          in: "default text",
          search: "default text",
        }
      },
    },
    debug: Boolean,
  },
  mounted: function() {
    if (!this.values.productType && this.initialProductType) {
      Vue.nextTick(partial(Vue.set, this.values, "productType", this.initialProductType))
    }
  },
  data: function() {
    return {
      values: {},
    }
  },
  methods: {
    submit() {
      let url
      let productTypeSearchPath = this.getProductType(this.values.productType, "searchPath")

      if (!productTypeSearchPath || !isString(productTypeSearchPath)) {
        return
      }

      url = URI(productTypeSearchPath)

      window.location = url.search(this.getSearchQuery())
    },
    text(key, fallback) {
      return get(this.copy, key, fallback)
    },
    handleInputChange(key, value) {
      Vue.set(this.values, key, value)
    },
    getProductType(value, key) {
      let productType = find(this.productTypes, matchesProperty("value", value))

      return key ? get(productType, key) : productType
    },
    getSearchQuery() {
      let query = {}

      if (this.values.location) {
        query.region = this.values.location
      }

      if (this.values.delegates) {
        query.del = this.values.delegates
      }

      if (this.debug) {
        query.debug = ""
      }

      return query
    },
  },
  computed: {
    partial: function() {
      return partial
    },
  },
}
</script>

<style lang="scss" scoped></style>

<docs>
  ```jsx
  <div>
    <vs-product-search-form
      :product-types="[ 
        { 
          text: 'Product type 1', 
          value: 'product-type-1', 
          searchPath: 'https://businessevents.visitscotland.com/venue/search' 
        },
        { 
          text: 'Product type 2', 
          value: 'product-type-2', 
          searchPath: 'https://businessevents.visitscotland.com/service/search' 
        }
      ]"
      :regions="[ 
        { label: 'Place 1', value: 'Place 1' }, 
        { label: 'Place 2', value: 'Place 2' } 
      ]"
      :copy="{
        search_for: 'Search For',
        search_for_sub: 'products...',
        location: 'Location',
        in: 'In',
        search: 'Do search'
      }"
      initial-product-type="product-type-2"
    />
  </div>
  ```
</docs>
