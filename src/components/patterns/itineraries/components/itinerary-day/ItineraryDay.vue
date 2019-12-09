<template>
  <component :is="type" class="vs-itinerary-day__list-item">
    <vs-container>
      <div class="vs-itinerary-day__header text-center position-relative">
        <slot name="day-title" />
        <vs-button
          class="vs-itinerary-day__toggle-button position-absolute p-0"
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
      <div v-if="this.show">
        <vs-row align-h="center">
          <vs-col cols="12" sm="6" md="5">
            <slot name="day-distance" />
          </vs-col>
          <vs-col cols="12" sm="6" md="5">
            <slot name="day-transport" />
          </vs-col>
        </vs-row>
        <slot name="day-introduction" />
        <slot name="stops" />
      </div>
    </vs-container>
  </component>
</template>

<script>
import VsIcon from "@components/elements/icon/Icon"
import VsButton from "@components/elements/button/Button"
import { VsContainer, VsRow, VsCol } from "@components/elements/layout"

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
    VsContainer,
    VsRow,
    VsCol,
  },
  data() {
    return {
      show: this.defaultShow,
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
    triggerToggle() {
      this.show = !this.show
    },
  },
}
</script>

<style lang="scss" scoped>
.vs-itinerary-day__list-item {
  border-top: 5px solid $color-base-text;
  padding: $spacer-4 0;
  margin-bottom: $spacer-4;
}

.vs-itinerary-day__header ::v-deep .vs-itinerary-day__title > span {
  border-bottom: 1px solid $color-base-text;
  color: $color-theme-secondary-teal;
  display: block;
  font-family: $headings-font-family;
  margin-bottom: $spacer-4;
  padding: $spacer-3;
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
  
  <dl v-if="day.dayMiles && day.dayKM" slot="day-distance" class="list-inline text-center">
    <dt class="list-inline-item mb-0"><abbr title="miles">mi</abbr>/<abbr title="kilometres">km</abbr>:</dt>
    <dd class="list-inline-item mb-0">{{day.dayMiles}}/{{day.dayKM}}</dd>
  </dl>

  <dl v-if="day.transport.length" class="list-inline text-center" slot="day-transport">
    <dt class="list-inline-item">Transport:</dt>
    <dl class="list-inline-item" v-for="(transportType, transportTypeIndex) in day.transport">
      <vs-icon :name="transportType.key" variant="dark" size="sm" />
      <span class="sr-only">{{transportType.value}}</span>
    </dl>
  </dl>

  <div slot="day-introduction" v-html="day.introduction"></div>
  </vs-itinerary-day>
  </ul>
  ```
</docs>
