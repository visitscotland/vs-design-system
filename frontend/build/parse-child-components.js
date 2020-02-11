const {
  includes,
  extend,
  split,
  head,
  tail,
  join,
  findIndex,
  get,
  capitalize,
  map,
} = require("lodash")
const { visit } = require("ast-types")
const { packageName } = require("./utils.js")

exports.default = function parseChildComponents(
  documentation,
  componentDefinition,
  astPath,
  options
) {
  let componentPath = componentDefinition.get("properties").filter(prop => {
    return prop.node.key.name === "components"
  })

  if (!componentPath.length) {
    return
  }

  let childComponents = getComponentsDetails(
    astPath,
    getChildComponentNames(componentPath[0]),
    options
  )

  documentation.set("childComponents", childComponents)
}

function getLocalPackageName() {
  return packageName || "local"
}

function getChildComponentNames(componentPath) {
  return componentPath.get("value", "properties").map(prop => prop.value.value.name)
}

function getComponentsDetails(astPath, childComponentVariableNames, options) {
  const components = {}

  visit(astPath, {
    visitImportDeclaration(importAstPath) {
      extend(
        components,
        getImportComponentDetail(childComponentVariableNames, importAstPath, options)
      )

      return false
    },
  })

  return components
}

function getImportComponentDetail(childComponentVariableNames, importAstPath, options) {
  let variableName = importAstPath.get("specifiers", "0", "local", "name").value

  if (variableName && includes(childComponentVariableNames, variableName)) {
    return { [variableName]: getComponentImportStatementDetails(importAstPath, options) }
  }

  return false
}

function getComponentImportStatementDetails(importAstPath, options) {
  let importSource = importAstPath.get("source", "value").value
  let importPackageDetails = getImportPackageDetails(importSource, importAstPath, options)

  return extend({}, importPackageDetails, {
    source: importSource,
  })
}

function getImportPackageDetails(importSource, importAstPath, options) {
  let sourceBits = split(importSource, "/")
  let packageName = head(sourceBits)

  if (packageName === "." || packageName === "..") {
    packageName = getLocalPackageName()
  }

  return {
    packageName: packageName,
    relativeSource: packageRelativeSource(sourceBits),
    link: getImportLink(packageName, sourceBits),
  }
}

function getImportLink(packageName, sourceBits) {
  if (packageName === getLocalPackageName()) {
    return packageRelativeSource(sourceBits)
  }

  if (packageName === "bootstrap-vue") {
    let rootUrl = "https://bootstrap-vue.js.org/docs/components/"
    let componentIndex = findIndex(sourceBits, bit => bit === "components")

    if (componentIndex !== -1) {
      return rootUrl + (componentIndex === -1 ? "" : "/" + get(sourceBits, componentIndex + 1))
    }
  }

  return ""
}

function packageRelativeSource(sourceBits) {
  return join(map(head(sourceBits) === ".." ? sourceBits : tail(sourceBits), capitalize), "/")
}
