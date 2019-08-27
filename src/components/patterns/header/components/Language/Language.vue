<template>
  <component :is="type" class="vs-dropdown" :aria-label="name">
    <button class="vs-dropdown__button" id="language-toggle-trigger" v-b-toggle.language-list>
      <span v-if="selectedLanguage !== null">
        <span class="sr-only">Currently selected language is </span>
        <abbr :title="selectedLanguage.title">{{ selectedLanguage.abbreviation }}</abbr>
      </span>
      <span v-else> <span class="sr-only">Toggle menu for </span>{{ name }}</span>
      <div class="vs-dropdown__icon-wrapper">
        <vs-icon name="chevron-down" variant="reverse-white" size="xxs" />
      </div>
    </button>
    <b-collapse id="language-list">
      <ul aria-role="menubar" :aria-label="name" class="vs-dropdown__list list-unstyled">
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
    </b-collapse>
  </component>
</template>

<script>
import VsIcon from "../../../../elements/icon/Icon"
import { VBToggle, BCollapse } from "bootstrap-vue"

export default {
  name: "VsLanguage",
  status: "prototype",
  release: "0.0.1",
  components: {
    BCollapse,
    VsIcon,
  },
  directives: { "b-toggle": VBToggle },
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
        this.close()
      }
    },
    close() {
      this.setFocusOnToggle()
      this.$root.$emit("bv::toggle::collapse", "language-list")
    },
    setSelectedLanguage(language) {
      this.selectedLanguage = language
    },
    setFocusOnToggle() {
      setTimeout(() => {
        document.getElementById("language-toggle-trigger").focus()
      }, 100)
    },
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
