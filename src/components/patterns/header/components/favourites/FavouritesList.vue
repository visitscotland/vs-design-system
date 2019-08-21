<template>
  <component :is="type" class="vs-favourites">
    <b-collapse id="collapse-favourites" class="vs-favourites__wrapper">
      <vs-container>
        <vs-row>
          <vs-col>
            <h4>Favourites List</h4>
            <ul class="list-unstyled">
              <li
                v-for="(item, index) in favourites"
                class="d-flex align-items-center"
                :key="index"
              >
                <a :href="item.href" class="mr-3">{{ item.title }}</a>
                <vs-button variant="light" @click.native="deleteFavourite(item.href)">
                  <span class="sr-only">Remove from favourites</span>
                  <vs-icon name="close" size="xs" variant="reverse-white" />
                </vs-button>
              </li>
            </ul>
          </vs-col>
        </vs-row>
      </vs-container>
    </b-collapse>
  </component>
</template>

<script>
import { BCollapse } from "bootstrap-vue"
import VsIcon from "../../../../elements/icon/Icon"
import store from "./favourites.store"

export default {
  name: "VsFavouritesList",
  status: "prototype",
  release: "0.0.1",
  components: {
    BCollapse,
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
  methods: {
    deleteFavourite(href) {
      console.log(href)
      this.$store.dispatch("favourites/deleteFavourite", href)
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
@import "../../styles/placeholders";

.vs-favourites__wrapper {
  background-color: $gray-tint-7;
  box-shadow: inset 0 8px 6px -6px rgba(0, 0, 0, 0.3), 0 8px 6px -6px rgba(0, 0, 0, 0.3);
  display: flex;
  justify-content: space-between;
  left: 0;
  padding: 1rem;
  width: 100%;
  z-index: 1;
}
</style>

<docs>
  ```jsx
  <div style="position: relative; height: 100px;">
    <vs-favourites-button />
    <vs-favourites-list
    />
  </div>
  ```
</docs>
