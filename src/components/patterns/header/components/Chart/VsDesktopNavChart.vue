<template>
  <component :is="type" class="vs-desktop-nav-chart col-4 col-xl-3">
    <span class="vs-desktop-nav-chart__header">{{ chartTitle }}</span>
    <canvas ref="chart" width="400" height="400"></canvas>
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
    labels: {
      type: Array,
    },
    datasets: {
      type: Array,
    },
  },
  methods: {},
  mounted() {
    const ctx = this.$refs.chart

    console.log(this.labels)

    const thisChart = new Chart(ctx, {
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
.vs-desktop-nav-chart__header {
  font-size: 1.5rem;
  margin-bottom: 1rem;
}
</style>

<docs>
  ```jsx
    const mainNav = require("../../../../../assets/fixtures/mainNav.json")
    const charts = mainNav[3].chartWidgets
    <div style="height: 400px; width: 400px;">
      <vs-desktop-nav-chart
        v-for="(chart, index) in charts" :key="index"
        :chart-title="chart.chartTitle"
        :labels="chart.labels"
        :datasets="chart.datasets"
        :options="chart.options"
      />
    </div>
  ```
</docs>
