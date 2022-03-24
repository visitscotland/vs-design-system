import Vue from 'vue'
import Router from 'vue-router'
import { normalizeURL, decode } from 'ufo'
import { interopDefault } from './utils'
import scrollBehavior from './router.scrollBehavior.js'

<<<<<<< HEAD
const _62c00dd5 = () => interopDefault(import('../pages/GettingStarted.vue' /* webpackChunkName: "pages/GettingStarted" */))
const _6d4f696a = () => interopDefault(import('../pages/index.vue' /* webpackChunkName: "pages/index" */))
=======
const _cb7c8c32 = () => interopDefault(import('../pages/index.vue' /* webpackChunkName: "pages/index" */))
const _cee9cec2 = () => interopDefault(import('../pages/_slug.vue' /* webpackChunkName: "pages/_slug" */))
>>>>>>> d528b0ebf615aaee3e3cd0c677defd6c34d85709

const emptyFn = () => {}

Vue.use(Router)

export const routerOptions = {
  mode: 'history',
  base: '/',
  linkActiveClass: 'nuxt-link-active',
  linkExactActiveClass: 'nuxt-link-exact-active',
  scrollBehavior,

  routes: [{
<<<<<<< HEAD
    path: "/GettingStarted",
    component: _62c00dd5,
    name: "GettingStarted"
  }, {
    path: "/",
    component: _6d4f696a,
    name: "index"
=======
    path: "/",
    component: _cb7c8c32,
    name: "index"
  }, {
    path: "/:slug",
    component: _cee9cec2,
    name: "slug"
>>>>>>> d528b0ebf615aaee3e3cd0c677defd6c34d85709
  }],

  fallback: false
}

export function createRouter (ssrContext, config) {
  const base = (config._app && config._app.basePath) || routerOptions.base
  const router = new Router({ ...routerOptions, base  })

  // TODO: remove in Nuxt 3
  const originalPush = router.push
  router.push = function push (location, onComplete = emptyFn, onAbort) {
    return originalPush.call(this, location, onComplete, onAbort)
  }

  const resolve = router.resolve.bind(router)
  router.resolve = (to, current, append) => {
    if (typeof to === 'string') {
      to = normalizeURL(to)
    }
    return resolve(to, current, append)
  }

  return router
}
