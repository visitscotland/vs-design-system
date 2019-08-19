<template>
  <component :is="type" class="vs-search">
    <button
      class="vs-search__button"
      ref="trigger"
      id="searchbutton"
      data-test-trigger
      @click="triggerToggle()"
      aria-haspopup="true"
      :aria-expanded="show ? 'true' : 'false'"
    >
      <span class="sr-only">Toggle Search</span>
      <vs-icon name="search" size="sm" variant="reverse-white" />
    </button>
    <transition name="slide-fade">
      <div
        class="vs-search__form-wrapper"
        :class="{ expanded: show }"
        data-toggle-pane
        v-show="show"
      >
        <form role="search" action method="get" class="vs-search__form">
          <label for="search" class="vs-search__label">
            <span class="sr-only">Enter a search term</span>
            <vs-icon name="search" size="sm" variant="primary" />
          </label>
          <input
            class="vs-search__input"
            type="search"
            ref="search"
            placeholder="Enter a search term"
            autocomplete="off"
            v-model="searchTerm"
            id="search"
          />
          <button
            v-if="searchTerm.length"
            class="vs-search__clear-button"
            @click.prevent="clearSearchField()"
          >
            <span class="sr-only">Clear search</span>
            <!-- TODO: convert to vs-icon when colours are finalised -->
            <vs-svg path="icons/cross" height="18" fill="#929091" />
          </button>
          <button @keydown="checkKeydown($event)" class="vs-search__submit-button" type="submit">
            Go
          </button>
        </form>
      </div>
    </transition>
  </component>
</template>

<script>
import VsIcon from "../../../elements/icon/Icon"

export default {
  name: "VsSearch",
  status: "prototype",
  release: "0.0.1",
  components: { VsIcon },
  data() {
    return {
      searchTerm: "",
      show: false,
    }
  },
  methods: {
    checkKeydown($event) {
      if ($event.key === "Tab" && !$event.shiftKey) {
        this.show = false
      }
    },
    clearSearchField() {
      this.searchTerm = ""
    },
    triggerToggle() {
      this.$refs.trigger.focus()
      let currentState = this.show
      this.$root.$emit("resetMenus")
      if (currentState === false) {
        this.show = true
        setTimeout(() => {
          this.$refs.trigger.blur()
          this.$refs.search.focus()
        }, 250)
      }
    },
    reset() {
      this.show = false
    },
  },
  mounted() {
    this.$root.$on("resetMenus", this.reset)
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
@import "../styles/placeholders";
@import "../styles/animations";

.vs-search__button {
  @extend %button-reset;
  @extend %button-pink;
  @extend %main-nav-button-style;

  &[aria-expanded="true"] {
    background-color: $gray-tint-6;

    svg {
      fill: $gray-shade-2 !important;
    }
  }
}

.vs-search__form-wrapper {
  background-color: $gray-tint-7;
  box-shadow: inset 0 8px 6px -6px rgba(0, 0, 0, 0.3), 0 8px 6px -6px rgba(0, 0, 0, 0.3);
  justify-content: space-between;
  padding: 1rem;
  left: 0;
  position: absolute;
  top: 40px;
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
