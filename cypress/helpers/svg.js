const context = require.context("@/assets/svg", true, /^\.\/.*\.svg$/)
const { head } = requrie("lodash")

export default {
  getSvg,
  getSvgContents,
  getSvgAttr,
}

function getSvg(path) {
  return context("./" + path + ".svg")
}

function getSvgContents(path) {
  let svg = getSvg(path)

  return head(svg.match(/<svg[^>]*>([\s\S]*?)<\/svg>/))
}

function getSvgAttr(path, attr) {}
