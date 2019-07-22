<template>
  <component :is="type" class="vs-favourites">
    <button
      class="vs-favourites__button"
      @click="addToFavourites({ url: 'http://www.visitscotland.com', title: 'Homepage' })"
    >
      <span class="sr-only">Add to Favourites</span>
      <span class="vs-favourites__count" v-if="this.favourites.length">
        <span class="sr-only">Current favourites count:</span> {{ this.displayCount }}
      </span>
      <vs-svg
        v-if="this.favourites.length"
        path="icons/favourite-filled"
        height="20"
        fill="#700e57"
      />
      <vs-svg v-else path="icons/favourite" height="18" fill="#929091" />
    </button>
  </component>
</template>

<script>
import VsSvg from "../../../elements/svg/Svg"

export default {
  name: "VsFavourites",
  status: "prototype",
  release: "0.0.1",
  components: { VsSvg },
  data() {
    return {
      favourites: [],
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
  },
  computed: {
    displayCount: function() {
      return this.favourites.length > 99 ? "99+" : this.favourites.length
    },
  },
  watch: {
    favourites(oldValue, newValue) {},
  },
  methods: {
    addToFavourites(favourite) {
      this.$root.$emit("resetMenus")
      this.$el.blur()
      if (this.favourites.indexOf(favourite) === -1) {
        this.favourites.push(favourite)
      }
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
    top: 11px;
    width: 100%;
  }
}
</style>

<docs>
  ```jsx
    <vs-favourites

    />
  ```
</docs>
