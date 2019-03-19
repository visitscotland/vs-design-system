<template>
  <div class="vs-list" data-test="vs-list">
    <div class="clearfix"><slot name="list-heading"></slot> <slot name="list-filters"></slot></div>
    <div class="vs-list__current-filters mb-3">
      <span v-if="currentFilters.length === 0">{{ labels.filtersDefault }}</span>
      <span v-else>{{ labels.filtersApplied }}</span>
      <button
        v-for="currentFilter in currentFilters"
        :key="currentFilter.key"
        @click="removeFilter(currentFilter)"
        class="button button--tag button--icon-right mr-1 mb-1"
      >
        {{ currentFilter.label }}<i class="icon icon-cross"></i>
      </button>
    </div>
    <div v-if="noResults" class="vs-list__no-results">{{ labels.noResults }}</div>
    <div v-else>
      <div class="vs-list__wrapper" ref="listItemsWrapper" :style="{ height: listHeight }">
        <div ref="listItemsWrapperInner">
          <slot v-if="showSlotListItems" name="list-items"></slot>
          <div v-else v-html="listItemsHTML"></div>
        </div>
      </div>
      <vs-list-pagination
        v-if="paginate"
        ref="paginate"
        :page-count="totalPages"
        :initial-page="currentPage"
        :click-handler="paginateClicked"
        container-class="vs-list-pagination"
        prev-class="vs-list-pagination__prev"
        next-class="vs-list-pagination__next"
        page-class="vs-list-pagination__link"
        active-class="vs-list-pagination__link--active"
        disabled-class="disabled"
      >
        <span slot="prevContent"><i class="icon icon-arrow-left"></i></span>
        <span slot="nextContent"
          >{{ labels.paginationNext }} <i class="icon icon-arrow-right"></i
        ></span>
      </vs-list-pagination>
    </div>
  </div>
</template>

<script>
import VsListPagination from "vuejs-paginate"
import Axios from "axios"

export default {
  name: "VsList",
  components: {
    VsListPagination,
  },
  props: {
    api: {
      type: String,
      required: true,
    },
    apiAppendQuerystring: {
      type: String,
      required: false,
    },
    initTotalPages: {
      type: Number,
      required: true,
    },
    labels: {
      type: Object,
      required: true,
    },
    pageSize: {
      type: Number,
      required: true,
    },
    paginate: {
      type: Boolean,
      required: false,
      default: true,
    },
  },
  data() {
    return {
      currentFilters: [],
      currentPage: 0,
      noResults: false,
      totalPages: null,
      showSlotListItems: true,
      showLoadedListItems: false,
      listItemsHTML: "",
      listHeight: "auto",
    }
  },
  created: function() {
    // update filters when filter triggers event.
    this.$on("filterschanged", () => {
      this.updateFilters()
    })

    // set total pages.
    this.totalPages = this.initTotalPages
  },
  mounted: function() {
    this.addPaginationEventListeners()
    this.updateListHeight(true)

    // update height of list wrapper as window resizes.
    window.addEventListener("resize", () => {
      this.updateListHeight(true)
    })
  },
  updated: function() {
    this.addPaginationEventListeners()
    this.updatePagination()
    this.updateListHeight(false)
  },
  methods: {
    updateFilters: function() {
      // All filters being applied.
      let allCurrentFilters = []

      // get the applied filters from vs-list-filters child components.
      Array.prototype.forEach.call(this.$children, el => {
        if (typeof el.getCurrentFilters === "function") {
          // get current filters.
          let vsListFilters = el.getCurrentFilters()

          // add filters to overall array of filters.
          allCurrentFilters = allCurrentFilters.concat(vsListFilters)
        }
      })

      // update current filters for vs-list.
      this.currentFilters = allCurrentFilters

      // set current page.
      this.currentPage = 0

      // reset pagination.
      if (this.$refs.paginate) {
        this.$refs.paginate.selected = 0
      }

      // update the list.
      this.updateList()
    },
    removeFilter: function(filter) {
      // call removeFilter on each child component vs-list-filter.
      Array.prototype.forEach.call(this.$children, el => {
        if (typeof el.getCurrentFilters === "function") {
          // remove filter (if it exists).
          el.removeFilter(filter)
        }
      })
    },
    updateList: function() {
      // parameters for url.
      let params = {}

      // Loop through the current filters and create a params object to represent the parameters for the URL request.
      Array.prototype.forEach.call(this.currentFilters, function(filter) {
        // if the filterkey doesn't exist and a new array with the filter.key, otherwise push it into the existing array.
        if (!params[filter.filterkey]) {
          params[filter.filterkey] = [filter.key]
        } else {
          params[filter.filterkey].push(filter.key)
        }
      })

      // API's base URL.
      let querystring = ""

      // Loop through params object and create api querystring.
      Object.keys(params).forEach(function(key) {
        querystring = querystring.concat("&" + key.toString() + "=" + params[key].join(","))
      })

      // add the additional querystrings.
      querystring = querystring.concat("&page=", this.currentPage, "&pageSize=", this.pageSize)

      // Append a default query string.
      if (this.apiAppendQuerystring) {
        querystring = querystring.concat("&", this.apiAppendQuerystring)
      }

      // load list HTML for params.
      this.loadHTML(querystring)
    },
    updatePagination: function() {
      // get the list DOM element.
      let list = this.$el.querySelector("[data-component-list]")

      // if the list doesn't exist, return.
      if (!list) {
        return
      }

      // get the list settings as JSON.
      let listSettings = JSON.parse(list.getAttribute("data-component-list"))

      // update the total pages.
      this.totalPages = listSettings.totalPages
    },
    addPaginationEventListeners: function() {
      // get pagination element.
      let pagination = this.$el.querySelector("[data-component-pagination]")

      // do nothing if the pagination element doesn't exist.
      if (!pagination) {
        return
      }

      // add click event listener.
      pagination.addEventListener("click", e => {
        if (e.target.hasAttribute("data-pagination-link")) {
          this.loadHTML(e.target.getAttribute("data-pagination-link"))
        }
      })
    },
    paginateClicked: function(pageNumber) {
      // change the current page.
      this.currentPage = pageNumber

      // update the list.
      this.updateList()
    },
    loadHTML: function(querystring) {
      // get params if it's a fully qualified URL.
      if (querystring.indexOf("?") > 0) {
        querystring = querystring.substring(querystring.indexOf("?") + 1)
      }

      // API's URL.
      let api = this.api + "?" + querystring

      // get the list HTML from the API.
      Axios.get(api).then(response => {
        // inject the response.data as it is already HTML.
        this.listItemsHTML = response.data

        // hide original slot list items.
        this.showSlotListItems = false

        // check if no results received.
        if (response.data.indexOf("data-component-list") !== -1) {
          this.noResults = false
        } else {
          this.noResults = true
        }
      })

      // scroll the window to the top of the list.
      this.scrollWindowToTopOfList()
    },
    updateListHeight: function(disableTransition) {
      // check if list wrapper exists.
      if (typeof this.$refs.listItemsWrapper === "undefined") {
        return
      }

      // disable/enable transitions.
      if (disableTransition) {
        this.$refs.listItemsWrapper.classList.add("vs-list__wrapper--transition-disabled")
      } else {
        this.$refs.listItemsWrapper.classList.remove("vs-list__wrapper--transition-disabled")
      }

      // set height of list.
      this.listHeight = this.$refs.listItemsWrapperInner.offsetHeight + "px"
    },
    scrollWindowToTopOfList: function() {
      // animate scroll of the webpage back to the top of the list.
      setTimeout(() => {
        window.scroll({
          top: this.$el.offsetTop - 20,
          left: 0,
          behavior: "smooth",
        })
      }, 150)
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/functions";
@import "~bootstrap/scss/variables";
@import "~bootstrap/scss/mixins";

$vs-list-item-enter: 500ms;
$vs-list-item-enter-step: 75ms;

.vs-list {
  &__current-filters {
    font-size: vs-font-size(xxs);

    > span {
      display: inline-block;
      padding: calc(0.33em + 2px) 0.5em calc(0.33em + 2px) 0;
      margin-right: 4px;
      margin-bottom: 4px;
    }
  }

  &__wrapper {
    transition: height 350ms ease-in-out;

    &--transition-disabled {
      transition: none;
    }
  }

  &__no-results {
    margin-bottom: 3em;
    padding: 3em;
    background: rgba($color_mid_granite, 0.075);
    border-radius: $border-radius;
  }

  @supports (animation: vs-list-items-enter) {
    .card {
      opacity: 0;
      animation: vs-list-items-enter $vs-list-item-enter forwards;
      animation-delay: $vs-list-item-enter-step * 12;
      transform: scale3d(1, 1, 1);

      @for $i from 1 to 12 {
        &:nth-child(#{$i}) {
          animation-delay: $vs-list-item-enter-step * $i;
        }
        $i: $i + 1;
      }
    }
  }
}

@keyframes vs-list-items-enter {
  0% {
    opacity: 0;
    transform: translateY(10px) scale3d(0.95, 0.95, 0.95);
  }

  100% {
    opacity: 1;
    transform: translateY(0) scale3d(1, 1, 1);
  }
}

/**
 * Pagination
 */

.vs-list-pagination {
  width: 100%;
  text-align: center;
  user-select: none;

  &,
  li {
    margin: 0;
    padding: 0;
    list-style: none;
  }

  li {
    display: inline-block;
    margin-bottom: 5px;

    &.disabled a {
      opacity: 0.3;
      filter: grayscale(100%);
      pointer-events: none;
      transition-duration: 350ms;
    }
  }

  a {
    overflow: hidden;
    position: relative;
    display: block;
    margin: 0 2px;
    padding: 0 0.5em;
    height: 2em;
    line-height: 1.8;
    min-width: 2em;
    border-radius: 5px;
    text-align: center;
    transition: all 0s;

    &:focus {
      outline: none;
    }
  }

  &__prev a {
    border: 1px solid $color_dark_granite;

    &:focus,
    &:hover {
      border-color: $color_heather_pink;
      background: $color_heather_pink;
      color: white;
    }

    .icon {
      font-size: 12px;
    }
  }

  &__link a {
    border: 1px solid transparent;
    color: $color_dark_granite;

    &:focus,
    &:hover {
      background: $color_mid_granite;
      color: white;
    }
  }

  &__link--active a {
    &,
    &:focus,
    &:hover {
      border-color: $color_heather_pink;
      background: $color_heather_pink;
      color: white;
    }
  }

  &__next a {
    border-color: $color_heather_pink;
    background: $color_heather_pink;
    color: white;
    padding-left: 0.75em;
    padding-right: 2em;

    &:focus,
    &:hover {
      border-color: $color_heather_pink;
      background: $color_heather_pink;
      color: white;
    }

    .icon {
      position: absolute;
      top: 50%;
      right: 0.75em;
      width: 1em;
      height: 1em;
      line-height: 1;
      margin-top: -0.5em;
      font-size: 12px;
    }
  }
}
</style>

<docs>
    ```jsx
    <VsList
        api="https://www.visitscotland.org/data/newslist"
        api-append-querystring="hideFutureItems=true&pagination=true"
        v-bind:init-total-pages="47"
        v-bind:labels="{
            filtersDefault: 'Showing latest news.',
            filtersApplied: 'Showing news for:',
            paginationPrev: 'Previous',
            paginationNext: 'Next',
            noResults:      'Sorry, there are no results.'
        }"
        v-bind:page-size="4"
        v-bind:paginate="true"
    >
        <h2 slot="list-heading" class="my-0 mb-4">Latest news</h2>

        <div slot="list-items">
            <div class="container" data-test="component-list" data-component-list='{"totalPages":47}'>
                <div class="row">
                    <div class="col-12">
                        <div class="cards cards--x4">

                            <article class="card card--news has-edit-button" data-test="component-card">
                                <a class="card__link" href="https://www.visitscotland.org/news/2019/herscotland-golf-campaign">
                                    <div class="card__link__image" data-test="component-card-image">
                                        <div class="card__link__image__background" style="background-image: url(https://www.visitscotland.org/binaries/content/gallery/herscotland-2.jpg/herscotland-2.jpg/bloat%3Amd);"></div>
                                        <div class="card__link__image__overlay"></div>
                                    </div>

                                    <div class="card__link__content">
                                            <div class="card__link__content__date" data-test="component-card-date">12/02/2019</div>
                                            <h3 class="card__link__content__title" data-test="component-card-title">#HerScotland</h3>
                                            <p class="card__link__content__summary" data-test="component-card-summary">See our latest video #HerScotland, part of our latest campaign aimed at encouraging female golfers to play golf in Scotland!</p>
                                    </div>
                                    <div class="card__link__footer" data-test="component-card-summary">
                                        <p class="card__link__footer__tags">
                                                    Aberdeen & Aberdeenshire,
                                                    Argyll & The Isles,
                                                    Ayrshire & Arran...
                                        </p>
                                    </div>
                                </a>
                            </article>
                            <article class="card card--news has-edit-button" data-test="component-card">
                                <a class="card__link" href="https://www.visitscotland.org/news/2019/scotland-host-uci-cycling-world-championships-2023">
                                    <div class="card__link__image" data-test="component-card-image">
                                        <div class="card__link__image__background" style="background-image: url(https://www.visitscotland.org/binaries/content/gallery/dot-org/news-images/uci-cycling2.jpg/uci-cycling2.jpg/bloat%3Amd);"></div>
                                        <div class="card__link__image__overlay"></div>
                                    </div>

                                    <div class="card__link__content">
                                            <div class="card__link__content__date" data-test="component-card-date">11/02/2019</div>
                                            <h3 class="card__link__content__title" data-test="component-card-title">UCI Cycling World Championships</h3>
                                            <p class="card__link__content__summary" data-test="component-card-summary">Scotland will host the inaugural UCI Cycling World Championships in August 2023.</p>
                                    </div>
                                    <div class="card__link__footer" data-test="component-card-summary">
                                        <p class="card__link__footer__tags">
                                                    Aberdeen & Aberdeenshire,
                                                    Argyll & The Isles,
                                                    Ayrshire & Arran...
                                        </p>
                                    </div>
                                </a>
                            </article>
                            <article class="card card--news has-edit-button" data-test="component-card">
                                <a class="card__link" href="https://www.visitscotland.org/news/2019/regional-director-celebrates-argyll-tourism">
                                    <div class="card__link__image" data-test="component-card-image">
                                        <div class="card__link__image__background" style="background-image: url(https://www.visitscotland.org/binaries/content/gallery/dot-org/news-images/david-op-ed.jpg/david-op-ed.jpg/bloat%3Amd);"></div>
                                        <div class="card__link__image__overlay"></div>
                                    </div>

                                    <div class="card__link__content">
                                            <div class="card__link__content__date" data-test="component-card-date">11/02/2019</div>
                                            <h3 class="card__link__content__title" data-test="component-card-title">Celebrating tourism in Argyll</h3>
                                            <p class="card__link__content__summary" data-test="component-card-summary">Our Regional Director David Adams McGilp reflects on a successful year for Argyll tourism in 2018 and discusses things to look forward to this year.</p>
                                    </div>
                                    <div class="card__link__footer" data-test="component-card-summary">
                                        <p class="card__link__footer__tags">
                                                    Argyll & The Isles                </p>
                                    </div>
                                </a>
                            </article>
                            <article class="card card--news has-edit-button" data-test="component-card">
                                <a class="card__link" href="https://www.visitscotland.org/news/2019/solheim-cup-industry-event">
                                    <div class="card__link__image" data-test="component-card-image">
                                        <div class="card__link__image__background" style="background-image: url(https://www.visitscotland.org/binaries/content/gallery/visit-scotland-solheim-cup-2019.jpg/visit-scotland-solheim-cup-2019.jpg/bloat%3Amd);"></div>
                                        <div class="card__link__image__overlay"></div>
                                    </div>

                                    <div class="card__link__content">
                                            <div class="card__link__content__date" data-test="component-card-date">08/02/2019</div>
                                            <h3 class="card__link__content__title" data-test="component-card-title">Let’s get Solheim Cup ready!</h3>
                                            <p class="card__link__content__summary" data-test="component-card-summary">Hear about our 2019 Solheim Cup industry event which took place on Wednesday (6 February), with access to the presentations, our business toolkit&hellip;</p>
                                    </div>
                                    <div class="card__link__footer" data-test="component-card-summary">
                                        <p class="card__link__footer__tags">
                                                    Dundee & Angus,
                                                    Fife,
                                                    Loch Lomond, The Trossachs, Stirling & Forth Valley...
                                        </p>
                                    </div>
                                </a>
                            </article>
                        </div>
                    </div>
                </div>
            </div>


        </div>
    </VsList>
</docs>
