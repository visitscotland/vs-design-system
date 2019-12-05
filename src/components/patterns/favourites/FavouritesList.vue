<template>
  <div v-if="favourites.length">
    <span class="vs-favourites-list__header text-uppercase">{{ listHeader }}</span>
    <ul class="list-unstyled">
      <li v-for="(item, index) in favourites" class="d-flex align-items-center w-100" :key="index">
        <a :href="item.href" class="vs-favourites-list__link">{{ item.title }}</a>
        <vs-button
          class="p-2"
          :animate="false"
          variant="transparent"
          @click.native.prevent="deleteFavourite(item.href)"
        >
          <span class="sr-only">Remove from favourites</span>
          <vs-icon name="close" size="xs" />
        </vs-button>
      </li>
    </ul>
  </div>
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
    Test Add Favourite Item 1
    <vs-favourites-toggle-button 
      :href="favourite.href"
      :title="favourite.title"
    >
    </vs-favourites-toggle-button>
    Test Add Favourite Item 2
     <vs-favourites-toggle-button 
      href="http:www.visitscotland.org"
      title="VisitScotland Corporate Website"
    >
    </vs-favourites-toggle-button>
    Test Add Favourite Item 3
     <vs-favourites-toggle-button 
      href="https://www.visitscotland.com/destinations-maps/st-andrews/"
      title="St Andrews"
    >
    </vs-favourites-toggle-button>
    Favourites Count
    <vs-favourites-view-button>
    </vs-favourites-view-button>
    <vs-favourites-list/>
  </div>
  ```
</docs>
