<template>
  <vs-button
    class="vs-favourites__button p-1 position-relative"
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
@import "~bootstrap/scss/utilities/position";
@import "~bootstrap/scss/utilities/spacing";
@import "~bootstrap/scss/utilities/screenreaders";

.vs-favourites__button__count {
  color: $color-white;
  display: block;
  font-size: 0.75rem;
  width: 100%;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
}
</style>

<docs>
  ```jsx

    <vs-favourites-button
      :href="favourite.href"
      :title="favourite.title"
    />
  ```
</docs>
