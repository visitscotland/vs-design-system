<template>
    <div>
        <BCard
            bg-variant="primary"
            style="max-width: 20rem;"
        >
            <BCardText>
                Inside component 1, the count from the VueX state is {{ count }}
            </BCardText>
            <VsButton @click.native="increment">
                <slot />
            </VsButton>
        </BCard>
    </div>
</template>

<script>
import mixin from "./example.mixin"

/**
 * This is an example component to demonstrate how VueX can be used with
 * components of the design system.
 */
export default {
    name: "VsVueXExample",
    mixins: [mixin],
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/card";
</style>

<docs>
  ```vue

  <template>
  <div>
  <vs-row>
      <vs-col tag="vs-vue-x-example" cols="3">Increment count</vs-col>
      <vs-col tag="vs-vue-x-example-2" cols="3">Increment count</vs-col>
  </vs-row>
    <div style="margin-top:24px">
      <span>Outside the components, the count is {{ count }}</span>
    </div>
  </div>
  </template>

  <script>
  import store from './example.store'

  export default {
    store,
    computed: {
      count() {
        return this.$store.state.example.count
      }
    }
  }
  </script>


  ```
</docs>
