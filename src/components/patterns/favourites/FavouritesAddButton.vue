<template>
  <vs-button
    class="vs-favourites-add__button"
    @click.native="addFavourite"
    variant="transparent"
    :animate="false"
    size="sm"
  >
    <span class="sr-only">Add to Favourites</span>
    <vs-icon v-if="favourited" name="favourite-filled" size="md" variant="primary" :padding="0" />
    <vs-icon v-else name="favourite" size="md" variant="dark" :padding="0" />
  </vs-button>
</template>

<script>
import VsIcon from "@components/elements/icon/Icon"
import store from "./favourites.store"

export default {
  name: "VsFavouritesAddButton",
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
    favourites() {
      return store.getters["favourites/getFavourites"]
    },
    favouriteItem() {
      return {
        title: this.title,
        href: this.href,
      }
    },
    favourited() {
      return (
        this.favourites.findIndex(favourite => favourite.href === this.favouriteItem.href) !== -1
      )
    },
  },
  methods: {
    addFavourite() {
      store.dispatch("favourites/addFavourite", this.favouriteItem)
    },
  },
}
</script>

<style lang="scss" scoped>
.vs-favourites-add__button {
  display: block;
  position: relative;
  height: 40px;
  padding: 0 $spacer-2;
  margin: 0 0 $spacer-2 $spacer-2;
}
</style>

<docs>
  ```jsx

    <vs-favourites-add-button
      :href="favourite.href"
      :title="favourite.title"
    />
  ```
</docs>
