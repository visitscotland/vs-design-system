<template>
  <component :is="type" class="vs-main-nav" :aria-label="name">
    <button
      class="vs-main-nav__button"
      data-toggle-trigger="main-nav"
      @click="triggerToggle('main-nav')"
      aria-haspopup="true"
      aria-expanded="false"
    >
      <span class="switch"> <span class="sr-only">Toggle Main Navigation</span> </span>
    </button>
    <ul class="vs-main-nav__list" data-toggle-pane="main-nav">
      <li
        class="vs-main-nav__list-item"
        v-for="(link, index) in this.mainNavigationList"
        :key="index"
      >
        <template v-if="link.subnav">
          <button
            class="vs-main-nav__button-link"
            :aria-haspopup="hasPopup(link)"
            :aria-expanded="isExpanded(link)"
          >
            {{ link.title }}
            <vs-svg path="icons/chevron-down" height="10" fill="#C6BFBF" />
          </button>
        </template>
        <template v-if="!link.subnav">
          <a
            class="vs-main-nav__link"
            :href="link.href"
            :class="{ external: link.isExternal }"
            :target="link.isExternal ? '_blank' : false"
            :data-di-id="link.trackingID"
            >{{ link.title }}</a
          >
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
  </component>
</template>

<script>
import VsSvg from "../../elements/svg/Svg"

export default {
  name: "VsMainNav",
  status: "prototype",
  release: "0.0.1",
  components: { VsSvg },
  data() {
    return {
      mainNavListIsExpanded: false,
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
    mainNavigationList: {
      type: Array,
      required: true,
    },
    name: {
      type: String,
    },
  },
  methods: {
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
@import "styles/placeholders";

.vs-main-nav {
  display: flex;

  &__button {
    @extend %button-reset;
    @extend %main-nav-button-style;

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
    box-shadow: inset 0 8px 6px -6px rgba(0, 0, 0, 0.3);
    left: 0;
    position: absolute;
    background-color: $color-white;
    overflow: hidden;
    top: 40px;
    transform: translateX(100%);
    transition: transform 250ms ease-in-out;
    width: 0;

    &.expanded {
      height: 100vh;
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
    font-weight: $font-weight-semi-bold;
    font-size: 1.125rem;
    border-bottom: 1px solid $color-light-granite;
    padding: 20px 16px 0px 16px;

    &:hover {
      background-color: $color-light-granite;
    }

    &:focus {
      @extend %focus-pink;
    }
  }

  &__dropdown-panel {
    display: none;

    &--active {
      display: block;
    }
  }
}
</style>

<docs>
  ```jsx
    <vs-main-nav
        name="Main navigation"
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
  ```
</docs>
