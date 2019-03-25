"use strict"
const utils = require("./utils")

module.exports = {
  entry: require("../src/system-components"),
  output: {
    filename: utils.assetsSystemPath("components/[name]/component.js"),
  },
}
