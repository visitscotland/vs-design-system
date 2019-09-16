<template>
  <vs-row>
    <vs-col cols="12">
      <span class="vs-favourites-list__header">{{ listHeader }}</span>
      <button
        class="vs-favourites-list__close-button"
        id="favourites-close-button"
        @click="handleClose()"
      >
        <span class="sr-only">Close the favourites list</span>
        <vs-icon name="close" size="xs" variant="dark" />
      </button>
    </vs-col>

    <vs-col>
      <ul class="list-unstyled row">
        <li v-for="(item, index) in favourites" class="d-flex align-items-center" :key="index">
          <a :href="item.href" class="vs-favourites-list__link mr-3">{{ item.title }}</a>
          <vs-button
            variant="light"
            @click.native="deleteFavourite(item.href)"
            @keydown.native="checkKeydown($event, index === last)"
          >
            <span class="sr-only">Remove from favourites</span>
            <vs-icon name="close" size="xs" variant="reverse-white" />
          </vs-button>
        </li>
      </ul>
    </vs-col>
  </vs-row>
</template>

<script>
import VsIcon from "@components/elements/icon/Icon"
import store from "./favourites2.store"

export default {
  name: "VsFavouritesList2",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsIcon,
  },
  store,
  data() {
    return {}
  },
  computed: {
    favourites() {
      return this.$store.getters["favourites/getFavourites"]
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
    checkFavouritesLength() {
      if (this.favourites.length === 0) {
        this.handleClose()
      }
    },
    checkKeydown($event, isLast) {
      if ($event.key === "Tab" && !$event.shiftKey && isLast) {
        this.handleClose()
      }
    },
    handleClose() {
      this.setFocusOnToggle()
      this.$root.$emit("bv::toggle::collapse", "collapse-favourites")
    },
    deleteFavourite(href) {
      this.$store.dispatch("favourites/deleteFavourite", href)
      this.checkFavouritesLength()
    },
    setFocusOnToggle() {
      setTimeout(() => {
        document.getElementById("favourites-toggle-trigger").focus()
      }, 100)
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
// @import "../../styles/placeholders";
// @import "../../styles/mixins";

.vs-favourites {
  position: relative;
}

.vs-favourites-list__close-button {
  // @extend %button-reset;
  display: inline-block;
  position: absolute;
  right: 1rem;
  height: 50px;
  width: 50px;

  &:hover,
  &:focus {
    // @include focus-underline($color-pink);
  }
}

.vs-favourites-list__wrapper {
  // @extend %default-inset-box-shadow;
  background-color: $gray-tint-7;
  padding: 2.5rem 0;
  width: 100%;
}

.vs-favourites-list__link {
  font-size: 1.5rem;
}

.vs-favourites-list__header {
  color: $gray-shade-2;
  display: inline-block;
  font-size: 2rem;
  font-weight: 300;
  letter-spacing: 1px;
  text-transform: uppercase;
}
</style>

<docs>
  ```jsx
  
  const favourite = require("@/assets/fixtures/favourite.json")

  <div style="position: relative; height: 600px;">
    <vs-favourites-button 
      :href="favourite.href"
      :title="favourite.title"
    />
    <vs-favourites-list
      
    />
  </div>
  ```
</docs>
