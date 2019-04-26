<template>
  <component :is="container" v-html="svg" :class="containerClass" />
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
     * The html element used to contain the svg.
     */
    container: {
      type: String,
      default: "div",
    },
    /**
     * The class string to apply to the container
     */
    containerClass: {
      type: String,
    },
    /**
     * The height attributeof the svg
     */
    height: {
      type: Number,
    },
    /**
     * The width attributeof the svg
     */
    width: {
      type: Number,
    },
  },
  computed: {
    svg() {
      let template = '<svg style="fill: ' + this.fill + '" '

      if (this.height !== null && this.height !== undefined) {
        template += ' height="' + this.height + '" '
      }

      if (this.width !== null && this.width !== undefined) {
        template += ' width="' + this.width + '" '
      }

      return req("./" + this.path + ".svg").replace(/^<svg /, template)
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

    <hr />

    <pre>height="110"</pre>
    <vs-svg path="svg/logo" height="110" />

    <hr />

    <pre>width="110"</pre>
    <vs-svg path="svg/logo" width="110" />

    <hr />

    <pre>fill="red"</pre>
    <vs-svg path="svg/logo" height="110" fill="red" />

    <hr />

    <pre>container="heading"</pre>
    <vs-svg path="svg/logo" height="110" container="heading" />

    <hr />

    <pre>container-class="shadow p-5 mb-5 bg-warning rounded"</pre>
    <vs-svg path="svg/logo" height="110" container-class="shadow p-5 mb-5 bg-warning rounded" />
  </div>
  ```
</docs>
