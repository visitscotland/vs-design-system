<template>
  <component :is="type" class="vs-itinerary-day__list-item">
    <div class="vs-itinerary-day__header text-center position-relative">
      <slot name="day-title" />
      <vs-button
        class="vs-itinerary-day__toggle-button position-absolute p-0"
        v-if="!this.isDesktop"
        variant="transparent"
        :animate="false"
        @click.native="triggerToggle()"
        aria-haspopup="true"
        :aria-expanded="show ? 'true' : 'false'"
      >
        <vs-icon v-if="this.show" name="chevron-down" variant="dark" size="xs" :padding="3" />
        <vs-icon v-else name="chevron-up" variant="dark" size="xs" :padding="3" />
      </vs-button>
    </div>
    <div v-show="this.show || this.isDesktop">
      <slot name="day-transport" />
      <slot name="day-introduction" />
      <slot name="stops" />
      <slot name="nearby-links" />
    </div>
  </component>
</template>

<script>
import VsIcon from "@components/elements/icon/Icon"
import VsButton from "@components/elements/button/Button"
import { VsRow, VsCol } from "@components/elements/layout"

/**
 * TODO: Document usage.
 */

export default {
  name: "VsItineraryDay",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsButton,
    VsIcon,
    VsRow,
    VsCol,
  },
  data() {
    return {
      show: this.defaultShow,
      isDesktop: window.innerWidth >= 1200 ? true : false,
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
    defaultShow: {
      type: Boolean,
      default: true,
    },
  },
  // TODO: add watcher for when stop activated and expand all the days on mobile
  // so that stops can be scrolled to
  methods: {
    onResize() {
      this.isDesktop = window.innerWidth >= 1200 ? true : false
    },
    triggerToggle() {
      this.show = !this.show
    },
  },
  mounted() {
    window.addEventListener("resize", this.onResize)
  },
  destroyed() {
    window.removeEventListener("resize", this.onResize)
  },
}
</script>

<style lang="scss" scoped>
.vs-itinerary-day__list-item {
  @include media-breakpoint-down(md) {
    border-top: 5px solid $color-base-text;
  }
  padding: $spacer-4 0;
  margin-bottom: $spacer-4;

  .vs-itinerary-stop__list-item {
    background-color: $color-white;
    border: 1px solid $color-gray-tint-5;
    padding: $spacer-4;
    margin-bottom: $spacer-4;

    .flex-fill {
      max-width: 160px;
      @media (min-width: 340px) {
        max-width: none;
      }
    }

    .vs-itinerary-stop__title span {
      font-family: $headings-font-family;
      display: block;
    }

    .itinerary-stop__facilities {
      border-top: 1px solid $color-gray-tint-5;
      margin: $spacer-9 -1rem -1rem;
      padding: 1rem;
      text-align: center;

      dt {
        margin-bottom: 1rem;
        flex-basis: 1;
        display: block;
        width: 100%;
      }

      dd {
        display: inline-table;
        text-align: center;
        width: 90px;
        position: relative;

        .icon-wrapper {
          display: block;
          width: 100%;
        }
      }
    }
  }
}

.vs-itinerary-day__header ::v-deep .vs-itinerary-day__title > span {
  border-bottom: 1px solid $color-base-text;
  color: $color-theme-secondary-teal;
  display: block;
  font-family: $headings-font-family;
  margin-bottom: $spacer-4;
  padding: $spacer-3 $spacer-6;
}

.vs-itinerary-day__toggle-button {
  border: 1px solid $color-base-text;
  border-radius: 50%;
  top: $spacer-2;
  right: 0;
}
</style>

<docs>
```jsx
  <ul class="list-unstyled">
    <vs-itinerary-day 
      v-for="(day, index) in itineraries.sampleItinerary.days"
      :defaultShow="(day.dayCount < 3) ? true : false"
      :key="index"
    >
      <vs-heading 
        slot="day-title"
        level="2" 
        thin 
        class="vs-itinerary-day__title">
        <span>Day {{day.dayCount}}</span>
        {{day.title}}
      </vs-heading>
    
      <dl v-if="day.transport.length" class="list-inline text-center" slot="day-transport">
        <dt class="list-inline-item">Transport:</dt>
        <dd class="list-inline-item" v-for="(transportType, transportTypeIndex) in day.transport">
            <vs-tooltip :title="transportType.value">
              <vs-icon :name="transportType.key" variant="dark" size="sm" />
            </vs-tooltip>
            <span class="sr-only">{{transportType.value}}</span>
        </dd>
      </dl>

      <div slot="day-introduction" v-html="day.introduction"></div>
    </vs-itinerary-day>
  </ul>
```
</docs>
