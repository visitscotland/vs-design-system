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
            :class="displayHorAlignValue"
            display="3"
            :style="{ 'margin-top': displayTextMarginTop }"
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

/**
 * Number of pixels for different height values
 */
const heights = {
  short: 200,
  medium: 500,
  tall: 700,
}

/**
 * Factor for display vertical positioning calculation
 * at different display positions
 */
const displayPositionFactors = {
  top: 0.25,
  middle: 0.5,
  bottom: 0.75,
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

    /**
     * Vertical positioning of display text
     * `top, middle, bottom`
     */
    displayVertPosition: {
      type: String,
      default: "middle",
      validator: value => {
        return value.match(/(top|middle|bottom)/)
      },
    },

    /**
     * Horizontal alignment of display text
     * `left, center, right`
     */
    displayHorAlign: {
      type: String,
      default: "center",
      validator: value => {
        return value.match(/(left|center|right)/)
      },
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
    displayTextMarginTop() {
      return this.heightPixels * get(displayPositionFactors, this.displayVertPosition) - 48 + "px"
    },
    displayHorAlignValue() {
      return "text-" + this.displayHorAlign
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
  <div>
    <vs-hero 
      src="https://cimg.visitscotland.com/cms-images/homepage/rest-be-thankful-homepage?size=lg"
      display-text="src"
      display-text-sub="https://cimg.visitscotland.com/..." 
    />
    <br />
    <vs-hero 
      src="https://cimg.visitscotland.com/cms-images/homepage/rest-be-thankful-homepage?size=lg"
    />
    <br />

    <vs-hero 
      src="https://cimg.visitscotland.com/cms-images/homepage/rest-be-thankful-homepage?size=lg"
      height="tall"
      display-text="Height"
      display-text-sub="tall"
    />
    <br />
    

    <vs-hero 
      src="https://cimg.visitscotland.com/cms-images/homepage/rest-be-thankful-homepage?size=lg"
      height="short"
      display-text="Height"
      display-text-sub="short"
    />
    <br />

    <vs-hero 
      src="https://cimg.visitscotland.com/cms-images/homepage/rest-be-thankful-homepage?size=lg" 
      display-text="Display vertical position"
      display-text-sub="middle (default)"
    />
    <br />

    <vs-hero 
      src="https://cimg.visitscotland.com/cms-images/homepage/rest-be-thankful-homepage?size=lg" 
      display-text="Display vertical position"
      display-text-sub="top"
      display-vert-position="top"
    />
    <br />
  
    <vs-hero 
      src="https://cimg.visitscotland.com/cms-images/homepage/rest-be-thankful-homepage?size=lg" 
      display-text="Display vertical position"
      display-text-sub="bottom"
      display-vert-position="bottom"
    />
    <br />

    <vs-hero 
      src="https://cimg.visitscotland.com/cms-images/homepage/rest-be-thankful-homepage?size=lg" 
      display-text="Display horizontal alignment"
      display-text-sub="left"
      display-hor-align="top"
    />
    <br />

    <vs-hero 
      src="https://cimg.visitscotland.com/cms-images/homepage/rest-be-thankful-homepage?size=lg" 
      display-text="Display horizontal alignment"
      display-text-sub="right"
      display-hor-align="right"
    />
  </div>
  ```
</docs>
