<template>
  <component :is="type" class="vs-dropdown" :aria-label="name">
    <button
      class="vs-dropdown__button"
      data-toggle-trigger
      @click="triggerToggle()"
      aria-haspopup="true"
      :aria-expanded="show ? 'true' : 'false'"
    >
      <span> <span class="sr-only">Toggle submenu for </span>{{ name }} </span>
      <div class="vs-dropdown__icon-wrapper">
        <vs-svg path="icons/chevron-down" height="5" fill="white" />
      </div>
    </button>
    <transition name="slide-fade">
      <ul
        aria-role="menubar"
        :aria-label="name"
        class="vs-dropdown__list"
        :class="{ expanded: show }"
        data-toggle-pane
        v-if="show"
      >
        <li
          role="none"
          class="vs-dropdown__list-item"
          :class="{ 'vs-dropdown__list-item--active': link.isActive }"
          v-for="(link, index) in dropdownList"
          :key="index"
        >
          <a
            class="vs-dropdown__link"
            :href="link.href"
            :target="link.isExternal ? '_blank' : false"
            :data-di-id="link.trackingID"
          >
            {{ link.title }}
            <div v-if="link.isExternal" class="vs-dropdown__external-icon-wrapper">
              <vs-svg path="icons/external-link" height="10" fill="white" />
            </div>
          </a>
        </li>
      </ul>
    </transition>
  </component>
</template>

<script>
import VsSvg from "../../../elements/svg/Svg"

export default {
  name: "VsDropdown",
  status: "prototype",
  release: "0.0.1",
  components: { VsSvg },
  data() {
    return {
      show: false,
    }
  },
  props: {
    /**
     * The html element name used for the component
     */
    type: {
      type: String,
      default: "nav",
    },
    dropdownList: {
      type: Array,
      required: true,
    },
    name: {
      type: String,
    },
  },
  methods: {
    triggerToggle() {
      let currentState = this.show
      this.$root.$emit("resetMenus")
      if (currentState === false) {
        this.show = true
      }
      this.$el.querySelector("[data-toggle-trigger]").blur()
    },
    reset() {
      this.show = false
    },
  },
  mounted() {
    this.$root.$on("resetMenus", this.reset)
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/text";
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/utilities/screenreaders";
@import "../styles/placeholders";
@import "../styles/animations";

.vs-dropdown {
  &__list {
    @extend %list-reset;
    background-color: shade($color-heather-purple, 20%);
    left: 0;
    position: absolute;
    top: 28px;
    width: 100%;
    height: 100vh;
  }

  &__link {
    align-items: center;
    border-bottom: 1px solid tint($color-heather-purple, 5%);
    box-shadow: inset 0 0 0 0 transparent;
    color: $color-white;
    display: flex;
    font-size: 1.5rem;
    font-weight: $font-weight-normal;
    padding: 0.75rem 1.25rem;
    position: relative;
    transition: all 250ms ease-in-out;
    width: 100%;

    &:focus {
      @extend %focus-white-inset;
    }

    &:hover {
      color: $color-white;
    }
  }

  &__external-icon-wrapper {
    display: flex;
    margin-left: 5px;
  }

  &__button {
    @extend %button-reset;
    @extend %uni-nav-button-style;
  }

  &__icon-wrapper {
    margin-left: 5px;
    transition: transform 250ms;

    .vs-dropdown__button[aria-expanded="true"] & {
      transform: rotate(180deg);
      transform-origin: 50% 54%;
    }
  }
}
</style>

<docs>
  ```jsx
  <div style="position: relative; height: 100vh;">
    <vs-dropdown
        name="Our sites"
        :dropdown-list="[
            {
                title: 'Business Events',
                href: 'https://businessevents.visitscotland.com',
                isExternal: true,
                isActive: false,
                trackingID: 1
            },
            {
                title: 'Travel Trade',
                href: 'https://traveltrade.visitscotland.org',
                isExternal: true,
                isActive: false,
                trackingID: 1
            },
            {
                title: 'Media Centre',
                href: 'http://mediacentre.visitscotland.org',
                isExternal: true,
                isActive: false,
                trackingID: 1
            },
            {
                title: 'Corporate',
                href: 'https://www.visitscotland.org',
                isExternal: true,
                isActive: false,
                trackingID: 1
            }
        ]"
    />
  </div>
  ```
</docs>
