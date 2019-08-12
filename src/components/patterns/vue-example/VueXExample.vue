<template>
  <div>
    <b-card bg-variant="primary" style="max-width: 20rem;">
      <b-card-text>
        Inside the component, the count from the VueX state is {{ count }}
      </b-card-text>
      <vs-button
        @click.native="increment('Incrementing count from ' + count + ' to ' + (count + 1))"
      >
        <slot />
      </vs-button>
    </b-card>
  </div>
</template>

<script>
import BCard from "bootstrap-vue/es/components/card/card"
import BCardText from "bootstrap-vue/es/components/card/card-text"
import VsButton from "../../elements/button"
import store from "./example.store"

/**
 * This is an example component to demonstrate how VueX can be used with
 * components of the design system.
 */
export default {
  name: "VsVueXExample",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsButton,
    BCard,
    BCardText,
  },
  store,
  computed: {
    count() {
      return this.$store.state.example.count
    },
  },
  methods: {
    increment(message) {
      this.$store.dispatch("example/increment", message)
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/card";
</style>

<docs>
  ```vue

  <template>
  <div>
    <vs-vue-x-example>Increment count</vs-vue-x-example>
    <div style="margin-top:24px">
      <span>Outside the component, the count is {{ count }}</span>
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
