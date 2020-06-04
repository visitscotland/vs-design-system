/**
 * This file outputs an object for consumption by webpack as its entry config.
 * The object lists components to build and their file paths.
 */

const glob = require("glob")
const path = require("path")
const camelCase = require("lodash/camelCase")
const upperFirst = require("lodash/upperFirst")

const componentPaths = [
    "./src/components/elements/**/*.vue",
    "./src/components/patterns/**/*.vue",
    "./src/components/modules/**/*.vue",
    "./src/components/templates/**/*.vue",
    "./src/components/examples/**/*.vue",
]

const storePattern = "./src/components/**/*.store.js"

const components = {
    core: "./build/core.system-components.js",
}

componentPaths
    .reduce((accumulator, pattern) => accumulator.concat(glob.sync(pattern)), [])
    .forEach((componentPath) => {
        components[upperFirst(camelCase(path.basename(componentPath, ".vue")))] = componentPath
    })

glob.sync(storePattern).forEach((storePath) => {
    components[`store${upperFirst(camelCase(path.basename(storePath, ".js")))}`] = storePath
})

module.exports = components
