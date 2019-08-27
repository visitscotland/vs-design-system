<template>
  <component
    :is="type"
    class="vs-favourites__button"
    @click="handleClick()"
    id="favourites-toggle-trigger"
    v-b-toggle.collapse-favourites
  >
    <span class="sr-only">Add to Favourites</span>
    <span class="vs-favourites__count" v-if="favouritesCount > 0">
      <span class="sr-only">Current favourites count:</span> {{ favouritesCount }}
    </span>
    <vs-icon v-if="favouritesCount > 0" name="favourite-filled" size="sm" variant="primary" />
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
    return {}
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
    title: {
      type: String,
    },
    href: {
      type: String,
    },
  },
  store,
  computed: {
    favouritesCount() {
      return this.$store.getters["favourites/getFavouritesCount"]
    },
    favourites() {
      return this.$store.getters["favourites/getFavourites"]
    },
    favouriteItem() {
      return {
        title: this.title,
        href: this.href,
      }
    },
  },
  methods: {
    handleClick() {
      var inArray = this.favourites.filter(favourite => favourite.href === this.favouriteItem.href)
      if (inArray.length === 0) {
        this.$store.dispatch("favourites/addFavourite", this.favouriteItem)
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
@import "../../styles/placeholders";

.vs-favourites {
  &__button {
    @extend %button-reset;
    @extend %main-nav-button-style;

    position: relative;

    &:hover,
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

    const favourite = require("../../../../../assets/fixtures/favourite.json")

    <vs-favourites-button
      :href="favourite.href"
      :title="favourite.title"
    />
  ```
</docs>
