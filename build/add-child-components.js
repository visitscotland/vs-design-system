// const babelTypes = require("@babel/types")
const { includes, partial } = require("lodash")
const { visit } = require("ast-types")
// import cacher from "./utils/cacher"

exports.default = function parseChildComponents(documentation, componentDefinition, astPath, opt) {
  let componentPath = componentDefinition.get("properties").filter(prop => {
    return prop.node.key.name === "components"
  })

  if (!componentPath.length) {
    return
  }

  let childComponentVariableNames = getChildComponentNames(componentPath)

  console.log(childComponentVariableNames)

  let childComponents = getComponentsDetails(astPath, childComponentVariableNames)

  console.log(childComponents)
}

function getChildComponentNames(componentPath) {
  return componentPath[0].get("value", "properties").map(prop => prop.value.value.name)
}

function getComponentsDetails(astPath, childComponentVariableNames) {
  const components = {}

  try {
    visit(astPath, {
      visitImportDeclaration: partial(getComponentDetail, components, childComponentVariableNames),
    })
  } catch (e) {
    console.log(e)
    debugger
  }

  console.log(components)

  return components
}

function getComponentDetail(components, childComponentVariableNames, importAstPath) {
  let variableName = importAstPath.get("specifiers", "0", "local", "name").value

  if (includes(childComponentVariableNames, variableName)) {
    components[variableName] = importAstPath.get("source", "value").value
  }

  return false
}
