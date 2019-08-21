<template>
  <component
    :is="type"
    class="vs-favourites__button"
    @click.native="increment('Incrementing count from ' + count + ' to ' + (count + 1))"
    v-b-toggle.collapse-favourites
  >
    <span class="sr-only">Add to Favourites</span>
    <span class="vs-favourites__count" v-if="count <= 1">
      <span class="sr-only">Current favourites count:</span> {{ count }}
    </span>
    <vs-icon v-if="this.favourites.length" name="favourite-filled" size="sm" variant="primary" />
    <vs-icon v-else name="favourite" size="sm" variant="primary" />
  </component>
</template>

<script>
import VsIcon from "../../../../elements/icon/Icon"
import { VBToggle } from "bootstrap-vue"
import store from "./favourites.store"

export default {
  name: "VsFavouritesButton",
  status: "prototype",
  release: "0.0.1",
  components: { VsIcon },
  data() {
    return {
      favourites: [],
    }
  },
  directives: { "b-toggle": VBToggle },
  props: {
    /**
     * The html element name used for the component
     */
    type: {
      type: String,
      default: "button",
    },
  },
  store,
  computed: {
    count() {
      return this.$store.state.favourites.count
    },
  },
  methods: {
    increment(message) {
      console.log("increment")
      this.$store.dispatch("favourites/increment", message)
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

.vs-favourites {
  &__button {
    @extend %button-reset;
    @extend %main-nav-button-style;

    position: relative;

    &:focus {
      @extend %focus-pink-inset;
    }
  }

  &__count {
    color: $color-white;
    display: block;
    font-size: 0.75rem;
    left: 0;
    position: absolute;
    top: 10px;
    width: 100%;

    @include media-breakpoint-up(md) {
      top: 14px;
    }
  }
}
</style>

<docs>
  ```jsx
    <vs-favourites-button

    />
  ```
</docs>
