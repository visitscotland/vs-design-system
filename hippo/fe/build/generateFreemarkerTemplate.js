const fs = require("fs")
const path = require("path")

const rf = require("rimraf")
const { each, kebabCase, partial, reduce, trimStart, startsWith } = require("lodash")
const { getOptions } = require("loader-utils")
const validateOptions = require("schema-utils")

const defaultTargetpath = path.resolve(__dirname, "../dist/templates")

const optionsSchema = {
  type: "object",
  properties: {
    targetPath: {
      type: "string",
    },
  },
}

function generateTemplateContent(componentName, component) {
  let content = ""

  content += '<#include "../imports.ftl">\n' + '<#include "../deps/vue.ftl">\n\n'

  content =
    reduce(
      component.styles,
      function(content, path) {
        return content + generateTemplateContentStyle(path)
      },
      content
    ) + "\n"

  content = reduce(
    component.scripts,
    function(content, path) {
      return content + generateTemplateContentScript(path)
    },
    content
  )

  if (startsWith(componentName, "store")) {
    content += generateTemplateContentStoreScript(componentName)
  } else {
    content += generateTemplateContentRegisterScript(componentName)
  }

  return content
}

function generateTemplateContentStoreScript(componentName) {
  let scriptText = "vs.stores." + componentName + " = " + componentName + ".default"

  return generateTemplateContentScript(null, scriptText)
}

function generateTemplateContentRegisterScript(componentName) {
  let scriptText =
    "Vue.component('vs-" + kebabCase(componentName) + "', " + componentName + ".default)"

  return generateTemplateContentScript(null, scriptText)
}

function generateTemplateContentScript(scriptPath, scriptContent) {
  let nodeString = '\t<script type="text/javascript"'

  if (scriptPath) {
    nodeString += ' src="' + generateTemplateContentWebfilePath(scriptPath) + '"'
  }

  nodeString += ">"

  if (scriptContent) {
    nodeString += "\n\t\t" + scriptContent + "\n\t"
  }

  nodeString += "</script>"

  return generateTemplateContentHeadContribution(nodeString)
}

function generateTemplateContentStyle(path) {
  let linkString =
    '\t<link rel="stylesheet" href="' +
    generateTemplateContentWebfilePath(path) +
    '" type="text/css"/>'

  return generateTemplateContentHeadContribution(linkString, "htmlHead")
}

function generateTemplateContentHeadContribution(content, category) {
  let node = ""

  node += '\n<@hst.headContribution category="' + (category || "htmlBodyEnd") + '">\n'
  node += content + "\n"
  node += "</@hst.headContribution>\n"

  return node
}

function generateTemplateContentWebfilePath(path) {
  return "<@hst.webfile  path='design-system" + trimStart(path, "system-components") + "'/>"
}

function outputTemplate(component, componentName, targetPath) {
  let targetFilePath = path.resolve(targetPath, kebabCase(componentName) + ".ftl")

  fs.writeFileSync(targetFilePath, generateTemplateContent(componentName, component))
}

function prepTargetDir(targetPath) {
  if (fs.existsSync(targetPath)) {
    rf.sync(targetPath)
  }

  fs.mkdirSync(targetPath, { recursive: true })
}

module.exports = function(manifest, a, b) {
  const components = JSON.parse(manifest)
  const options = getOptions(this)
  const targetPath = options.targetPath || defaultTargetpath

  validateOptions(optionsSchema, options, "Generate Freemarker Template")

  prepTargetDir(targetPath)

  each(components, partial(outputTemplate, partial.placeholder, partial.placeholder, targetPath))

  return manifest
}
