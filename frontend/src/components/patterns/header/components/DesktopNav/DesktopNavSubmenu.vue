<template>
    <Component
        :is="type"
        data-test="desktop-nav-submenu"
    >
        <BCollapse
            data-test="desktop-nav-submenu-wrapper"
            :id="formattedCollapsePaneId"
            class="vs-desktop-submenu__wrapper"
        >
            <VsContainer>
                <VsRow class="mb-3">
                    <VsCol class="position-relative">
                        <span class="vs-desktop-submenu__header">{{ title }}</span>
                        <button
                            class="vs-desktop-submenu__close-button"
                            :id="formattedSubmenuCloseId"
                            v-b-toggle="formattedCollapsePaneId"
                            @keydown="checkKeydownReverse($event)"
                            @click="setFocusOnToggle()"
                        >
                            <span class="sr-only">Close the {{ lowerCaseTitle }} submenu</span>
                            <VsIcon
                                name="close"
                                size="xs"
                                variant="dark"
                            />
                        </button>
                    </VsCol>
                </VsRow>
                <ul
                    data-test="desktop-nav-submenu-list"
                    class="list-unstyled row flex-column vs-desktop-submenu__list"
                >
                    <slot name="subnav" />
                    <VsDesktopNavPromoItem
                        v-if="promoItem"
                        :href="promoItem.href"
                        :is-external="promoItem.isExternal"
                        :title="promoItem.title"
                        :button-text="promoItem.buttonText"
                        :description="promoItem.description"
                        :image-link="promoItem.imageLink"
                    />
                    <template v-if="promoList">
                        <VsDesktopNavPromoItem
                            v-for="(item, index) in promoList"
                            :key="index"
                            :href="header.mainNav[0].href"
                            :is-external="header.mainNav[0].isExternal"
                            :title="header.mainNav[0].title"
                            :button-text="header.mainNav[0].buttonText"
                            :description="header.mainNav[0].description"
                            :image-link="header.mainNav[0].imageLink"
                        />
                    </template>
                    <template v-if="chartWidgets">
                        <VsDesktopNavChart
                            v-for="(chart, index) in chartWidgets"
                            :chart-title="chart.chartTitle"
                            :labels="chart.labels"
                            :datasets="chart.datasets"
                            :options="chart.options"
                            :chart-id="'chartId' + index"
                            :key="'chart' + index"
                        />
                    </template>
                    <li
                        data-test="desktop-nav-submenu-landing-page-link"
                        class="vs-desktop-submenu__list-item
                        vs-desktop-submenu__list-item--landing-page-link"
                    >
                        <a
                            :href="href"
                            class="vs-desktop-submenu__landing-page-link"
                            @keydown="checkKeydownForward($event)"
                        >See all {{ lowerCaseTitle }}</a>
                    </li>
                </ul>
            </VsContainer>
        </BCollapse>
    </Component>
</template>

<script>
import { BCollapse, VBToggle } from "bootstrap-vue"
import VsIcon from "../../../../elements/icon/Icon"
import VsContainer from "../../../../elements/layout/Container"
import VsRow from "../../../../elements/layout/Row"
import VsCol from "../../../../elements/layout/Col"

export default {
    name: "VsDesktopNavSubmenu",
    status: "prototype",
    release: "0.0.1",
    components: {
        BCollapse,
        VsCol,
        VsContainer,
        VsIcon,
        VsRow,
    },
    directives: {
        "b-toggle": VBToggle,
    },
    props: {
        /**
         * The html element name used for the component
         */
        type: {
            type: String,
            default: "li",
        },
        href: {
            type: String,
            default: "",
        },
        isExternal: {
            type: Boolean,
        },
        trackingId: {
            type: String,
            default: "",
        },
        title: {
            type: String,
            default: "",
        },
        level: {
            type: Number,
            default: 0,
        },
        subnav: {
            type: Array,
            default() {
                return []
            },
        },
        promoList: {
            type: Array,
            default() {
                return []
            },
        },
        promoItem: {
            type: Object,
            default() {
                return {
                }
            },
        },
        chartWidgets: {
            type: Array,
            default() {
                return []
            },
        },
        subnavId: {
            type: Number,
            default: 0,
        },
    },
    data() {
        return {
        }
    },
    computed: {
        formattedCollapsePaneId() {
            return `collapse-subnav-${this.subnavId}`
        },
        formattedSubmenuCloseId() {
            return `submenu-close-${this.subnavId}`
        },
        lowerCaseTitle() {
            return this.title ? this.title.toLowerCase() : ""
        },
    },
    methods: {
        close() {
            this.setFocusOnToggle()
            this.$root.$emit("bv::toggle::collapse", this.formattedCollapsePaneId)
        },
        checkKeydownForward($event) {
            if ($event.key === "Tab" && !$event.shiftKey) {
                this.close()
            }
        },
        checkKeydownReverse($event) {
            if ($event.key === "Tab" && $event.shiftKey) {
                this.close()
            }
        },
        setFocusOnToggle() {
            const menuToggle = `collapse-toggle-${this.subnavId}`
            setTimeout(() => {
                document.getElementById(menuToggle).focus()
            }, 100)
        },
    },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";
@import "../../styles/collapse";
@import "../../styles/placeholders";
@import "../../styles/mixins";

.vs-desktop-submenu__wrapper {
  @extend %default-inset-box-shadow;
  position: relative;
  margin-bottom: 40px;
  padding: 40px 0;
}

.vs-desktop-submenu__header {
  color: $color-gray-shade-2;
  display: inline-block;
  font-size: 2rem;
  font-weight: $font-weight-light;
  letter-spacing: 1px;
  text-transform: uppercase;
}

.vs-desktop-submenu__list {
  max-height: 100vh;
  flex-wrap: wrap;

  @include media-breakpoint-up(xl) {
    max-height: 550px;
  }
}

.vs-desktop-submenu__close-button {
  @extend %button-reset;
  display: inline-block;
  position: absolute;
  right: 1rem;
  height: 50px;
  width: 50px;

  &:hover,
  &:focus {
    @include focus-underline($color-pink);
  }
}

.vs-desktop-submenu__landing-page-link {
  color: $color-white;
  background-color: $color-gray;
  font-size: 1.1875rem;
  display: block;
  padding: 0.5rem 1rem;
  transition: background-color 250ms ease-in-out;

  &:focus {
    background-color: $color-pink;
  }

  &:hover {
    background-color: $color-gray-shade-2;
    color: $color-white;
  }
}

.vs-desktop-submenu__list-item--landing-page-link {
  position: absolute;
  bottom: 0;
  right: 0;
  min-width: 33%;

  @include media-breakpoint-up(xl) {
    min-width: 25%;
  }
}
</style>

<docs>
  ```jsx

  <div>

    <ul style="display: flex; list-style-type: none; margin: 0;">
    <vs-desktop-nav-toggles
      :level="1"
      :href="header.mainNav[0].href"
      :is-external="header.mainNav[0].isExternal"
      :title="header.mainNav[0].title"
      :subnav="header.mainNav[0].subnav"
      :promo-list="header.mainNav[0].promoList"
      :promo-item="header.mainNav[0].promoItem"
      :chartWidgets="header.mainNav[0].chartWidgets"
      :toggleId="1"
    />
    </ul>

    <ul style="list-style-type: none; margin: 0; padding: 0;">

    <vs-desktop-nav-submenu
        :level="1"
        :href="header.mainNav[0].href"
        :is-external="header.mainNav[0].isExternal"
        :title="header.mainNav[0].title"
        :subnav="header.mainNav[0].subnav"
        :promo-list="header.mainNav[0].promoList"
        :promo-item="header.mainNav[0].promoItem"
        :chartWidgets="header.mainNav[0].chartWidgets"
        :subnav-id="1"
      >
      <vs-desktop-nav-list-item
          slot="subnav"
          v-for="(level2, index2) in header.mainNav[0].subnav"
          :level="2"
          :href="level2.href"
          :is-external="level2.isExternal"
          :title="level2.title"
          :subnav="level2.subnav"
          :key="index2"
        >
          <vs-desktop-nav-list-item
            slot="subnav"
            v-for="(level3, index3) in level2.subnav"
            :level="3"
            :href="level3.href"
            :is-external="level3.isExternal"
            :title="level3.title"
            :subnav="level3.subnav"
            :key="index3"
          >
          </vs-desktop-nav-list-item>
        </vs-desktop-nav-list-item>
      </vs-desktop-nav-submenu>
  </ul>
  </div>
  ```
</docs>
