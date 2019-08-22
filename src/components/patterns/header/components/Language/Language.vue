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
      <span v-if="selectedLanguage !== null">
        <span class="sr-only">Currently selected language is </span>
        <abbr :title="selectedLanguage.title">{{ selectedLanguage.abbreviation }}</abbr>
      </span>
      <span v-else> <span class="sr-only">Toggle menu for </span>{{ name }}</span>
      <div class="vs-dropdown__icon-wrapper">
        <vs-icon name="chevron-down" variant="reverse-white" size="xxs" />
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
            :data-vs-track="link.trackingId"
            @keydown="checkKeydown($event, index === last)"
          >
            {{ link.title }}
          </a>
        </li>
      </ul>
    </transition>
  </component>
</template>

<script>
import VsIcon from "../../../../elements/icon/Icon"
import { ClickOutside } from "../../../../../directives/ClickOutside.js"

export default {
  name: "VsLanguage",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsIcon,
  },
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
    selectedLanguage() {
      let selectedLanguage = this.dropdownList.filter(language => language.isActive)
      return selectedLanguage[0]
    },
  },
  methods: {
    checkKeydown($event, isLast) {
      if ($event.key === "Tab" && !$event.shiftKey && isLast) {
        this.show = false
      }
    },
    onClose() {
      this.show = false
    },
    reset() {
      this.show = false
    },
    setSelectedLanguage(language) {
      this.selectedLanguage = language
    },
    triggerToggle() {
      let currentState = this.show
      this.$root.$emit("resetMenus")
      if (currentState === false) {
        this.show = true
      }
      this.$refs.trigger.blur()
    },
  },
  directives: {
    ClickOutside,
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
@import "../../styles/placeholders";
@import "../../styles/animations";
@import "../../styles/vs-dropdown";

.vs-dropdown--language .vs-dropdown__list {
  left: auto;
  right: 0;
}

abbr[title] {
  border-bottom: none;
  cursor: inherit;
  text-decoration: none;
}
</style>

<docs>
  ```jsx
  const languages = require("../../../../../assets/fixtures/languages.json")

  <div style="position: relative; height: 100vh; display: flex; justify-content: space-between;">
    <vs-language
        name="Languages"
        :dropdown-list="languages"
    />
  </div>
  ```
</docs>
