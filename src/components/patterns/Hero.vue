<template>
  <component
    :is="type"
    class="hero"
    :style="{
      backgroundImage: backgroundImgStyleValue,
      height: height,
    }"
  >
    <vs-container>
      <vs-row>
        <vs-col> <h1>We love Scotland</h1> </vs-col>
      </vs-row>
    </vs-container>
  </component>
</template>

<script>
/**
 * Hero image element
 */
export default {
  name: "VsHero",
  status: "prototype",
  release: "0.0.1",
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

    height: {
      type: String,
      default: "500px",
    },
  },
  computed: {
    backgroundImgStyleValue() {
      if (!this.src) {
        return "none"
      }

      return "url('" + this.src + "')"
    },
  },
}
</script>

<style lang="scss" scoped>
.hero {
  background-size: cover;
}
</style>

<docs>
  ```jsx
  <vs-hero src="https://cimg.visitscotland.com/cms-images/homepage/rest-be-thankful-homepage?size=lg" />
  ```
</docs>
