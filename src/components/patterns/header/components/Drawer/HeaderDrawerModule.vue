<script>
import Vue from "vue"
import headerStore from "../../header.store"
import { isFunction, get } from "lodash"

export default {
  name: "HeaderDrawerModule",
  props: {
    moduleName: {
      type: String,
      required: true,
    },
  },
  render() {
    return this.isVisible && this.hasDefaultSlot ? this.$scopedSlots.default() : null
  },
  computed: {
    isVisible() {
      return headerStore.getters["header/drawer/isCurrentModule"](this.moduleName)
    },
    hasDefaultSlot() {
      return isFunction(get(this.$scopedSlots, "default"))
    },
  },
  methods: {
    isBecomingVisible(newVal, oldVal) {
      return (
        newVal && !oldVal && isFunction(get(this.$slots, "default[0].componentInstance.$el.focus"))
      )
    },
  },
  watch: {
    isVisible(newVal, oldVal) {
      if (this.isBecomingVisible(newVal, oldVal)) {
        Vue.nextTick(() => {
          this.$slots.default[0].componentInstance.$el.focus()
        })
      }
    },
  },
}
</script>
