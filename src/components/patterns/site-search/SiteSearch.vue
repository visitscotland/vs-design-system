<template>
  <component :is="type" class="vs-site-search">
    <!-- <b-collapse
      v-model="open"
      class="vs-site-search__form-wrapper"
      id="vs-site-search__form-wrapper"
    >
      <vs-container>
        <vs-row>
          <vs-col> -->
    <form role="search" action method="get" class="vs-site-search__form">
      <label for="search-input" class="vs-site-search__label">
        <span class="sr-only">Enter a search term</span>
        <vs-icon name="search" size="sm" variant="primary" />
      </label>
      <input
        class="vs-site-search__input"
        type="search"
        placeholder="Enter a search term"
        autocomplete="off"
        v-model="searchTerm"
        ref="searchInput"
        id="search-input"
      />
      <button
        v-if="searchTerm.length"
        class="vs-site-search__clear-button"
        @click.prevent="clearSearchField()"
      >
        <span class="sr-only">Clear search</span>
        <!-- TODO: convert to vs-icon when colours are finalised -->
        <vs-icon name="close" size="xs" variant="light" />
      </button>
      <button @keydown="checkKeydown($event)" class="vs-site-search__submit-button" type="submit">
        Go
      </button>
    </form>
    <!-- </vs-col>
        </vs-row>
      </vs-container>
    </b-collapse> -->
  </component>
</template>

<script>
import VsIcon from "@components/elements/icon/Icon"
import siteSearchStore from "../header/components/Search/site-search.store"

export default {
  name: "VsSiteSearch",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsIcon,
  },
  data() {
    return {
      searchTerm: "",
      open: false,
    }
  },
  props: {
    /**
     * The html element name used for the component
     */
    type: {
      type: String,
      default: "div",
    },
    closeFocusElement: {
      type: Element,
      default: null,
    },
  },
  computed: {
    searchStoreOpen() {
      return siteSearchStore.state.siteSearch.open
    },
  },
  watch: {
    searchStoreOpen(newVal, oldVal) {
      this.open = newVal

      if (this.open) {
        setTimeout(() => {
          this.$refs.searchInput.focus()
        })
      }
    },
  },
  methods: {
    close() {
      // this.setFocusOnToggle()
      siteSearchStore.dispatch("siteSearch/close", this.closeFocusElement)
    },
    checkKeydown($event) {
      if ($event.key === "Tab" && !$event.shiftKey) {
        this.close()
      }
    },
    clearSearchField() {
      this.searchTerm = ""
    },
    // setFocusOnToggle() {
    //   setTimeout(() => {
    //     document.getElementById("search-toggle-trigger").focus()
    //   }, 100)
    // },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/text";
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/utilities/screenreaders";

@import "../header/styles/placeholders";
@import "../header/styles/mixins";

.vs-site-search__form-wrapper {
  @extend %default-inset-box-shadow;
  background-color: $gray-tint-7;
  padding: 1rem;
  width: 100%;
}

.vs-site-search__form {
  display: flex;
}

.vs-site-search__input {
  @extend %reset-clear;
  border: none;
  display: block;
  padding: 7px 7px 7px 0;
  width: 100%;
  position: relative;

  &:focus {
    @include focus-underline($color-pink, -1px);
  }
}

.vs-site-search__label {
  background-color: $color-white;
  display: flex;
  align-items: center;
  margin: 0;
}

.vs-site-search__clear-button {
  @extend %button-reset;
  background: $color-white;
  display: flex;
  align-items: center;
  padding: 0 10px;

  &:focus {
    @extend %focus-pink-inset;
  }
}

.vs-site-search__submit-button {
  @extend %button-reset;
  @extend %button-pink;
  font-size: 1.125rem;
  font-weight: $font-weight-semi-bold;
  padding: 0 10px;
  margin-left: 5px;
}
</style>

<docs>
  ```jsx
  <div style="position: relative; height: 100px;">
    <vs-site-search
    />
  </div>
  ```
</docs>
