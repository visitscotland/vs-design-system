<template>
  <svg :is="container" v-bind="attributes">{{ children }}</svg>
</template>

<script>
import svgContext from "@/utils/svg-context"
import { first, split, map, partial, zipObject, fromPairs, replace, mapValues } from "lodash"

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
      return svgContext("./" + this.path + ".svg")
    },
    nativeAttrs() {
      let tag = first(this.svg.match(/<svg[^>]+.*?>/))

      let attributes = tag.match(/(\S+)=["']?((?:.(?!["']?\s+(?:\S+)=|[>"']))+.)["']?/g)
      let attributesMap = fromPairs(map(attributes, partial(split, partial.placeholder, "=", 2)))

      return mapValues(attributesMap, partial(replace, partial.placeholder, /"/g, ""))
    },
    attributes() {},
    children() {},
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
