<template>
  <vs-row>
    <vs-col cols="12">
      <span class="vs-favourites-list__header text-uppercase">{{ listHeader }}</span>
    </vs-col>

    <vs-col>
      <ul class="list-unstyled row">
        <li
          v-for="(item, index) in favourites"
          class="d-flex align-items-center  col-md-6 col-xl-4"
          :key="index"
        >
          <a :href="item.href" class="vs-favourites-list__link mr-3">{{ item.title }}</a>
          <vs-button variant="transparent" @click.native.prevent="deleteFavourite(item.href)">
            <span class="sr-only">Remove from favourites</span>
            <vs-icon name="close" size="xs" />
          </vs-button>
        </li>
      </ul>
    </vs-col>
  </vs-row>
</template>

<script>
import VsIcon from "@components/elements/icon/Icon"
import favouritesStore from "./favourites.store"

export default {
  name: "VsFavouritesList",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsIcon,
  },
  data() {
    return {}
  },
  computed: {
    favourites() {
      return favouritesStore.getters["favourites/getFavourites"]
    },
    last() {
      return this.favourites.length - 1
    },
  },
  props: {
    listHeader: {
      type: String,
      default: "Favourites list",
    },
  },
  methods: {
    deleteFavourite(href) {
      favouritesStore.dispatch("favourites/deleteFavourite", href)
      this.$emit("favourite-deleted")
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/text";
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/type";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/utilities/spacing";
@import "~bootstrap/scss/utilities/screenreaders";

.vs-favourites-list__link {
  font-size: 1.5rem;
}

.vs-favourites-list__header {
  color: $color-gray-shade-2;
  font-size: 2rem;
  font-weight: 300;
  letter-spacing: 1px;
}
</style>

<docs>
  ```jsx

  <div style="position: relative; height: 600px;">
    <vs-favourites-button 
      :href="favourite.href"
      :title="favourite.title"
    />
    <vs-favourites-list/>
  </div>
  ```
</docs>
