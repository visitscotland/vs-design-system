<template>
    <div
        class="vs-summary-box-icon-with-label d-flex align-items-end position-absolute justify-content-center w-100"
    >
        <div class="icon-wrapper text-right">
            <slot name="icon" />
        </div>
        <div class="text-wrapper">
            <slot name="label" />
        </div>
    </div>
</template>

<script>
/**
 * Summary Box Icon With Label
 */
export default {
    name: "VsSummaryBoxIconWithLabel",
    status: "prototype",
    release: "0.0.1",
    components: {},
    props: {},
}
</script>

<style lang="scss" scoped>
.vs-summary-box-icon-with-label ::v-deep {
    bottom: 35%;
    p,
    span {
        font-size: 0.875rem;
        line-height: 1rem;
        display: block;
        @include media-breakpoint-up(lg) {
            font-size: 1rem;
            line-height: 1.25rem;
        }
    }
    .icon-wrapper {
        width: auto;
        max-width: 40%;
    }
    .text-wrapper {
        hyphens: auto;
        margin: 0 5px;
        max-width: 50%;
        overflow-wrap: break-word;
        width: auto;
        word-wrap: break-word;
    }
}
</style>
<docs>
  
  ```jsx
    <vs-summary-box-icon-with-label>
        <vs-icon slot="icon" :name="itineraries.sampleItinerary.transport.key" variant="dark" size="md" padding="0" />
        <span slot="label">{{itineraries.sampleItinerary.transport.value}}</span>
    </vs-summary-box-icon-with-label>
  ```
</docs>
