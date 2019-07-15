<template>
  <component :is="type" class="vs-dropdown" :aria-label="name">
    <button
      class="vs-dropdown__button"
      data-toggle-trigger
      @click="triggerToggle()"
      aria-haspopup="true"
      aria-expanded="false"
    >
      <span> <span class="sr-only">Toggle submenu for</span>{{ name }} </span>
      <div class="vs-dropdown__icon-wrapper">
        <vs-svg path="icons/chevron-down" height="5" fill="white" />
      </div>
    </button>
    <ul aria-role="menubar" :aria-label="name" class="vs-dropdown__list" data-toggle-pane>
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
  </component>
</template>

<script>
import VsSvg from "../../elements/svg/Svg"

export default {
  name: "VsDropdown",
  status: "prototype",
  release: "0.0.1",
  components: { VsSvg },
  data() {
    return {}
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
    resetMenus() {
      Array.prototype.forEach.call(document.querySelectorAll("[data-toggle-pane]"), pane => {
        pane.classList.remove("expanded")
      })
      Array.prototype.forEach.call(document.querySelectorAll("[data-toggle-trigger]"), trigger => {
        trigger.setAttribute("aria-expanded", false)
      })
    },
    triggerToggle() {
      let thisTrigger = this.$el.querySelector("[data-toggle-trigger]")
      let thisPane = this.$el.querySelector("[data-toggle-pane]")
      if (thisPane.classList.contains("expanded")) {
        thisPane.classList.remove("expanded")
        thisTrigger.setAttribute("aria-expanded", false)
      } else {
        this.resetMenus()
        thisPane.classList.add("expanded")
        thisTrigger.setAttribute("aria-expanded", true)
      }
      thisTrigger.blur()
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/text";
@import "~bootstrap/scss/utilities/display";
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/utilities/screenreaders";
@import "styles/placeholders";

.vs-dropdown {
  &__list {
    @extend %list-reset;
    background-color: darken($color-heather-purple, 10%);
    left: 0;
    opacity: 0;
    position: absolute;
    top: 28px;
    opacity: 0;
    transform: translate3d(0, -130%, 0);
    transition: all 250ms ease-in-out;
    width: calc(100% + 2rem);

    &.expanded {
      opacity: 1;
      transform: translate3d(0, 0, 0);
      height: 100vh;
    }
  }

  &__list-item {
    min-height: 1rem;
  }

  &__link {
    box-shadow: inset 0 0 0 0 transparent;
    font-weight: $font-weight-normal;
    font-size: 1.125rem;
    border-bottom: 1px solid rgba(255, 255, 255, 0.2);
    display: none;
    padding: 20px 1rem;
    width: 100%;
    color: $color-white;
    padding: 20px 1rem 0px 1rem;
    position: relative;
    transition: all 250ms ease-in-out;

    &:focus {
      background-color: $color-thistle-pink;
      @extend %focus-white;
    }

    &:hover {
      background-color: $color-thistle-pink;
      color: $color-white;
    }
    .vs-dropdown__list.expanded & {
      display: flex;
      align-items: center;
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
  ```
</docs>
