<template>
  <component :is="type" class="vs-header">
    <div class="vs-universal-nav__wrapper">
      <vs-container>
        <div class="vs-universal-nav__wrapper--inner">
          <nav aria-label="Other Sites Navigation">
            <button
              class="vs-universal-nav__button"
              data-toggle-trigger="universal-nav"
              @click="triggerToggle('universal-nav')"
              aria-haspopup="true"
              aria-expanded="false"
            >
              <span class="sr-only">Toggle submenu for</span>Our sites
            </button>
            <ul
              aria-role="menu"
              aria-label="Our Sites"
              class="vs-universal-nav__list"
              data-toggle-pane="universal-nav"
            >
              <li
                class="vs-universal-nav__list-item"
                :class="{ 'vs-universal-nav__list-item--active': link.isActive }"
                v-for="(link, index) in universalNavLinks"
                :key="index"
              >
                <a
                  class="vs-universal-nav__link"
                  :href="link.href"
                  :target="link.isExternal ? '_blank' : false"
                  :data-di-id="link.trackingID"
                >
                  {{ link.title }}
                  <vs-svg
                    v-if="link.isExternal"
                    path="icons/external-link"
                    height="10"
                    fill="white"
                    container-class="vs-external-link__icon-wrapper"
                  />
                </a>
              </li>
            </ul>
          </nav>
          <button class="vs-account__button" @click="toggleLogin()">
            <vs-svg
              path="icons/account-avatar"
              height="10"
              fill="white"
              container-class="vs-account__icon-wrapper"
            />
            <template v-if="!this.isLoggedIn"
              >Login</template
            >
            <template v-if="this.isLoggedIn"
              >Log out</template
            >
          </button>
          <div class="vs-language__wrapper" data-toggle-pane="language-list">
            <button
              class="vs-language__button"
              data-toggle-trigger="language-list"
              @click="triggerToggle('language-list')"
              aria-haspopup="true"
              aria-expanded="false"
            >
              <span class="vs-language__selected">
                {{ this.selectedLanguage.abbreviation }}
                <span class="sr-only"
                  >{{ this.selectedLanguage.title }} is the currently selected. Click to toggle
                  language selection.</span
                >
              </span>
              <vs-svg
                path="icons/chevron-down"
                height="8"
                fill="white"
                container-class="vs-language__icon-wrapper"
              />
            </button>
            <ul class="vs-language__list">
              <li
                class="vs-language__list-item"
                v-for="(link, index) in languageNavLinks"
                :key="index"
              >
                <a class="vs-language__link" :href="link.locale">{{ link.title }}</a>
              </li>
            </ul>
          </div>
        </div>
      </vs-container>
    </div>

    <div class="vs-main-nav__wrapper">
      <vs-container>
        <div class="d-flex align-items-stretch justify-content-between">
          <a href="#" class="vs-main-nav__branding-link"> Scotland <span>Alba</span> </a>
          <div class="vs-controls__wrapper">
            <button
              class="vs-search__button"
              data-trigger-toggle="search"
              @click="triggerToggle('search')"
            >
              <span class="sr-only">Toggle Search</span>
              <vs-svg
                path="icons/search"
                height="18"
                fill="white"
                container-class="vs-search__icon-wrapper"
              />
            </button>
            <button
              class="vs-favourites__button"
              @click="addToFavourites({ url: 'http://www.visitscotland.com', title: 'Homepage' })"
            >
              <span class="sr-only">Add to Favourites</span>
              <span class="vs-favourites__count" v-if="this.favourites.length"
                ><span class="sr-only">Current favourites count: </span
                >{{ this.favourites.length }}</span
              >
              <vs-svg
                path="icons/favourites-heart"
                height="18"
                fill="#700e57"
                container-class="vs-favourites__icon-wrapper"
              />
            </button>
            <button
              class="vs-main-nav__button"
              data-toggle-trigger="main-nav"
              @click="triggerToggle('main-nav')"
              aria-haspopup="true"
              aria-expanded="false"
            >
              <span class="switch"> <span class="sr-only">Toggle Main Navigation</span> </span>
            </button>
          </div>
        </div>
        <nav aria-label="Main Navigation">
          <ul class="vs-main-nav__list" data-toggle-pane="main-nav">
            <li
              class="vs-main-nav__list-item"
              v-for="(link, index) in this.mainNavLinks"
              :key="index"
            >
              <template v-if="link.subnav">
                <button
                  class="vs-main-nav__button-link"
                  :aria-haspopup="hasPopup(link)"
                  :aria-expanded="isExpanded(link)"
                >
                  {{ link.title }}
                  <vs-svg
                    path="icons/chevron-down"
                    height="18"
                    fill="#C6BFBF"
                    container-class="vs-main-nav__icon-wrapper"
                  />
                </button>
              </template>
              <template v-if="!link.subnav">
                <a
                  class="vs-main-nav__link"
                  :href="link.href"
                  :class="{ external: link.isExternal }"
                  :target="link.isExternal ? '_blank' : false"
                  :data-di-id="link.trackingID"
                >
                  {{ link.title }}
                </a>
              </template>
              <div
                class="vs-main-nav__dropdown-panel"
                v-if="hasPopup(link)"
                :class="{ 'vs-main-nav__dropdown-panel--active': link.isActive }"
              >
                <vs-row>
                  <vs-col v-if="link.subnav">
                    <ul class="vs-main-nav__list vs-main-nav__list--level2">
                      <li
                        class="vs-main-nav__list-item"
                        v-for="(subnavLevel1, index) in link.subnav"
                        :key="index"
                      >
                        <template v-if="!subnavLevel1.href">
                          <vs-heading level="3">{{ subnavLevel1.title }}</vs-heading>
                        </template>
                        <template v-if="subnavLevel1.href">
                          <a
                            class="vs-main-nav__link"
                            :href="subnavLevel1.href"
                            :class="{ external: subnavLevel1.isExternal }"
                            :target="subnavLevel1.isExternal ? '_blank' : false"
                            :data-di-id="subnavLevel1.trackingID"
                            >{{ subnavLevel1.title }}</a
                          >
                        </template>
                        <ul
                          class="vs-main-nav__list vs-main-nav__list--level-3"
                          v-if="subnavLevel1.subnav"
                        >
                          <li
                            class="vs-main-nav__list-item"
                            v-for="(subnavLevel2, index) in subnavLevel1.subnav"
                            :key="index"
                          >
                            <a
                              class="vs-main-nav__link"
                              :href="subnavLevel2.href"
                              :class="{ external: subnavLevel2.isExternal }"
                              :target="subnavLevel2.isExternal ? '_blank' : false"
                              :data-di-id="subnavLevel2.trackingID"
                              >{{ subnavLevel2.title }}</a
                            >
                          </li>
                        </ul>
                      </li>
                    </ul>
                  </vs-col>
                  <vs-col v-if="link.promoPanel">
                    <vs-heading level="3">{{ link.promoPanel.title }}</vs-heading>
                    <p>{{ link.promoPanel.description }}</p>
                    <a
                      :href="link.promoPanel.href"
                      :class="{ external: link.promoPanel.isExternal }"
                      :target="link.promoPanel.isExternal ? '_blank' : false"
                      :data-di-id="link.promoPanel.trackingID"
                      >{{ link.promoPanel.buttonText }}</a
                    >
                  </vs-col>
                  <ul class="row" v-if="link.promoList">
                    <li
                      class="col"
                      v-for="(promoItem, index) in link.promoList.promos"
                      :key="index"
                      style
                    >
                      <a
                        :href="promoItem.href"
                        :class="{ external: promoItem.isExternal }"
                        :target="promoItem.isExternal ? '_blank' : false"
                        :data-di-id="promoItem.trackingID"
                        >{{ promoItem.title }}</a
                      >
                    </li>
                  </ul>
                </vs-row>
              </div>
            </li>
          </ul>
        </nav>
      </vs-container>
    </div>
  </component>
</template>

<script>
import VsContainer from "../elements/Container"
import VsHeading from "../elements/Heading"
import VsRow from "../elements/Row"
import VsCol from "../elements/Col"
import VsSvg from "../elements/Svg"

export default {
  name: "VsHeader",
  status: "prototype",
  release: "0.0.1",
  components: { VsContainer, VsRow, VsCol, VsSvg },
  data() {
    return {
      mainNavLinks: this.mainNavigationList,
      languageNavLinks: this.languageSelectorList,
      universalNavLinks: this.universalNavigationList,
      selectedLanguage: this.languageSelectorList[0],
      mainNavListIsExpanded: false,
      favourites: [],
      isLoggedIn: false,
    }
  },
  props: {
    /**
     * The html element name used for the component
     */
    type: {
      type: String,
      default: "header",
    },
    universalNavigationList: {
      type: Array,
      required: true,
    },
    mainNavigationList: {
      type: Array,
      required: true,
    },
    languageSelectorList: {
      type: Array,
      required: true,
    },
  },
  watch: {
    favourites(oldValue, newValue) {},
  },
  methods: {
    addToFavourites(favourite) {
      if (this.favourites.indexOf(favourite) === -1) {
        this.favourites.push(favourite)
      }
    },
    hasPopup(link) {
      if (
        link.subnav !== undefined ||
        link.promoPanel !== undefined ||
        link.promoList !== undefined
      ) {
        return true
      }
      return false
    },
    isExpanded(link) {
      var hasPopup = this.hasPopup(link)
      if (link.isActive && hasPopup) {
        return true
      }
      return false
    },
    resetMenus() {
      Array.prototype.forEach.call(this.$el.querySelectorAll("[data-toggle-pane]"), pane => {
        pane.classList.remove("expanded")
      })
      Array.prototype.forEach.call(this.$el.querySelectorAll("[data-toggle-trigger]"), trigger => {
        trigger.setAttribute("aria-expanded", false)
      })
    },
    toggleLogin() {
      this.isLoggedIn = !this.isLoggedIn
      this.resetMenus()
    },
    toggleSubmenu(link) {
      return (link.isActive = !link.isActive)
    },
    triggerToggle(selector) {
      let thisTrigger = this.$el.querySelector('[data-toggle-trigger="' + selector + '"]')
      let thisPane = this.$el.querySelector('[data-toggle-pane="' + selector + '"]')
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

%clip {
  clip: rect(0, 0, 0, 0);
}

%clip-reset {
  clip: none;
}

%focus-white {
  outline: none;
  box-shadow: inset 0 -3px 0 0 $white;
}

%focus-pink {
  outline: none;
  box-shadow: inset 0 -3px 0 0 $color-thistle-pink;
}

%list-reset {
  list-style-type: none;
  margin: 0;
  padding: 0;
}

%button-reset {
  -webkit-appearance: none;
  border: none;
  background: none;
}

%universal-link {
  box-shadow: inset 0 0 0 0 transparent;
  color: #fff;
  display: block;
  font-size: 0.75rem;
  padding: 5px;
  position: relative;
  transition: all 250ms ease-in-out;

  &:focus {
    background-color: lighten($color-heather-purple, 3%);
    @extend %focus-white;
  }

  &:hover {
    background-color: lighten($color-heather-purple, 3%);
    color: #fff;
  }
}

.vs-header {
  min-height: 600px; // temporary -
  overflow: hidden;
  position: relative;
}

.vs-universal-nav {
  &__wrapper {
    background-color: $color-heather-purple;
    position: relative;
    z-index: 4;

    &--inner {
      display: flex;
      align-items: center;
      justify-content: space-between;
    }
  }

  &__list {
    @extend %list-reset;
    background-color: darken($color-heather-purple, 10%);
    left: 0;
    opacity: 0;
    position: absolute;
    top: 28px;
    opacity: 0;
    transform: translate3d(0, -100%, 0);
    transition: all 250ms ease-in-out;
    width: 100%;

    &.expanded {
      opacity: 1;
      transform: translate3d(0, 0, 0);
      height: 100vh;
    }
  }

  &__list-item {
    min-height: 20px;
  }

  &__link {
    @extend %universal-link;
    font-size: 1rem;
    border-bottom: 1px solid rgba(255, 255, 255, 0.2);
    display: none;
    padding: 20px 16px;
    width: 100%;

    .vs-universal-nav__list.expanded & {
      display: block;
    }
  }

  &__button {
    @extend %button-reset;
    @extend %universal-link;
    margin-left: -5px;
    z-index: 5;

    h2 {
      font-weight: 400;
      font-size: 0.75rem;
      margin: 0;
      color: #fff;
    }
  }
}

.vs-external-link {
  &__icon-wrapper {
    display: inline-flex;
    margin-left: 5px;
  }
}

.vs-account {
  &__button {
    @extend %button-reset;
    @extend %universal-link;
    display: flex;
    align-items: center;
    z-index: 5;
  }

  &__icon-wrapper {
    margin-right: 3px;
  }
}

.vs-language {
  &__wrapper {
    display: flex;
    align-items: center;
  }

  &__selected {
    font-weight: 400;
    font-size: 0.75rem;
    margin: 0;
    color: #fff;
  }

  &__list {
    @extend %list-reset;
    background-color: darken($color-heather-purple, 10%);
    font-size: 1rem;
    left: 0;
    opacity: 0;
    position: absolute;
    top: 28px;
    transform: translate3d(0, -100%, 0);
    transition: all 250ms ease-in-out;
    width: 100%;

    .vs-language__wrapper.expanded & {
      opacity: 1;
      transform: translate3d(0, 0, 0);
      height: 100vh;
    }
  }

  &__list-item {
    min-height: 20px;
  }

  &__link {
    @extend %universal-link;
    border-bottom: 1px solid rgba(255, 255, 255, 0.2);
    font-size: 1rem;
    padding: 20px 16px;
    width: 100%;
    display: none;

    .vs-language__wrapper.expanded & {
      display: block;
    }
  }

  &__button {
    @extend %button-reset;
    @extend %universal-link;
    margin-right: -5px;
    display: flex;
    height: 100%;
    padding: 5px;
    z-index: 5;
  }

  &__icon-wrapper {
    margin-left: 5px;
    transition: transform 250ms;

    .vs-language__wrapper.expanded & {
      transform: rotate(180deg);
      transform-origin: 50% 50%;
    }
  }
}

.vs-main-nav {
  &__branding-link {
    font-family: $font-family-display;
    font-size: 0.75rem;
    padding: 13px 16px;
    margin-left: -16px;

    &:focus {
      @extend %focus-pink;
    }

    span {
      position: relative;
      display: inline-block;
      color: $color-mid-granite;
      padding-left: 5px;
      margin-left: 5px;

      &::before {
        background-color: $color-mid-granite;
        content: "";
        display: block;
        height: 12px;
        left: -3px;
        position: absolute;
        top: 3px;
        width: 2px;
      }
    }
  }

  &__wrapper {
    background-color: $white;
    box-shadow: 0 8px 6px -6px rgba(0, 0, 0, 0.3);
    position: relative;
    z-index: 3;
  }

  &__button {
    @extend %button-reset;
    margin: 0;
    padding: 20px 14px;
    margin-right: -16px;

    .switch {
      display: block;
      width: 16px;
      height: 2px;
      background-color: $color-mid-granite;
      position: relative;

      &::before {
        content: "";
        display: block;
        width: 16px;
        height: 2px;
        background-color: $color-mid-granite;
        position: absolute;
        top: -6px;
        transition: transform 250ms ease-in-out;
      }

      &::after {
        content: "";
        display: block;
        width: 16px;
        height: 2px;
        background-color: $color-mid-granite;
        position: absolute;
        top: 6px;
        transition: transform 250ms ease-in-out;
      }
    }

    &[aria-expanded="true"] {
      .switch {
        background-color: $color-white;

        &::before {
          transform: rotate(45deg);
          top: 0;
          left: -2px;
          width: 20px;
        }

        &::after {
          transform: rotate(135deg);
          top: 0;
          left: -2px;
          width: 20px;
        }
      }
    }

    &:focus {
      @extend %focus-pink;
    }
  }

  &__list {
    @extend %list-reset;
    left: 0;
    position: absolute;
    background-color: $color-white;
    overflow: hidden;
    transform: translateX(100%);
    transition: transform 250ms ease-in-out;
    width: 0;

    &.expanded {
      transform: translateX(0);
      width: 100%;
      z-index: 2;
    }
  }

  &__button-link {
    @extend %button-reset;
    width: 100%;
  }

  &__button-link,
  &__link {
    color: $color-total-eclipse;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-weight: 600;
    font-size: 1.5rem;
    border-bottom: 1px solid $color-mid-granite;
    padding: 20px 16px 0px 16px;
  }

  &__dropdown-panel {
    display: none;

    &--active {
      display: block;
    }
  }
}

.vs-controls {
  &__wrapper {
    display: flex;
    align-items: stretch;
  }
}

.vs-search {
  &__button {
    @extend %button-reset;
    background-color: $color-thistle-pink;
    padding: 10px 12px;

    &:focus {
      @extend %focus-white;
    }
  }
}

.vs-favourites {
  &__button {
    @extend %button-reset;
    padding: 10px 12px;
    position: relative;

    &:focus {
      @extend %focus-pink;
    }
  }

  &__count {
    position: absolute;
    display: block;
    width: 100%;
    left: 0;
  }
}
</style>

<docs>
  ```jsx
  <div>
    <vs-header
        :language-selector-list="[
            {
                title: 'English',
                abbreviation: 'EN',
                locale: 'en-en',
                trackingID: 1,
                isActive: true
            },
            {
                title: 'Deutsch',
                abbreviation: 'DE',
                locale: 'de-de',
                trackingID: 1,
                isActive: false
            },
            {
                title: 'Español',
                abbreviation: 'ES',
                locale: 'es-es',
                trackingID: 1,
                isActive: false
            },
            {
                title: 'Français',
                abbreviation: 'FR',
                locale: 'fr-fr',
                trackingID: 1,
                isActive: false
            },
            {
                title: 'Italiano',
                abbreviation: 'IT',
                locale: 'it-it',
                trackingID: 1,
                isActive: false
            },
            {
                title: 'Nederlands',
                abbreviation: 'NL',
                locale: 'nl-nl',
                trackingID: 1,
                isActive: false
            }
        ]"
        :universal-navigation-list="[
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
        :main-navigation-list="[
            {
                title: 'Destinations',
                href: 'https://www.visitscotland.com/destinations',
                isExternal: false,
                isActive: false,
                trackingID: 1,
                subnav: [
                    {
                        title: 'Top Destination Searches',
                        href: null,
                        isExternal: false,
                        isActive: false,
                        trackingID: null,
                        subnav: [
                            {
                                title: 'Aberdeen',
                                href: 'https://www.visitscotland.com/destinations-maps/aberdeen',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Isle of Arran',
                                href: 'https://www.visitscotland.com/destinations-maps/arran',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Aviemore',
                                href: 'https://www.visitscotland.com/destinations-maps/aviemore',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Ayr',
                                href: 'https://www.visitscotland.com/info/towns-villages/ayr-p242821',
                                isExternal: true,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Dumfries',
                                href: 'https://www.visitscotland.com/destinations-maps/dumfries',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Dundee',
                                href: 'https://www.visitscotland.com/destinations-maps/dundee',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Edinburgh',
                                href: 'https://www.visitscotland.com/destinations-maps/edinburgh',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Fort William',
                                href: 'https://www.visitscotland.com/destinations-maps/fort-william',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Glasgow',
                                href: 'https://www.visitscotland.com/destinations-maps/glasgow',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Glencoe',
                                href: 'https://www.visitscotland.com/destinations-maps/glencoe',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Highlands',
                                href: 'https://www.visitscotland.com/destinations-maps/highlands',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Inverness',
                                href: 'https://www.visitscotland.com/destinations-maps/inverness',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Isle of Islay',
                                href: 'https://www.visitscotland.com/destinations-maps/isle-islay',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Isle of Jura',
                                href: 'https://www.visitscotland.com/destinations-maps/isle-jura',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Isle of Mull',
                                href: 'https://www.visitscotland.com/destinations-maps/isle-mull',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Kirkwall',
                                href: 'https://www.visitscotland.com/destinations-maps/kirkwall',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Lerwick',
                                href: 'https://www.visitscotland.com/destinations-maps/lerwick',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Isle of Lewis, Isle of Harris and Stornoway',
                                href: 'https://www.visitscotland.com/destinations-maps/lewis-harris-stornoway',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Loch Lomond',
                                href: 'https://www.visitscotland.com/destinations-maps/loch-lomond',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Loch Ness',
                                href: 'https://www.visitscotland.com/destinations-maps/loch-ness',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Oban',
                                href: 'https://www.visitscotland.com/destinations-maps/oban',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Orkney',
                                href: 'https://www.visitscotland.com/destinations-maps/orkney',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Peebles',
                                href: 'https://www.visitscotland.com/destinations-maps/peebles',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Perth',
                                href: 'https://www.visitscotland.com/destinations-maps/perth',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Pitlochry',
                                href: 'https://www.visitscotland.com/destinations-maps/pitlochry',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Shetland',
                                href: 'https://www.visitscotland.com/destinations-maps/shetland',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Isle of Skye',
                                href: 'https://www.visitscotland.com/destinations-maps/isle-skye',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'St Andrews',
                                href: 'https://www.visitscotland.com/destinations-maps/st-andrews',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Stirling',
                                href: 'https://www.visitscotland.com/destinations-maps/stirling',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            }
                        ]
                    },
                    {
                        title: 'Explore Scotland\u0027s Regions',
                        href: null,
                        isExternal: false,
                        isActive: false,
                        trackingID: 1,
                        subnav: [
                            {
                                title: 'Aberdeen & Aberdeenshire',
                                href: 'https://www.visitscotland.com/destinations-maps/aberdeen-city-shire',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Argyll and the Isles',
                                href: 'https://www.visitscotland.com/destinations-maps/argyll-isles',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Ayrshire and Arran',
                                href: 'https://www.visitscotland.com/destinations-maps/ayrshire-arran',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Dumfries and Galloway',
                                href: 'https://www.visitscotland.com/destinations-maps/dumfries-galloway',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Dundee and Angus',
                                href: 'https://www.visitscotland.com/destinations-maps/dundee-angus',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Edinburgh and the Lothians',
                                href: 'https://www.visitscotland.com/destinations-maps/edinburgh-lothians',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Greater Glasgow and the Clyde Valley',
                                href: 'https://www.visitscotland.com/destinations-maps/glasgow-clyde-valley',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'The Highlands',
                                href: 'https://www.visitscotland.com/destinations-maps/highlands',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'The Kingdom of Fife',
                                href: 'https://www.visitscotland.com/destinations-maps/kingdom-fife',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Loch Lomond, The Trossachs, Stirling and Forth Valley',
                                href: 'https://www.visitscotland.com/destinations-maps/loch-lomond-trossachs-stirling-forth-valley',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Orkney',
                                href: 'https://www.visitscotland.com/destinations-maps/orkney',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Outer Hebrides',
                                href: 'https://www.visitscotland.com/destinations-maps/outer-hebrides',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Perthshire',
                                href: 'https://www.visitscotland.com/destinations-maps/perthshire',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Shetland',
                                href: 'https://www.visitscotland.com/destinations-maps/shetland',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            }
                        ]
                    }
                ]
            },
            {
                title: 'Places to stay',
                href: 'https://www.visitscotland.com/accommodation',
                isExternal: false,
                isActive: false,
                trackingID: 1,
                subnav: [
                    {
                        title: 'Accommodation Types',
                        href: null,
                        trackingID: null,
                        subnav: [
                            {
                                title: 'Accessible accommodation',
                                href: 'https://www.visitscotland.com/accommodation/accessible',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'B&Bs and Guesthouses',
                                href: 'https://www.visitscotland.com/accommodation/bandbs-guesthouses',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Cabins and lodges',
                                href: 'https://www.visitscotland.com/accommodation/cabins-lodges-chalets',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Caravan holidays and camping in Scotland',
                                href: 'https://www.visitscotland.com/accommodation/caravan-camping',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Eco accommodation',
                                href: 'https://www.visitscotland.com/accommodation/eco-green',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Exclusive use venues',
                                href: 'https://www.visitscotland.com/accommodation/exclusive-use-venues',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Glamping',
                                href: 'https://www.visitscotland.com/accommodation/caravan-camping/glamping',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Hostels',
                                href: 'https://www.visitscotland.com/accommodation/hostels',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Hotels',
                                href: 'https://www.visitscotland.com/accommodation/hotels',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Restaurants with rooms and inns',
                                href: 'https://www.visitscotland.com/accommodation/inns-restaurants-with-rooms',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Quality assurance',
                                href: 'https://www.visitscotland.com/accommodation/quality-assurance',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Self-catering',
                                href: 'https://www.visitscotland.com/accommodation/self-catering',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Unusual places to stay',
                                href: 'https://www.visitscotland.com/accommodation/unusual-places-to-stay',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Wedding venues',
                                href: 'https://www.visitscotland.com/accommodation/wedding-venues',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            }
                        ]
                    },
                    {
                        title: 'Top Accommodation Searches',
                        href: null,
                        isExternal: false,
                        isActive: false,
                        trackingID: null,
                        subnav: [
                            {
                                title: 'Aberdeen',
                                href: 'https://www.visitscotland.com/destinations-maps/aberdeen/accommodation',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Aviemore',
                                href: 'https://www.visitscotland.com/destinations-maps/aviemore/accommodation',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Arran',
                                href: 'https://www.visitscotland.com/destinations-maps/arran/accommodation',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Edinburgh',
                                href: 'https://www.visitscotland.com/destinations-maps/edinburgh/accommodation',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Fort William',
                                href: 'https://www.visitscotland.com/destinations-maps/fort-william/accommodation',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Glasgow',
                                href: 'https://www.visitscotland.com/destinations-maps/glasgow/accommodation',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Inverness',
                                href: 'https://www.visitscotland.com/destinations-maps/inverness/accommodation',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Loch Lomond',
                                href: 'https://www.visitscotland.com/destinations-maps/loch-lomond/accommodation',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Oban',
                                href: 'https://www.visitscotland.com/destinations-maps/oban/accommodation',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'St Andrews',
                                href: 'https://www.visitscotland.com/destinations-maps/st-andrews/accommodation',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            }
                        ]
                    }
                ],
                promoPanel: {
                    title: 'Unusual Accommodation',
                    href: 'https://www.visitscotland.com/accommodation/unusual-places-to-stay/',
                    isExternal: false,
                    isActive: false,
                    buttonText: 'Read more',
                    description: 'Why not stay in a castle, a lighthouse or on a working farm?',
                    imageLink: 'https://cimg.visitscotland.com/cms-images/navigation/accommodation-rc?size=md'
                }
            },
            {
                title: 'Things to do',
                href: 'https://www.visitscotland.com/see-do',
                isExternal: false,
                isActive: false,
                trackingID: 1,
                subnav: [
                    {
                        title: 'Ancestry',
                        href: 'https://www.visitscotland.com/see-do/research-your-ancestry',
                        isExternal: false,
                        isActive: false,
                        trackingID: 1
                    },
                    {
                        title: 'Attractions',
                        href: 'https://www.visitscotland.com/see-do/attractions',
                        isExternal: false,
                        isActive: false,
                        trackingID: 1
                    },
                    {
                        title: 'Castles',
                        href: 'https://www.visitscotland.com/see-do/attractions/castles',
                        isExternal: false,
                        isActive: false,
                        trackingID: 1
                    },
                    {
                        title: 'Events',
                        href: 'https://www.visitscotland.com/see-do/events',
                        isExternal: false,
                        isActive: false,
                        trackingID: 1
                    },
                    {
                        title: 'Film and TV locations',
                        href: 'https://www.visitscotland.com/see-do/attractions/film-tv',
                        isExternal: false,
                        isActive: false,
                        trackingID: 1
                    },
                    {
                        title: 'Food and drink',
                        href: 'https://www.visitscotland.com/see-do/food-drink',
                        isExternal: false,
                        isActive: false,
                        trackingID: 1
                    },
                    {
                        title: 'Golf',
                        href: 'https://www.visitscotland.com/see-do/active/golf',
                        isExternal: false,
                        isActive: false,
                        trackingID: 1
                    },
                    {
                        title: 'Islands and island hopping',
                        href: 'https://www.visitscotland.com/see-do/island-hopping',
                        isExternal: false,
                        isActive: false,
                        trackingID: 1
                    },
                    {
                        title: 'Itineraries',
                        href: 'https://www.visitscotland.com/see-do/itineraries',
                        isExternal: false,
                        isActive: false,
                        trackingID: 1
                    },
                    {
                        title: 'Landscapes and nature',
                        href: 'https://www.visitscotland.com/see-do/landscapes-nature',
                        isExternal: false,
                        isActive: false,
                        trackingID: 1
                    },
                    {
                        title: 'Outdoor activities',
                        href: 'https://www.visitscotland.com/see-do/active',
                        isExternal: false,
                        isActive: false,
                        trackingID: 1
                    },
                    {
                        title: 'Scenic driving routes',
                        href: 'https://www.visitscotland.com/see-do/tours/driving-road-trips',
                        isExternal: false,
                        isActive: false,
                        trackingID: 1
                    }
                ],
                promoPanel: {
                    title: 'Scotland\u0027s Tallest Peak',
                    href: 'https://www.visitscotland.com/see-do/iconic-scotland/ben-nevis',
                    isExternal: false,
                    isActive: false,
                    trackingID: 1,
                    buttonText: 'Read more',
                    description: 'Iconic Ben Nevis needs to be top of your Scottish to-do list.',
                    imageLink: 'https://cimg.visitscotland.com/cms-images/destinations/fort-william/ben-nevis-portrait-1?size=md'
                }
            },
            {
                title: 'Planning a trip',
                href: 'https://www.visitscotland.com/travel',
                isExternal: false,
                isActive: false,
                trackingID: 1,
                subnav: [
                    {
                        title: 'Getting to Scotland',
                        href: null,
                        isExternal: false,
                        isActive: false,
                        trackingID: null,
                        subnav: [
                            {
                                title: 'From England and Wales',
                                href: 'https://www.visitscotland.com/travel/getting-to-scotland/england-wales/',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'From Ireland',
                                href: 'https://www.visitscotland.com/travel/getting-to-scotland/ireland/',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'From overseas',
                                href: 'https://www.visitscotland.com/travel/getting-to-scotland/overseas/',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            }
                        ]
                    },
                    {
                        title: 'Getting around Scotland',
                        href: null,
                        isExternal: false,
                        isActive: false,
                        trackingID: null,
                        subnav: [
                            {
                                title: 'Air',
                                href: 'https://www.visitscotland.com/travel/getting-around-scotland/air/',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Bus and coach',
                                href: 'https://www.visitscotland.com/travel/getting-around-scotland/coach/',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Driving',
                                href: 'https://www.visitscotland.com/about/practical-information/driving-in-scotland/',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Ferry',
                                href: 'https://www.visitscotland.com/travel/getting-around-scotland/ferry/',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Scenic Driving Routes',
                                href: 'https://www.visitscotland.com/see-do/tours/driving-road-trips/',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Train',
                                href: 'https://www.visitscotland.com/travel/getting-around-scotland/train/',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Campervan and motorhome',
                                href: 'https://www.visitscotland.com/accommodation/caravan-camping/caravan-motorhomes-campervan-hire/',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            },
                            {
                                title: 'Cycling',
                                href: 'https://www.visitscotland.com/see-do/active/cycling/',
                                isExternal: false,
                                isActive: false,
                                trackingID: 1
                            }
                        ]
                    }
                ],
                promoPanel: {
                    title: 'VisitScotland iCentres',
                    href: 'https://www.visitscotland.com/about/practical-information/vic/',
                    isExternal: false,
                    isActive: false,
                    buttonText: 'Read more',
                    trackingID: 1,
                    description: 'Expert local knowledge, gifts and inspiration.',
                    imageLink: 'https://cimg.visitscotland.com/cms-images/navigation/travel-rc?size=md'
                }
            },
            {
                title: 'Inspiration',
                href: 'https://www.visitscotland.com/inspiration',
                isExternal: false,
                isActive: false,
                trackingID: 1,
                promoList: {
                    buttonText: 'See all inspiration',
                    href: 'https://www.visitscotland.com/inspiration',
                    isExternal: false,
                    isActive: false,
                    trackingID: 1,
                    promos: [
                        {
                            title: 'Romantic',
                            href: 'https://www.visitscotland.com/inspiration/romantic',
                            isExternal: false,
                            isActive: false,
                            trackingID: 1,
                            imageLink: ''
                        },
                        {
                            title: 'Island hopping',
                            href: 'https://www.visitscotland.com/inpiration/island-hopping',
                            isExternal: false,
                            isActive: false,
                            trackingID: 1,
                            imageLink: ''
                        },
                        {
                            title: 'Family',
                            href: 'https://www.visitscotland.com/inpiration/family',
                            isExternal: false,
                            isActive: false,
                            trackingID: 1,
                            imageLink: ''
                        },
                        {
                            title: 'Farm stays',
                            href: 'https://www.visitscotland.com/inpiration/farm-stays',
                            isExternal: false,
                            isActive: false,
                            trackingID: 1,
                            imageLink: ''
                        },
                        {
                            title: 'Wellness',
                            href: 'https://www.visitscotland.com/inpiration/wellness',
                            isExternal: false,
                            isActive: false,
                            trackingID: 1,
                            imageLink: ''
                        }
                    ]
                }
            }
        ]"
    />
  </div>
  ```
</docs>
