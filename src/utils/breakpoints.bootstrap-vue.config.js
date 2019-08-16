import Vue from "vue"
import { BVConfigPlugin } from "bootstrap-vue"

const breakpoints = ["xs", "sm", "md", "lg", "xl", "xxl"]

Vue.use(BVConfigPlugin, {
  breakpoints: breakpoints,
})

export default breakpoints
