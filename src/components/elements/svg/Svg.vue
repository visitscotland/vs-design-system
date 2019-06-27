<template>
  <svg :fill="fill" :height="height" :width="width">{contents}</svg>
</template>

<script>
const req = require.context("@/assets/", true, /^\.\/.*\.svg$/)

/**
 * SVGs are used to display vector images
 */
export default {
  name: "VsSvg",
  release: "0.0.1",
  status: "prototype",
  props: {
    /**
     * The path of the svg to display, relative to /src/assets
     */
    path: {
      required: true,
    },
    /**
     * The fill color of the SVG.
     */
    fill: {
      type: String,
      default: "black",
    },
    /**
     * The height attributeof the svg
     */
    height: {
      type: [Number, String],
    },
    /**
     * The width attributeof the svg
     */
    width: {
      type: [Number, String],
    },
  },
  computed: {
    svg() {
      // let template = '<svg style="fill: ' + this.fill + '" '

      // if (this.height !== null && this.height !== undefined) {
      //   template += ' height="' + this.height + '" '
      // }

      // if (this.width !== null && this.width !== undefined) {
      //   template += ' width="' + this.width + '" '
      // }

      // return req("./" + this.path + ".svg").replace(/^<svg /, template)
      return req("./" + this.path + ".svg")
    },
  },
}
</script>

<style lang="scss"></style>

<docs>
  ```jsx
  <div>
    <pre>&lt;vs-svg path="svg/logo" /&gt; </pre>
    <vs-svg path="svg/logo" />

    <br /><hr /><br />

    <pre>height="110"</pre>
    <vs-svg path="svg/logo" height="110" />

    <br /><hr /><br />

    <pre>width="110"</pre>
    <vs-svg path="svg/logo" width="110" />

    <br /><hr /><br />

    <pre>fill="red"</pre>
    <vs-svg path="svg/logo" height="110" fill="red" />

    <br /><hr /><br />

    <pre>container="vs-heading"</pre>
    <vs-svg path="svg/logo" height="110" container="vs-heading" />

    <br /><hr /><br />

    <pre>container-class="shadow mb-5 bg-warning rounded"</pre>
    <vs-svg path="svg/logo" height="110" container-class="shadow p-5 mb-5 bg-warning rounded" />
  </div>
  ```
</docs>
