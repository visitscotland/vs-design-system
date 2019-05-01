<template>
  <component
    :is="type"
    class="hero"
    :style="{
      backgroundImage: backgroundImgStyleValue,
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
    imageSrc: {
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
    backgroundImgStyleValue() {
      if (!this.imageSrc) {
        return "none"
      }

      return "url('" + this.imageSrc + "')"
    },
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
.hero {
  background-size: cover;
}

.container,
.row {
  height: 100%;
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
      display-align-h="top"
    />
    <br />

    <vs-hero 
      image-src="https://cimg.visitscotland.com/cms-images/homepage/rest-be-thankful-homepage?size=lg" 
      display-text="Display horizontal alignment"
      display-text-sub="right"
      display-align-h="right"
    />
  </div>
  ```
</docs>
