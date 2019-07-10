<template>
  <component
    :is="type"
    class="hero"
    :style="{
      backgroundImage: bgdImgStyleValue,
      backgroundPositionY: imageAlignV,
      height: heightPixels + 'px',
    }"
  >
    <vs-container>
      <vs-row :align-v="displayAlignVValue">
        <vs-col>
          <vs-heading
            v-if="displayText"
            :class="'text-' + displayAlignH"
            display="3"
            :sub="displayTextSub"
            >{{ displayText }}
          </vs-heading>
        </vs-col>
      </vs-row>
    </vs-container>
  </component>
</template>

<script>
import VsHeading from "../elements/heading"
import { VsContainer, VsRow, VsCol } from "../elements/layout"
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
const displayAlignVLookup = {
  top: "start",
  middle: "center",
  bottom: "end",
}

/**
 * Hero image element
 */
export default {
  name: "VsHero",
  status: "prototype",
  release: "0.0.1",
  components: { VsHeading, VsContainer, VsRow, VsCol },
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
    imageSrc: {
      type: String,
    },

    /**
     * The hero image background position style value
     *
     * Valid values according to background-position style
     */
    imageAlignV: {
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
     * Vertical alignment of display text
     * `top, middle, bottom`
     */
    displayAlignV: {
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
    displayAlignH: {
      type: String,
      default: "center",
      validator: value => {
        return value.match(/(left|center|right)/)
      },
    },
  },
  computed: {
    bgdImgStyleValue() {
      if (!this.imageSrc) {
        return "none"
      }

      return "url('" + this.imageSrc + "')"
    },
    bgdPosStyleValue() {},
    heightPixels() {
      return get(heights, this.height)
    },
    displayAlignVValue() {
      return get(displayAlignVLookup, this.displayAlignV)
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/text";
@import "~bootstrap/scss/utilities/flex";

.hero {
  background-size: cover;
}

.container,
.row {
  height: 100%;
}

/* TODO: fix deep selectors not working with dart sass */
.hero ::v-deep .heading,
.hero ::v-deep .heading .sub-heading {
  color: $color-single-malt-gold;
}
.hero ::v-deep .heading .sub-heading {
  color: $color-white;
}
</style>

<docs>
  ```jsx
  <div>
    <vs-hero 
      image-src="https://cimg.visitscotland.com/cms-images/homepage/rest-be-thankful-homepage?size=lg"
      display-text="src"
      display-text-sub="https://cimg.visitscotland.com/..." 
    />
    <br />
    
    <vs-hero 
      image-src="https://cimg.visitscotland.com/cms-images/homepage/rest-be-thankful-homepage?size=lg"
    />
    <br />

    <vs-hero 
      image-src="https://cimg.visitscotland.com/cms-images/homepage/rest-be-thankful-homepage?size=lg"
      height="tall"
      display-text="Height"
      display-text-sub="tall"
    />
    <br />
    
    <vs-hero 
      image-src="https://cimg.visitscotland.com/cms-images/homepage/rest-be-thankful-homepage?size=lg"
      height="short"
      display-text="Height"
      display-text-sub="short"
    />
    <br />

    <vs-hero 
      image-src="https://cimg.visitscotland.com/cms-images/homepage/rest-be-thankful-homepage?size=lg" 
      display-text="Display vertical position"
      display-text-sub="middle (default)"
    />
    <br />

    <vs-hero 
      image-src="https://cimg.visitscotland.com/cms-images/homepage/rest-be-thankful-homepage?size=lg" 
      display-text="Display vertical position"
      display-text-sub="top"
      display-align-v="top"
    />
    <br />
  
    <vs-hero 
      image-src="https://cimg.visitscotland.com/cms-images/homepage/rest-be-thankful-homepage?size=lg" 
      display-text="Display vertical position"
      display-text-sub="bottom"
      display-align-v="bottom"
    />
    <br />

    <vs-hero 
      image-src="https://cimg.visitscotland.com/cms-images/homepage/rest-be-thankful-homepage?size=lg" 
      display-text="Display horizontal alignment"
      display-text-sub="left"
      display-align-h="left"
    />
    <br />

    <vs-hero 
      image-src="https://cimg.visitscotland.com/cms-images/homepage/rest-be-thankful-homepage?size=lg" 
      display-text="Display horizontal alignment"
      display-text-sub="right"
      display-align-h="right"
    />
    <br />

    <vs-hero 
      image-src="https://cimg.visitscotland.com/cms-images/homepage/rest-be-thankful-homepage?size=lg" 
      display-text="Image vertical position"
      display-text-sub="20%"
      image-align-v="20%"
    />
  </div>
  ```
</docs>
