<template>
  <div class="vs-list-filter" :data-test="'vs-list-filter-' + filterkey">
    <button
      @click="showDropdown = !showDropdown"
      @blur="buttonBlur()"
      class="button button--dropdown button--icon-right"
    >
      {{ label }} <i class="icon icon-chevron-down"></i>
    </button>
    <div
      class="vs-list-filter__list"
      :class="[showDropdown ? 'vs-list-filter__list--visible' : '']"
    >
      <div class="vs-list-filter__list__arrow"></div>
      <ul>
        <li v-for="item in items" :key="item.key">
          <button @click="toggleFilter(item.key)" @blur="buttonBlur()">
            <i v-if="isCurrentFilter(item.key)" class="icon icon-tick"></i>{{ item.label }}
          </button>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "VsFilter",
  props: {
    label: {
      type: String,
      required: true,
    },
    filterkey: {
      type: String,
      required: true,
    },
    items: {
      type: Array,
      required: true,
    },
  },
  data() {
    return {
      showDropdown: false,
      currentFilters: [],
    }
  },
  mounted: function() {
    // trigger blur event for browsers that don't support it as 'normal' (ORG-313).
    let fnTriggerButtonBlur = e => {
      if (this.showDropdown && !this.$el.contains(e.target)) {
        this.buttonBlur()
      }
    }

    // trigger in Firefox.
    window.addEventListener("click", e => {
      fnTriggerButtonBlur(e)
    })

    // trigger in touch devices (e.g. iOS/Safari).
    window.addEventListener("touchend", e => {
      fnTriggerButtonBlur(e)
    })
  },
  methods: {
    getCurrentFilters: function() {
      // array to store current filters
      let currentFiltersAsObjects = []

      // Loop through current filters and return object for each.
      Array.prototype.forEach.call(this.currentFilters, key => {
        // Filter matching items prop.
        let matchedFilter = this.items.filter(item => {
          return item.key === key
        })[0]

        // Add filter key to matched filter.
        matchedFilter.filterkey = this.filterkey

        // Add to current filters array.
        currentFiltersAsObjects.push(matchedFilter)
      })

      return currentFiltersAsObjects
    },
    toggleFilter: function(key) {
      // index of the key to be toggled.
      let indexOfKey = this.currentFilters.indexOf(key)

      // toggle the key.
      if (indexOfKey === -1) {
        this.currentFilters.push(key)
      } else {
        this.currentFilters.splice(indexOfKey, 1)
      }

      // emit event to parent.
      this.$parent.$emit("filterschanged")
    },
    removeFilter: function(filter) {
      // if the filterkey doesn't match, return.
      if (filter.filterkey !== this.filterkey) {
        return
      }

      // index of the key to be toggled.
      let indexOfKey = this.currentFilters.indexOf(filter.key)

      // remove the filter from the current filters.
      this.currentFilters.splice(indexOfKey, 1)

      // emit event to parent.
      this.$parent.$emit("filterschanged")
    },
    buttonBlur: function() {
      // if the dropdown is hidden, do nothing.
      if (!this.showDropdown) {
        return
      }

      // determine whether one of the buttons has focus (default to false).
      let buttonHasFocus = false

      // timeout to allow focus to change, then loop through all the buttons and check if any have focus.
      setTimeout(() => {
        // loop through buttons.
        Array.prototype.forEach.call(this.$el.querySelectorAll("button"), function(el) {
          if (el === document.activeElement) {
            buttonHasFocus = true
          }
        })

        // if none of the buttons have focus, hide the dropdown.
        if (!buttonHasFocus) {
          this.showDropdown = false
        }
      }, 50)
    },
    isCurrentFilter: function(filterKey) {
      if (this.currentFilters.indexOf(filterKey) !== -1) {
        return true
      }
      return false
    },
  },
}
</script>

<style lang="scss" scoped>
.vs-list-filter {
  display: inline-block;
  float: left;
  clear: left;

  &__list {
    visibility: hidden;
    position: relative;
    z-index: $zindex-dropdown;
    opacity: 0;
    transition: opacity 150ms, transform 300ms cubic-bezier(0.175, 0.885, 0.32, 1.275);
    transform: translateZ(0) scale3d(0.75, 0.75, 0.75);
    transform-origin: top left;

    &--visible {
      visibility: visible;
      transform: translateZ(0) scale3d(1, 1, 1);
      opacity: 1;
    }

    &__arrow {
      display: block;
      width: 0;
      height: 0;
      border-style: solid;
      border-width: 0 15px 20px 15px;
      border-color: transparent transparent $color_light_granite transparent;
      position: absolute;
      top: 1px;
      left: 50%;
      margin-left: -15px;
      z-index: 2;

      &::after {
        content: " ";
        width: 0;
        height: 0;
        border-style: solid;
        border-width: 0 14px 18px 14px;
        border-color: transparent transparent white transparent;
        position: absolute;
        top: 2px;
        left: -14px;
      }
    }

    ul,
    li {
      @include list-unstyled;
    }

    ul {
      display: block;
      overflow: hidden;
      width: 20em;
      max-width: 92vw;
      border-bottom: 1px solid $gray-300;
      box-shadow: 0 3px 7px rgba(black, 0.16);
      border-radius: $border-radius-lg;
      border: 1px solid $color_light_granite;
      position: absolute;
      top: 20px;
      left: 0;
      z-index: 1;
      user-select: none;
    }

    button {
      position: relative;
      display: block;
      width: 100%;
      padding: 0.5em 2.5em;
      border: none;
      border-bottom: none;
      background: white;
      color: $color_dark_granite;
      text-align: left;
      cursor: pointer;
      font-size: vs-font-size(xxs);

      &:hover {
        background: darken($color_very_light_granite, 2%);
        color: $color_total_eclipse;
      }

      &:focus {
        background: $color_very_light_granite;
        outline: none;
      }

      .icon {
        position: absolute;
        top: 0;
        right: 0.66em;
        bottom: 0;
        left: auto;
        margin: auto;
        width: 1em;
        height: 1em;
        line-height: 1;
        color: $color_mid_granite;
      }
    }
  }

  &__active {
    display: inline-block;

    button {
      @supports (white-space: nowrap) {
        max-width: 12em;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }
    }
  }

  @include media-breakpoint-up(md) {
    float: none;
    clear: none;
  }
}
</style>
