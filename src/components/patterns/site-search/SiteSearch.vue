<template>
  <b-form
    inline
    role="search"
    class="d-flex align-items-start"
    action
    method="get"
    :novalidate="true"
    :validated="validated"
    @focus="onFocus"
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
        class="px-5 vs-site-search__input"
        :placeholder="labelText"
        autocomplete="off"
        size="lg"
        v-model="searchTerm"
        :state="isValid"
        ref="searchInput"
        id="search-input"
      />

      <b-form-invalid-feedback v-if="validated && !isValid" :state="isValid">{{
        validationText
      }}</b-form-invalid-feedback>
      <div v-if="searchTerm.length" class="position-absolute vs-search__clear-container">
        <vs-button
          variant="transparent"
          type="button"
          focus-colour="pink"
          @click.native.prevent="clearSearchField()"
        >
          <span class="sr-only">{{ clearButtonText }}</span>
          <vs-icon name="close" size="xs" variant="dark" />
        </vs-button>
      </div>
    </div>
    <div class="d-flex">
      <vs-button
        type="submit"
        class="px-md-5"
        size="lg"
        :variant="'primary-pink'"
        focus-style="inset"
        focus-colour="white"
        >{{ submitButtonText }}</vs-button
      >
    </div>
  </b-form>
</template>

<script>
import VsIcon from "@components/elements/icon/Icon"
import VsFormInput from "@components/elements/form-input/FormInput"
import {
  BForm,
  BFormInput,
  BFormInvalidFeedback,
  BInputGroup,
  BInputGroupAppend,
  BInputGroupPrepend,
} from "bootstrap-vue"
import headerStore from "../header/"

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
    isValid() {
      return this.searchTerm.length > 1
    },
  },
  methods: {
    clearSearchField() {
      this.searchTerm = ""
    },
    onFocus() {
      this.$refs.searchInput.$el.focus()
    },
    onSubmit($event) {
      if (!this.isValid) {
        $event.preventDefault()
        this.validated = false
      }
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
    <vs-site-search
    />
  </div>
  ```
</docs>
