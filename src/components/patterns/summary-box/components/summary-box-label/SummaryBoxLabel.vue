<template>
    <div class="text-center vs-summary-box-label">
        <strong><slot /></strong>
    </div>
</template>

<script>
/**
 * Summary Box Label
 */
export default {
    name: "VsSummaryBoxLabel",
    status: "prototype",
    release: "0.0.1",
    components: {},
    props: {},
}
</script>

<style lang="scss" scoped>
.vs-summary-box-label {
    position: absolute;
    bottom: 5px;
    width: 100%;
    left: 0;

    strong {
        display: block;
        font-size: 0.875rem;
        line-height: 1.125rem;
        @include media-breakpoint-up(lg) {
            font-size: 1.125rem;
        }
    }
}
</style>

<docs>
  
  ```jsx
    <div class="position-relative p-5">
        <vs-summary-box-label>Distance <br /><abbr title="miles">mi</abbr>/<abbr title="kilometres">km</abbr></vs-summary-box-label>
    </div>
  ```
</docs>
