<template>
  <b-form
    role="search"
    class="d-flex align-items-start py-2 py-md-4"
    action
    method="get"
    :novalidate="true"
    :validated="validated"
    @focus="focusOnInput"
    @submit="onSubmit"
    tabindex="-1"
  >
    <div class="d-flex flex-column flex-grow-1 position-relative">
      <label for="search-input" class="position-absolute vs-site-search__label">
        <span class="sr-only">{{ labelText }}</span>
        <vs-icon name="search" size="sm" variant="dark" />
      </label>

      <vs-form-input
        type="search"
        class="px-9 vs-site-search__input"
        :placeholder="labelText"
        autocomplete="off"
        size="lg"
        v-model="searchTerm"
        :state="validated"
        ref="searchInput"
        id="search-input"
        @input.native="onInput"
      />

      <b-form-invalid-feedback v-if="validated === false" :state="validated">{{
        validationText
      }}</b-form-invalid-feedback>
      <div v-if="searchTerm.length" class="position-absolute vs-search__clear-container">
        <vs-button
          variant="transparent"
          type="button"
          @click.native.prevent="clearSearchFieldAndFocus()"
        >
          <span class="sr-only">{{ clearButtonText }}</span>
          <vs-icon name="close" size="xs" variant="dark" />
        </vs-button>
      </div>
    </div>
    <vs-button
      type="submit"
      class="px-md-5"
      size="lg"
      :variant="'primary-pink'"
      focus-style="outset"
      focus-colour="pink"
      >{{ submitButtonText }}</vs-button
    >
  </b-form>
</template>

<script>
import VsIcon from "@components/elements/icon/Icon"
import VsFormInput from "@components/elements/form-input/FormInput"
import drawerStore from "../header/components/Drawer/drawer.store"

import {
  BForm,
  BFormInput,
  BFormInvalidFeedback,
  BInputGroup,
  BInputGroupAppend,
  BInputGroupPrepend,
} from "bootstrap-vue"

export default {
  name: "VsSiteSearch",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsIcon,
    BForm,
    VsFormInput,
    BFormInvalidFeedback,
    BInputGroup,
    BInputGroupAppend,
    BInputGroupPrepend,
  },
  props: {
    labelText: {
      type: String,
      default: "Enter a search term",
    },
    clearButtonText: {
      type: String,
      default: "Clear search",
    },
    submitButtonText: {
      type: String,
      default: "Go",
    },
    validationText: {
      type: String,
      default: "Please enter a search term.",
    },
  },
  data() {
    return {
      searchTerm: "",
      validated: null,
    }
  },
  computed: {
    drawerModule() {
      return drawerStore.getters["drawer/module"]
    },
    isValid() {
      return this.searchTerm.length > 0
    },
  },
  watch: {
    drawerModule(newValue) {
      if (newValue !== "site-search") {
        this.clearSearchField()
        this.resetValidation()
      }
    },
  },
  methods: {
    clearSearchField() {
      this.searchTerm = ""
    },
    focusOnInput() {
      this.$refs.searchInput.$el.focus()
    },
    clearSearchFieldAndFocus() {
      this.clearSearchField()
      this.focusOnInput()
    },
    onSubmit($event) {
      if (!this.isValid) {
        $event.preventDefault()
        this.validated = false
      } else {
        return true
      }
    },
    onInput() {
      this.validated = this.isValid ? null : false
    },
    resetValidation() {
      this.validated = null
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/text";
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/utilities/spacing";
@import "~bootstrap/scss/utilities/sizing";
@import "~bootstrap/scss/utilities/position";
@import "~bootstrap/scss/utilities/screenreaders";
@import "~bootstrap/scss/forms";
@import "~bootstrap/scss/input-group";

.vs-site-search__input {
  @extend %reset-clear;
}

.vs-site-search__label {
  padding: 5px;
}

.vs-search__clear-container {
  right: 0;
  padding: 5px;
}
</style>

<docs>
  ```jsx
  <div>
    <vs-site-search />
  </div>
  ```
</docs>
