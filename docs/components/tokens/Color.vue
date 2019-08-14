<template>
  <div>
    <div
      v-for="(category, categoryKey) in tokenGroups"
      :key="categoryKey"
      class="color-category"
      :class="['color-category-' + category.name]"
    >
      <h2>{{ category.name }}</h2>

      <ul class="colors">
        <li v-for="(prop, index) in category.colors" :key="index" class="color">
          <div class="swatch" :style="{ backgroundColor: prop.value }">
            <ul class="contrast-indicator__ul">
              <li v-for="(item, index2) in prop.readability" :key="index2">
                <p :class="index2">A</p>
                <p class="result">{{ item }}</p>
              </li>
            </ul>
          </div>
          <h3>{{ prop.name.replace(/_/g, " ").replace(/color/g, "") }}</h3>
          <dl class="docs__dl">
            <dt>SCSS:</dt>
            <dd>${{ prop.name.replace(/_/g, "-") }}</dd>
            <dt>HEX:</dt>
            <dd>{{ prop.originalValue }}</dd>
            <dt>RGB:</dt>
            <dd>{{ prop.value }}</dd>
          </dl>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import designTokens from "@/assets/tokens/tokens.raw.json"

import filter from "lodash/filter"
import groupBy from "lodash/groupBy"
import map from "lodash/map"
import each from "lodash/each"
import get from "lodash/get"
import find from "lodash/find"
import matchesProperty from "lodash/matchesProperty"
import cloneDeep from "lodash/cloneDeep"
import tinycolor from "tinycolor2"

export default {
  name: "Color",
  data() {
    let groups = []
    let colours = filter(designTokens.props, ["type", "color"])

    each(colours, colour => {
      let categoryName = get(colour, "category")
      let category = find(groups, matchesProperty("name", categoryName))
      colour.readability = this.checkContrast(colour.originalValue)

      if (!category) {
        category = {
          name: categoryName,
          colors: [],
        }
        groups.push(category)
      }

      category.colors.push(colour)
    })

    return {
      tokenGroups: groups,
    }
  },
  methods: {
    checkContrast(originalValue) {
      let readability = {}
      readability.blackLG = tinycolor.isReadable("#000000", originalValue, {
        level: "AA",
        size: "large",
      })
        ? "Pass"
        : "Fail"
      readability.blackSM = tinycolor.isReadable("#000000", originalValue, {
        level: "AA",
        size: "small",
      })
        ? "Pass"
        : "Fail"
      readability.whiteLG = tinycolor.isReadable("#ffffff", originalValue, {
        level: "AA",
        size: "large",
      })
        ? "Pass"
        : "Fail"
      readability.whiteSM = tinycolor.isReadable("#ffffff", originalValue, {
        level: "AA",
        size: "small",
      })
        ? "Pass"
        : "Fail"
      return readability
    },
  },
}
</script>

<style lang="scss" scoped>
@import "../../styles/docs.tokens.scss";
@import "../../styles/docs.mixins.scss";

/* STYLES
--------------------------------------------- */

.color-category {
  padding: 16px 0;
  border-bottom: 1px solid $docs-color-cloud;

  &:last-of-type {
    border-bottom: none;
  }
}

.contrast-indicator__ul {
  align-items: flex-end;
  display: flex;
  justify-content: space-evenly;
  list-style-type: none;
  margin: 0 0 12px;
  padding: 0 12px;
  text-align: center;
  width: 100%;

  p {
    margin: 0;
  }

  .result {
    color: $white;
    background-color: $black;
    font-size: 0.75rem;
    padding: 0 5px;
    border-radius: $radius-default;
  }

  .whiteLG {
    color: $white;
    font-size: 18px;
  }

  .whiteSM {
    color: $white;
    font-size: 14px;
  }

  .blackLG {
    color: $black;
    font-size: 18px;
  }

  .blackSM {
    color: $black;
    font-size: 14px;
  }
}

.docs__dl {
  dt,
  dd {
    float: left;
    margin: 0 5px 5px 0;
  }
  dt {
    clear: both;
  }
}

.colors {
  display: flex;
  flex-wrap: wrap;
  list-style-type: none;
  justify-content: flex-start;
  margin: $space-l 0 0;
  padding: 0;
  width: 100%;

  > li {
    width: 250px;
    margin-right: $space-s;
  }
}
.swatch {
  @include stack-space($space-s);
  border-bottom: 1px solid rgba(0, 0, 0, 0.08);
  height: $space-xxl;
  display: flex;
  align-items: flex-end;
  margin-left: -#{$space-s};
  margin-top: -#{$space-s};
  width: calc(100% + #{$space-s} * 2);
}
h3 {
  @include reset;
  @include stack-space($space-xs);
  text-transform: capitalize;
  line-height: 1.2;
  width: 100%;
  float: left;
}
.color {
  @include reset;
  @include inset-space($space-s);
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  margin-bottom: $space-m;
  box-shadow: 0 0 0 1px rgba(63, 63, 68, 0.05), 0 1px 3px 0 rgba(63, 63, 68, 0.15);
  font-size: $size-s;
  font-family: $font-text;
  color: $docs-color-rich-black;
  border-radius: $radius-default;
  overflow: hidden;
}
</style>
<docs>
  ```jsx
  <color/>
  ```
</docs>
