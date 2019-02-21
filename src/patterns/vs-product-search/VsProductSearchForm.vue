<template>
  <div class="row c-search c-search__inline-search gutter-44">
    <div class="col-xs-12">
      <form @submit.prevent="submit">
        <div class="row">
          <div class="col-sm-2 col-md-3 col-xs-12">
            <div class="c-search-type">
              <div class="form-group">
                <label for="search-type" class="c-search__label c-search__label--for hidden-xs">
                  {{ text("search_for") }}
                  <span class="c-search__label--for_sub hidden-sm hidden-md">
                    {{ text("search_for_sub") }}
                  </span>
                </label>

                <div class="select-wrap c-search__select-wrap">
                  <vs-dropdown
                    :options="productTypes"
                    :updateValue="partial(handleInputChange, 'productType')"
                    :value="values.productType"
                    class="c-search__search-type form-control"
                    labelKey="text"
                  />
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-md-3 col-xs-12">
            <div class="c-search-location">
              <div class="form-group">
                <label for="search-location" class="c-search__label hidden-xs">{{
                  text("location")
                }}</label>

                <div class="select-wrap c-search__select-wrap">
                  <vs-dropdown
                    :options="regions"
                    :updateValue="partial(handleInputChange, 'location')"
                    :value="values.locations"
                    class="c-search__search-location form-control"
                  />
                </div>
              </div>
              <span class="c-search__in hidden-xs">{{ text("in") }}</span>
            </div>
          </div>

          <div class="col-sm-6 col-xs-12 col-lg-3">
            <div class="row">
              <div class="c-search__button-container">
                <input
                  type="submit"
                  class="btn btn-pink c-search__button--submit"
                  :value="text('search')"
                />
              </div>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import { get, find, matchesProperty, partial, isString } from "lodash"
import Vue from "vue"
import URI from "urijs"
import VsDropdown from "@lewie/vue-dropdown"

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

<style lang="scss" scoped>
input {
  color: $color_thistle_purple;
}
</style>

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
        search_for_sub: 'subtitle',
        location: 'Location',
        in: 'In',
        search: 'Do search'
      }"
      :initial-product-type="product-type-2"
    />
  </div>
  ```
</docs>
