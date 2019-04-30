<template>
  <div class="template-example">
    <section>
      <img
        src="https://cimg.visitscotland.com/cms-images/homepage/rest-be-thankful-homepage?size=md"
      />
    </section>
    <vs-container>
      <vs-row>
        <vs-col cols="9"> <h1>Example title</h1> </vs-col>
        <vs-col cols="3">
          <section><h2>Sub section</h2></section>
        </vs-col>
      </vs-row>
    </vs-container>
  </div>
</template>

<script>
import VsContainer from "../elements/Container"
import VsRow from "../elements/Row"
import VsCol from "../elements/Col"

/**
 * The default page template.
 */
export default {
  name: "VsExampleTemplate",
  status: "prototype",
  release: "0.0.1",
  metaInfo: {
    title: "Template example",
    htmlAttrs: {
      lang: "en",
    },
  },
}
</script>

<style lang="scss" scoped>
.template-example {
  border: 1px dashed #ccc;
}
</style>

<docs>
  ```jsx
  <VsExampleTemplate />
  ```
</docs>
