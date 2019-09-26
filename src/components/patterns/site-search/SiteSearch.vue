<template>
  <b-form
    inline
    role="search"
    class="flex-nowrap"
    action
    method="get"
    @focus="onFocus"
    tabindex="-1"
  >
    <label for="search-input" class="input-group-prepend">
      <span class="sr-only">Enter a search term</span>
      <vs-icon name="search" size="sm" variant="primary" />
    </label>
    <b-form-input
      type="search"
      class="mr-1 flex-grow-1 vs-site-search__input"
      placeholder="Enter a search term"
      autocomplete="off"
      v-model="searchTerm"
      ref="searchInput"
      id="search-input"
    />
    <vs-button
      v-if="searchTerm.length"
      variant="transparent"
      class="ml-n5"
      focus-colour="secondary"
      @click.native.prevent="clearSearchField()"
    >
      <span class="sr-only">Clear search</span>
      <vs-icon name="close" size="xs" variant="light" />
    </vs-button>

    <vs-button
      type="submit"
      class="px-md-4"
      :variant="'primary-pink'"
      focus-style="underline"
      focus-colour="white"
    >
      Go
    </vs-button>
  </b-form>
</template>

<script>
import VsIcon from "@components/elements/icon/Icon"
import { BForm, BInputGroup, BFormInput } from "bootstrap-vue"
import headerStore from "../header/"

export default {
  name: "VsSiteSearch",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsIcon,
    BForm,
    BInputGroup,
    BFormInput,
  },
  data() {
    return {
      searchTerm: "",
    }
  },
  methods: {
    clearSearchField() {
      this.searchTerm = ""
    },
    onFocus() {
      this.$refs.searchInput.focus()
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/text";
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/utilities/spacing";
@import "~bootstrap/scss/utilities/screenreaders";
@import "~bootstrap/scss/forms";
@import "~bootstrap/scss/input-group";

.vs-site-search__input {
  @extend %reset-clear;
}
</style>

<docs>
  ```jsx
  <div>
    <vs-site-search
    />
  </div>
  ```
</docs>
