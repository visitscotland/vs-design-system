<template>
  <component
    :is="type"
    class="vs-main-nav__list-item"
    :class="'vs-main-nav__list-item--level' + level"
  >
    <button
      v-if="hasPopup(item)"
      class="vs-main-nav__button"
      :class="[show ? 'expanded' : '', level ? 'vs-main-nav__button--level' + level : '']"
      data-toggle-trigger
      @click="triggerToggle()"
      aria-haspopup="true"
      :aria-expanded="show ? 'true' : 'false'"
    >
      {{ item.title }}
      <div
        class="vs-main-nav__icon-wrapper vs-main-nav__icon-wrapper--chevron-down"
        :class="[show ? 'vs-main-nav__icon-wrapper--expanded' : '', level ? 'level' + level : '']"
      >
        <vs-svg path="icons/chevron-down" height="10" fill="#929091" />
      </div>
    </button>
    <a
      v-else
      class="vs-main-nav__link"
      :href="item.href"
      :class="[item.isExternal ? 'external' : '', level ? 'vs-main-nav__link--level' + level : '']"
      :target="item.isExternal ? '_blank' : false"
      :data-di-id="item.trackingID"
      >{{ item.title }}</a
    >
    <transition name="slide-fade" v-if="hasPopup(item)">
      <div v-if="show">
        <ul
          class="vs-main-nav__list"
          :class="[
            show ? 'expanded' : '',
            incrementLevel(level) ? 'vs-main-nav__list--level' + incrementLevel(level) : '',
          ]"
        >
          <li
            class="vs-main-nav__list-item"
            :class="'vs-main-nav__list-item--level' + incrementLevel(level)"
            v-if="item.href !== null"
          >
            <a
              class="vs-main-nav__link vs-main-nav__link--landing-page"
              :href="item.href"
              :class="[
                item.isExternal ? 'external' : '',
                level ? 'vs-main-nav__link--level' + incrementLevel(level) : '',
              ]"
              :target="item.isExternal ? '_blank' : false"
              :data-di-id="item.trackingID"
              >See all {{ titleToLowerCase(item.title) }}
              <div class="vs-main-nav__icon-wrapper vs-main-nav__icon-wrapper--chevron-right">
                <vs-svg path="icons/chevron-right" height="15" fill="#AF006E" />
              </div>
            </a>
          </li>
          <VsMainNavListItem
            v-for="(subnav, index) in item.subnav"
            :level="incrementLevel(level)"
            :item="subnav"
            :key="index"
          />
        </ul>
        <VsMainNavPromoItem v-if="item.promoItem" :item="item.promoItem" />
        <VsMainNavPromoList v-if="item.promoList" :list="item.promoList" />
      </div>
    </transition>
  </component>
</template>

<script>
import VsSvg from "../../../elements/svg/Svg"

export default {
  name: "VsMainNavListItem",
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
      default: "li",
    },
    item: {
      type: Object,
      required: true,
    },
    level: {
      type: Number,
    },
  },
  methods: {
    hasPopup(item) {
      if (
        item.subnav !== undefined ||
        item.promoItem !== undefined ||
        item.promoList !== undefined
      ) {
        return true
      }
      return false
    },
    incrementLevel(level) {
      return level + 1
    },
    titleToLowerCase(title) {
      return title.toLowerCase()
    },
    triggerToggle() {
      this.show = !this.show
      let thisTrigger = this.$el.querySelector("[data-toggle-trigger]")
      let headerWrapper = document.querySelector(".vs-header")
      if (this.show) {
        headerWrapper.scrollTo({
          behavior: "smooth",
          left: 0,
          top: thisTrigger.offsetTop + 68,
        })
      } else {
        headerWrapper.scrollTo({
          behavior: "smooth",
          left: 0,
          top: 0,
        })
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
@import "../styles/placeholders";
@import "../styles/animations";

.vs-main-nav {
  &__icon-wrapper {
    &--chevron-down {
      margin-left: 5px;
      transition: transform 250ms;
    }

    &--expanded {
      transform: rotate(180deg);
      transform-origin: 50% 54%;

      &.level1 {
        svg {
          fill: #ffffff !important;
        }
      }
    }

    &--chevron-right {
      margin-top: -3px;
    }
  }

  &__list {
    @extend %list-reset;
  }

  &__button {
    @extend %button-reset;
  }

  &__button,
  &__link {
    color: $color-total-eclipse;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-weight: $font-weight-semi-bold;
    font-size: 1.125rem;
    transition: background-color 250ms ease-in-out;

    &:focus {
      @extend %focus-pink-inset;
    }

    &--level1 {
      box-shadow: 0 1px 0 0 $color-mid-granite;
      margin: 0 1rem;
      padding-top: 1rem;
      width: calc(100% - 2rem);

      &[aria-expanded="true"] {
        background-color: $color-dark-granite;
        color: $color-white;
        margin: 0;
        padding: 0.5rem 1rem;
        width: 100%;
      }
    }

    &--level2 {
      box-shadow: 0 1px 0 0 $color-mid-granite;
      margin: 0 1rem 0 2rem;
      padding-top: 1.5rem;
      width: calc(100% - 3rem);
    }

    &--level3 {
      margin: 0 1rem 0 3rem;
      padding-top: 1.5rem;
      width: calc(100% - 4rem);
    }

    &--level3,
    &--level2 {
      font-size: 0.875rem;
      font-weight: $font-weight-normal;

      &[aria-expanded="true"] {
        color: $color-thistle-pink;
        box-shadow: inset 0 -1px 0 0 $color-thistle-pink;
        font-weight: $font-weight-semi-bold;
      }
    }
  }

  &__link {
    &--landing-page {
      box-shadow: 0 1px 0 0 $color-thistle-pink;
      color: $color-thistle-pink;

      &:hover {
        color: $color-thistle-pink;
      }
    }
    &--level3 {
      border-bottom: none;
    }
  }
}
</style>

<docs>
  ```jsx
  <div style="position: relative; height: 100vh;">
    <vs-main-nav-list-item
        :item="{
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
            }"
    />
  </div>
  ```
</docs>
