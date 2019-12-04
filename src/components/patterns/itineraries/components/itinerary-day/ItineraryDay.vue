<template>
  <component :is="type" class="vs-itinerary-day__list-item">
    <div class="vs-itinerary-day__header text-center">
      <slot name="day-title" />
    </div>
    <slot name="day-distance" />
    <slot name="day-transport" />
    <slot name="day-introduction" />
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
    VsContainer,
    VsRow,
    VsCol,
    VsButton,
    VsIcon,
  },
  props: {
    /**
     * The html element name used for the component
     */
    type: {
      type: String,
      default: "li",
    },
  },
  computed: {},
  methods: {},
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";

.vs-itinerary-day__list-item {
  border-top: 5px solid $color-base-text;
  padding: $spacer-4;
  margin-bottom: $spacer-4;
}

.vs-itinerary-day__title ::v-deep > span {
  border-bottom: 1px solid $color-base-text;
  color: $color-theme-secondary-teal;
  display: block;
  font-family: $headings-font-family;
  margin-bottom: $spacer-4;
  padding: $spacer-3;
}

.itinerary-day__distance {
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
}
</style>

<docs>
```jsx
<ul style="list-style-type: none; padding: 0px;">
<vs-itinerary-day 
  v-for="(day, index) in itineraries.sampleItinerary.days"
  :key="index"
>
<vs-heading 
  slot="day-title"
  level="2" 
  thin 
  class="vs-itinerary-day__title">
  <span>Day {{day.count}}</span>
  {{day.title}}
</vs-heading>

  
  <dl v-if="day.dayMiles && day.dayKM" slot="day-distance" class="list-inline mb-0 text-center">
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
