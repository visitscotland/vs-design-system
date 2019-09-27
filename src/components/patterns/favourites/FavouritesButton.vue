<template>
  <vs-button
    class="vs-favourites__button"
    @click.native="addFavourite"
    focus-style="underline"
    focus-colour="pink"
    variant="transparent"
  >
    <span class="sr-only">Add to Favourites</span>
    <span class="vs-favourites__button__count" v-if="favouritesCount > 0">
      <span class="sr-only">Current favourites count:</span> {{ favouritesCount }}
    </span>
    <vs-icon v-if="favouritesCount > 0" name="favourite-filled" size="sm" variant="primary" />
    <vs-icon v-else name="favourite" size="sm" variant="primary" />
  </vs-button>
</template>

<script>
import VsIcon from "@components/elements/icon/Icon"
import store from "./favourites.store"

export default {
  name: "VsFavouritesButton",
  status: "prototype",
  release: "0.0.1",
  components: { VsIcon },
  props: {
    title: {
      type: String,
    },
    href: {
      type: String,
    },
  },
  // store,
  computed: {
    favouritesCount() {
      return store.getters["favourites/getFavouritesCount"]
    },
    favourites() {
      return store.getters["favourites/getFavourites"]
    },
    favouriteItem() {
      return {
        title: this.title,
        href: this.href,
      }
    },
  },
  methods: {
    addFavourite() {
      if (store.dispatch("favourites/addFavourite", this.favouriteItem)) {
        this.addFavouriteSuccess()
      }
    },
    addFavouriteSuccess() {
      /**
       * Emitted when a favourited is successfully added to the
       * favourites list
       */
      this.$emit("favouriteAdded")
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/text";
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/utilities/screenreaders";
// @import "../../styles/placeholders";

.vs-favourites__button {
  position: relative;
  width: 40px;

  @include media-breakpoint-up(md) {
    width: 50px;
  }
}

.vs-favourites__button__count {
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
</style>

<docs>
  ```jsx

    const favourite = require("@/assets/fixtures/favourite.json")

    <vs-favourites-button
      :href="favourite.href"
      :title="favourite.title"
    />
  ```
</docs>
