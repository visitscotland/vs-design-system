<template>
  <component
    :is="type"
    class="hero"
    :style="{
      backgroundImage: backgroundImgStyleValue,
      height: heightValue,
    }"
  >
    <vs-container>
      <vs-row>
        <vs-col>
          <vs-heading
            v-if="displayText"
            display="3"
            :style="{ 'margin-top': displayTextY }"
            :sub="displayTextSub"
            >{{ displayText }}</vs-heading
          >
        </vs-col>
      </vs-row>
    </vs-container>
  </component>
</template>

<script>
import VsHeading from "../elements/Heading"
import { get } from "lodash"

const heights = {
  short: "200",
  medium: "500",
  tall: "700",
}

/**
 * Hero image element
 */
export default {
  name: "VsHero",
  status: "prototype",
  release: "0.0.1",
  components: { VsHeading },
  props: {
    /**
     * The html element name used for the component
     */
    type: {
      type: String,
      default: "section",
    },

    /**
     * The source URL for the hero's image
     */
    src: {
      type: String,
    },

    /**
     * Height class of this hero
     * `short, medium, tall`
     */
    height: {
      type: String,
      default: "medium",
      validator: value => {
        return value.match(/(short|medium|tall)/)
      },
    },

    /**
     * Display text
     */
    displayText: {
      type: String,
    },

    /**
     * Display sub text
     */
    displayTextSub: {
      type: String,
    },
  },
  computed: {
    backgroundImgStyleValue() {
      if (!this.src) {
        return "none"
      }

      return "url('" + this.src + "')"
    },
    heightPixels() {
      return get(heights, this.height)
    },
    heightValue() {
      return this.heightPixels + "px"
    },
    displayTextY() {
      return this.heightPixels / 2 - 48 + "px"
    },
  },
}
</script>

<style lang="scss" scoped>
.hero {
  background-size: cover;
}

/deep/ .heading,
.heading .sub-heading {
  color: $color-gorse-yellow;
}
/deep/ .heading .sub-heading {
  color: $color-white;
}
</style>

<docs>
  ```jsx
  <vs-hero 
    src="https://cimg.visitscotland.com/cms-images/homepage/rest-be-thankful-homepage?size=lg" 
    display-text="We love Scotland"
    display-text-sub="And so will you"
   />
  ```
</docs>
