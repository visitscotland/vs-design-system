<template>
    <Component
        data-test="chart-container"
        :is="type"
        class="vs-desktop-nav-chart__container col-4 col-xl-3 divide-left"
    >
        <span
            data-test="chart-header"
            class="vs-desktop-nav-chart__header"
        >{{ chartTitle }}</span>
        <canvas
            data-test="chart-canvas"
            :ref="chartId"
            width="400"
            height="300"
        />
    </Component>
</template>

<script>
import Chart from "chart.js"

export default {
    name: "VsDesktopNavChart",
    status: "prototype",
    release: "0.0.1",
    components: {
    },
    props: {
        /**
         * The html element name used for the component
         */
        type: {
            type: String,
            default: "div",
        },
        chartTitle: {
            type: String,
            default: "",
        },
        chartId: {
            type: String,
            default: "",
        },
        labels: {
            type: Array,
            default() {
                return []
            },
        },
        datasets: {
            type: Array,
            default() {
                return []
            },
        },
    },
    data() {
        return {
        }
    },
    mounted() {
        const ctx = this.$refs[this.chartId].getContext("2d")

        const thisChart = new Chart(ctx, {
            type: "bar",
            data: {
                labels: this.labels,
                datasets: this.datasets,
            },
            options: this.options,
        })
    },
    methods: {
    },
}
</script>

<style lang="scss" scoped>
@import "../../styles/placeholders";

.vs-desktop-nav-chart__container {
  min-width: 300px;
}

.vs-desktop-nav-chart__header {
  font-size: 1.5rem;
  margin-bottom: 1rem;
}

.divide-left {
  @extend %divide-left;
}
</style>

<docs>
  ```jsx

    <vs-row>
      <vs-desktop-nav-chart
        v-for="(chart, index) in header.mainNav[3].chartWidgets"
        :chart-title="chart.chartTitle"
        :labels="chart.labels"
        :datasets="chart.datasets"
        :options="chart.options"
        :chartId="'chartId' + index"
        :key="'chart' + index"
      />
    </vs-row>
  ```
</docs>
