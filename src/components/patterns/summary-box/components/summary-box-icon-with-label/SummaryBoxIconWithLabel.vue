<template>
    <div
        class="vs-summary-box-icon-with-label d-flex align-items-end position-absolute justify-content-center w-100"
    >
        <div class="icon-wrapper text-right">
            <vs-icon slot="icon" :name="icon" variant="dark" size="md" :padding="0" />
        </div>
        <div class="text-wrapper d-block w-auto mx-1">
            <span>{{ label }}</span>
        </div>
    </div>
</template>

<script>
import VsIcon from "@components/elements/icon/Icon"
/**
 * Summary Box Icon With Label
 */
export default {
    name: "VsSummaryBoxIconWithLabel",
    status: "prototype",
    release: "0.0.1",
    components: { VsIcon },
    props: {
        icon: {
            type: String,
        },
        label: {
            type: String,
        },
    },
}
</script>

<style lang="scss" scoped>
.vs-summary-box-icon-with-label {
    bottom: 35%;

    .icon-wrapper {
        width: auto;
        max-width: 40%;
    }
    .text-wrapper {
        font-size: 0.875rem;
        line-height: 1rem;
        max-width: 50%;
        word-wrap: break-word;
        @include media-breakpoint-up(lg) {
            font-size: 1rem;
            line-height: 1.25rem;
        }
    }
}
</style>
<docs>
  
  ```jsx
    <div class="position-relative mt-5">
        <vs-summary-box-icon-with-label
            :icon=itineraries.sampleItinerary.transport.key
            :label=itineraries.sampleItinerary.transport.value
        />
    </div>
  ```
</docs>
