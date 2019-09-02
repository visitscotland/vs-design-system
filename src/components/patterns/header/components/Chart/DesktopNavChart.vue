<template>
  <component :is="type" class="vs-desktop-nav-chart__container col-4 col-xl-3 divide-left">
    <span class="vs-desktop-nav-chart__header">{{ chartTitle }}</span>
    <canvas :ref="chartId" width="400" height="300"></canvas>
  </component>
</template>

<script>
import Chart from "chart.js"

export default {
  name: "VsDesktopNavChart",
  status: "prototype",
  release: "0.0.1",
  components: {},
  data() {
    return {}
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
    },
    chartId: {
      type: String,
    },
    labels: {
      type: Array,
    },
    datasets: {
      type: Array,
    },
  },
  methods: {},
  mounted() {
    var ctx = this.$refs[this.chartId].getContext("2d")

    var thisChart = new Chart(ctx, {
      type: "bar",
      data: {
        labels: this.labels,
        datasets: this.datasets,
      },
      options: this.options,
    })
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
    const mainNav = require("../../../../../assets/fixtures/mainNav.json")
    const charts = mainNav[3].chartWidgets
    <vs-row>
      <vs-desktop-nav-chart
        v-for="(chart, index) in charts"
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
