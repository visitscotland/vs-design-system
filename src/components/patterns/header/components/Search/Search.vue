<template>
  <component :is="type" class="vs-search">
    <b-collapse id="collapse-search" class="vs-search__form-wrapper">
      <vs-container>
        <vs-row>
          <vs-col>
            <form role="search" action method="get" class="vs-search__form">
              <label for="searchinput" class="vs-search__label">
                <span class="sr-only">Enter a search term</span>
                <vs-icon name="search" size="sm" variant="primary" />
              </label>
              <input
                class="vs-search__input"
                type="search"
                placeholder="Enter a search term"
                autocomplete="off"
                v-model="searchTerm"
                id="searchinput"
              />
              <button
                v-if="searchTerm.length"
                class="vs-search__clear-button"
                @click.prevent="clearSearchField()"
              >
                <span class="sr-only">Clear search</span>
                <!-- TODO: convert to vs-icon when colours are finalised -->
                <vs-icon name="close" size="xs" variant="light" />
              </button>
              <button
                @keydown="checkKeydown($event)"
                class="vs-search__submit-button"
                type="submit"
              >
                Go
              </button>
            </form>
          </vs-col>
        </vs-row>
      </vs-container>
    </b-collapse>
  </component>
</template>

<script>
import VsIcon from "../../../../elements/icon/Icon"
import { BCollapse } from "bootstrap-vue"

export default {
  name: "VsSearch",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsIcon,
    BCollapse,
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
  },
  props: {
    /**
     * The html element name used for the component
     */
    type: {
      type: String,
      default: "div",
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/text";
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/utilities/screenreaders";
@import "../../styles/placeholders";

.vs-search__form-wrapper {
  background-color: $gray-tint-7;
  box-shadow: inset 0 8px 6px -6px rgba(0, 0, 0, 0.3), 0 8px 6px -6px rgba(0, 0, 0, 0.3);
  justify-content: space-between;
  padding: 1rem;
  left: 0;
  width: 100%;
  z-index: 1;
}

.vs-search__form {
  display: flex;
}

.vs-search__input {
  @extend %reset-clear;
  border: none;
  display: block;
  padding: 7px 7px 7px 0;
  width: 100%;
  position: relative;

  &:focus {
    @extend %focus-pink-inset;
  }
}

.vs-search__label {
  background-color: $color-white;
  display: flex;
  align-items: center;
  margin: 0;
}

.vs-search__clear-button {
  @extend %button-reset;
  background: $color-white;
  display: flex;
  align-items: center;
  padding: 0 10px;

  &:focus {
    @extend %focus-pink-inset;
  }
}

.vs-search__submit-button {
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
    <vs-search
    />
  </div>
  ```
</docs>
