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
        <dl class="list-inline-item" v-for="(transportType, transportTypeIndex) in day.transport">
          <vs-itinerary-transport-type :transportType="transportType">
            <span class="sr-only">{{transportType.value}}</span>
          </vs-itinerary-transport-type>
        </dl>
      </dl>

      <div slot="day-introduction" v-html="day.introduction"></div>
    </vs-itinerary-day>
  </ul>
```
</docs>
