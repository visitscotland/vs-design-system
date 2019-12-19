<template>
  <component :is="type" cols="6" class="vs-summary-box__list-item p-2 text-center" tag="li">
    <div
      class="vs-summary-box__list-item__wrapper d-flex flex-column-reverse justify-content-between pt-5"
    >
      <slot />
    </div>
  </component>
</template>

<script>
import { VsCol } from "@components/elements/layout"
/**
 * Itinerary Stop image element
 */
export default {
  name: "VsSummaryBoxListItem",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsCol,
  },
  props: {
    /**
     * The image alt text for screen readers
     */
    type: {
      type: String,
      default: "vs-col",
    },
  },
}
</script>

<style lang="scss" scoped>
.vs-summary-box__list-item ::v-deep {
  dt {
    font-family: $headings-font-family;
    border-bottom: 1px solid $color-gray-tint-5;
    margin-bottom: $spacer-3;
    padding: $spacer-1 0;
  }

  .vs-summary-box__list-item__wrapper {
    border: 1px solid $color-base-text;
    width: 100%;
    height: 100%;
    min-height: 100px;
    font-size: 0.875rem;

    span {
      font-size: 1.625rem;
      font-family: $headings-font-family;

      &.divider {
        font-family: $headings-font-family-thin;
      }
    }
  }
}
</style>

<docs>
  
  ```jsx
    <vs-summary-box-list>
      <vs-summary-box-list-item>
        <strong>Days</strong>
        <span>{{itineraries.sampleItinerary.totalDays}}</span>
      </vs-summary-box-list-item>
      <vs-summary-box-list-item>
        <strong>Distance <br /><abbr title="miles">mi</abbr>/<abbr title="kilometres">km</abbr></strong>
        <span>{{itineraries.sampleItinerary.totalMiles}}<span class="divider">/</span>{{itineraries.sampleItinerary.totalKM}}</span>
      </vs-summary-box-list-item>
      <vs-summary-box-list-item>
        <strong>Transport</strong>
        <div class="icon-wrapper">
          <vs-icon :name="itineraries.sampleItinerary.transport.key" variant="dark" size="sm" />
          {{itineraries.sampleItinerary.transport.value}}
        </div>
      </vs-summary-box-list-item>
      <vs-summary-box-list-item>
        <strong>Main theme</strong>
        <div class="icon-wrapper">
          <vs-icon :name="itineraries.sampleItinerary.theme.key" variant="dark" size="sm" />
          {{itineraries.sampleItinerary.theme.value}}
        </div>
      </vs-summary-box-list-item>
    </vs-summary-box-list>
  ```
</docs>
