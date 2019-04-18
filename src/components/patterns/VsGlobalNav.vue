<template>
  <component :is="type" class="nav global-nav d-none d-md-block">
    <component :is="wrapperType" :class="wrapperClass">
      <component :is="labelType" v-html="labelCopy" />

      <ul :class="listClass">
        <li v-for="(item, index) in navItems" :key="index">
          <a
            :href="item.href"
            target="_blank"
            :class="{ active: item.active }"
            v-html="item.title"
          />
        </li>
      </ul>
    </component>
  </component>
</template>

<script>
import get from "lodash/get"

/**
 * Global navigation across all VS sites
 */
export default {
  name: "VsGlobalNav",
  status: "prototype",
  release: "0.0.1",
  props: {
    /**
     * The html element name used for the nav bar.
     */
    type: {
      type: String,
      default: "nav",
    },
    /**
     * The html element name used for the label.
     */
    labelType: {
      type: String,
      default: "h6",
    },
    /**
     * The html element name used for the wrapper.
     */
    wrapperType: {
      type: String,
      default: "div",
    },
    /**
     * Label copy.
     */
    labelCopy: {
      type: String,
      default: "Other Sites",
    },
    /**
     * The class of the link list parent element
     */
    listClass: {
      type: String,
      default: "links-list",
    },
    /**
     * The class of the wrapper element
     */
    wrapperClass: {
      type: String,
      default: "container",
    },
    /**
     * Menu items to be displayed on the nav bar.
     */
    navItems: {
      required: true,
      type: Array,
    },
  },
}
</script>

<style lang="scss" scoped>
.global-nav {
  // @extend %invert-color;

  background: $color_thistle_purple;

  h6,
  a {
    display: inline-block;
    margin: 0;
    text-transform: uppercase;
    margin-right: 0.66em;
    line-height: 2.3;
    font-size: $font-size-xxs;
    font-weight: $font-weight-normal;
  }

  h6 {
    opacity: 0.4;
  }

  .links-list {
    display: inline;
    padding: 0;

    li {
      display: inline;

      a {
        opacity: 0.6;

        &.active {
          opacity: 1;
          background: $color_white;
          color: $color_thistle_purple;
          padding: 0 vs-padding(sm);
        }

        &:hover,
        &:focus {
          opacity: 1;
        }
      }
    }
  }
}
</style>

<docs>
  ```jsx
  <vs-global-nav :navItems="[
    {title: 'Holiday in Scotland', href: 'https://www.visitscotland.com'},
    {title: 'Corporate', active: true, href: 'https://www.visitscotland.org'},
    {title: 'Travel Trade', href: 'https://traveltrade.visitscotland.org/'},
    {title: 'Business Events', href: 'http://www.conventionscotland.com/'},
    {title: 'Media', href: 'http://mediacentre.visitscotland.org/'}
  ]"/>
  ```
</docs>
