<template>
  <component :is="type" class="vs-dropdown" :aria-label="name" v-click-outside="reset">
    <button
      class="vs-dropdown__button"
      ref="trigger"
      data-test-trigger
      @click="triggerToggle()"
      aria-haspopup="true"
      :aria-expanded="show ? 'true' : 'false'"
    >
      <span> <span class="sr-only">Toggle menu for </span>{{ name }}</span>
      <div class="vs-dropdown__icon-wrapper">
        <vs-svg path="icons/chevron-down" height="5" fill="white" />
      </div>
    </button>
    <transition name="slide-fade">
      <ul
        aria-role="menubar"
        :aria-label="name"
        class="vs-dropdown__list list-unstyled"
        :class="{ expanded: show }"
        data-toggle-pane
        v-show="show"
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
            :data-vs-track="formattedTrackingId(link.title)"
            @keydown="checkKeydown($event, index === last)"
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
  computed: {
    last() {
      return Object.keys(this.dropdownList).length - 1
    },
  },
  methods: {
    checkKeydown($event, isLast) {
      if ($event.key === "Tab" && !$event.shiftKey && isLast) {
        this.show = false
      }
    },
    formattedTrackingId(title) {
      var formattedTitle = title.toLowerCase().replace(/\s+/g, "-")
      var formattedDropdownName = this.name.toLowerCase().replace(/\s+/g, "-")
      return "link." + formattedDropdownName + "nav." + formattedTitle
    },
    onClose() {
      this.show = false
    },
    reset() {
      this.show = false
    },
    triggerToggle() {
      let currentState = this.show
      this.$root.$emit("resetMenus")
      if (currentState === false) {
        this.show = true
      }
    },
  },
  directives: {
    "click-outside": {
      bind: function(el, binding, vNode) {
        // Provided expression must evaluate to a function.
        if (typeof binding.value !== "function") {
          const compName = vNode.context.name
          let warn = `[Vue-click-outside:] provided expression '${
            binding.expression
          }' is not a function, but has to be`
          if (compName) {
            warn += `Found in component '${compName}'`
          }

          console.warn(warn)
        }
        // Define Handler and cache it on the element
        const bubble = binding.modifiers.bubble
        const handler = e => {
          if (bubble || (!el.contains(e.target) && el !== e.target)) {
            binding.value(e)
          }
        }
        el.__vueClickOutside__ = handler

        // add Event Listeners
        document.addEventListener("click", handler)
      },

      unbind: function(el, binding) {
        // Remove Event Listeners
        document.removeEventListener("click", el.__vueClickOutside__)
        el.__vueClickOutside__ = null
      },
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
@import "~bootstrap/scss/type";
@import "~bootstrap/scss/utilities/screenreaders";
@import "../styles/placeholders";
@import "../styles/animations";

.vs-dropdown__list {
  background-color: shade($color-theme-primary, 20%);
  color: $color-white;
  left: 0;
  min-width: 200px;
  position: absolute;
  top: 28px;
  width: auto;

  .vs-dropdown--language & {
    left: auto;
    right: 0;
  }

  @include media-breakpoint-down(md) {
    height: 100vh;
    width: 100%;
  }
}

.vs-dropdown__link {
  align-items: center;
  border-bottom: 1px solid tint($color-theme-primary, 5%);
  box-shadow: inset 0 0 0 0 transparent;
  color: $color-white;
  display: flex;
  font-size: 1.5rem;
  font-weight: $font-weight-light;
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

.vs-dropdown__external-icon-wrapper {
  display: flex;
  margin-left: 5px;
}

.vs-dropdown__button {
  @extend %button-reset;
  @extend %uni-nav-button-style;
}

.vs-dropdown__icon-wrapper {
  margin-left: 5px;
  transition: transform 250ms;

  .vs-dropdown__button[aria-expanded="true"] & {
    transform: rotate(180deg);
    transform-origin: 50% 54%;
  }
}
</style>

<docs>
  ```jsx
  const ourSites = require("../../../../assets/fixtures/ourSites.json")

  <div style="position: relative; height: 100vh; display: flex; justify-content: space-between;">
    <vs-dropdown
        name="Our sites"
        :dropdown-list="ourSites"
    />
  </div>
  ```
</docs>
