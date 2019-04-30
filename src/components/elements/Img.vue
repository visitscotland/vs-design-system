<template>
  <b-img :src="src" :alt="alt" :fluid="fluid" :fluid-grow="fluidGrow"> <slot /> </b-img>
</template>

<script>
import BImg from "bootstrap-vue/es/components/image/img"
/**
 * This image component is used to render images in the site. It utilises
 * <a href="https://bootstrap-vue.js.org/docs/components/image">the Bootstrap
 * Vue b-img component</a>.
 */

export default {
  name: "VsImg",
  status: "prototype",
  release: "0.0.1",
  components: {
    BImg,
  },
  props: {
    /**
     * The source URL
     */
    src: {
      type: String,
      required: true,
    },

    /**
     * The alt text for accessibility
     */
    alt: {
      type: String,
      required: true,
    },
    fluid: {
      type: Boolean,
    },
    fluidGrow: {
      type: Boolean,
    },
  },
}
</script>

<style lang="scss" scoped></style>

<docs>
  ```jsx

  <div>
    <vs-container>
      <vs-row>
        <vs-col>
          <vs-img 
            src="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
            alt="Claire standing stones"

          />
        </vs-col>
      </vs-row>
    </vs-container>
    <br />
    <vs-img 
      src="https://cimg.visitscotland.com/cms-images/homepage/new/loch-maree-kinlochewe?size=sm"
      alt="Loch Maree"
    />
  </div>


  
  ```
</docs>
