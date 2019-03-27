/**
 * This file outputs an object for consumption by webpack as its entry config.
 * The object lists components to build and their file paths.
 */

const glob = require("glob")
const path = require("path")
const camelCase = require("lodash/camelCase")
const upperFirst = require("lodash/upperFirst")

const componentPaths = [
  "./src/elements/**/*.vue",
  "./src/patterns/**/*.vue",
  "./src/modules/**/*.vue",
  "./src/templates/**/*.vue",
]

let components = {
  coreStyles: "./src/styles/styles.scss",
}

componentPaths
  .reduce((accumulator, pattern) => {
    return accumulator.concat(glob.sync(pattern))
  }, [])
  .forEach(componentPath => {
    components[upperFirst(camelCase(path.basename(componentPath, ".vue")))] = componentPath
  })

module.exports = components
