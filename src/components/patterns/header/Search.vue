<template>
  <component
    :is="type"
    class="vs-search"
    data-toggle-trigger
    @click="triggerToggle()"
    aria-haspopup="true"
    aria-expanded="false"
  >
    <span class="sr-only">Toggle Search</span>
    <vs-svg path="icons/search" height="18" fill="white" />
    <form role="search" action="" method="get" class="vs-search__form" data-toggle-pane>
      <label class="vs-search__label">
        <span class="sr-only">Enter a search term</span>
        <vs-svg path="icons/search" height="18" fill="#D0CECF" />
      </label>
      <input
        class="vs-search__input"
        type="search"
        placeholder="Enter a search term"
        autocomplete="off"
      />
      <button class="vs-search__clear-button">
        <span class="sr-only">Clear search</span>
        <vs-svg path="icons/cross" height="18" fill="#D0CECF" />
      </button>
      <button class="vs-search__submit-button" type="submit">Go</button>
    </form>
  </component>
</template>

<script>
import VsSvg from "../../elements/svg/Svg"

export default {
  name: "VsSearch",
  status: "prototype",
  release: "0.0.1",
  components: { VsSvg },
  data() {
    return {}
  },
  methods: {
    resetMenus() {
      Array.prototype.forEach.call(document.querySelectorAll("[data-toggle-pane]"), pane => {
        pane.classList.remove("expanded")
      })
      Array.prototype.forEach.call(document.querySelectorAll("[data-toggle-trigger]"), trigger => {
        trigger.setAttribute("aria-expanded", false)
      })
    },
    triggerToggle() {
      let thisTrigger = this.$el
      let thisPane = this.$el.querySelector("[data-toggle-pane]")
      if (thisPane.classList.contains("expanded")) {
        thisPane.classList.remove("expanded")
        thisTrigger.setAttribute("aria-expanded", false)
      } else {
        this.resetMenus()
        thisPane.classList.add("expanded")
        thisTrigger.setAttribute("aria-expanded", true)
      }
      thisTrigger.blur()
    },
  },
  props: {
    /**
     * The html element name used for the component
     */
    type: {
      type: String,
      default: "button",
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/text";
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/utilities/screenreaders";
@import "styles/placeholders";
.vs-search {
  @extend %button-reset;
  @extend %main-nav-button-style;

  background-color: $color-thistle-pink;

  &:focus {
    @extend %focus-white;
  }

  &__form {
    background-color: $color-very-light-granite;
    box-shadow: inset 0 8px 6px -6px rgba(0, 0, 0, 0.3), 0 8px 6px -6px rgba(0, 0, 0, 0.3);
    justify-content: space-between;
    display: flex;
    padding: 1rem;
    left: 0;
    opacity: 0;
    position: absolute;
    top: 40px;
    opacity: 0;
    transform: translate3d(0, -100%, 0);
    transition: all 250ms ease-in-out;
    width: 100%;

    &.expanded {
      opacity: 1;
      transform: translate3d(0, 0, 0);
    }
  }

  &__input {
    @extend %reset-clear;
    border: none;
    display: block;
    padding: 7px 30px;
    margin-right: 5px;
    width: 100%;
    position: relative;

    &:focus {
      @extend %focus-pink;
    }
  }

  &__label {
    position: absolute;
    display: block;
    left: 20px;
    top: 20px;
    z-index: 2;
  }

  &__clear-button {
    @extend %button-reset;
    position: absolute;
    right: 70px;
    top: 20px;

    &:focus {
      @extend %focus-white;
    }
  }

  &__submit-button {
    @extend %button-reset;
    background-color: $color-thistle-pink;
    color: $color-white;
    font-size: 1.125rem;
    font-weight: $font-weight-semi-bold;
    padding: 0 10px;
  }
}
</style>

<docs>
  ```jsx
    <vs-search

    />
  ```
</docs>
