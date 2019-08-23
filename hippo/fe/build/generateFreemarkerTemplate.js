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

function generateTemplateContent(moduleName, mod) {
  let content = ""

  content += '<#include "../imports.ftl">\n' + '<#include "../vue-app-init.ftl">\n\n'

  content =
    reduce(
      mod.styles,
      function(content, path) {
        return content + generateTemplateContentStyle(path)
      },
      content
    ) + "\n"

  content = reduce(
    mod.scripts,
    function(content, path) {
      return content + generateTemplateContentScript(path)
    },
    content
  )

  if (startsWith(moduleName, "store")) {
    content += generateTemplateContentStoreScript(moduleName)
  } else {
    content += generateTemplateContentRegisterScript(moduleName)
  }

  return content
}

function generateTemplateContentStoreScript(moduleName) {
  let scriptText = "vs.stores." + moduleName + " = " + moduleName + ".default"

  return generateTemplateContentScript(null, scriptText)
}

function generateTemplateContentRegisterScript(moduleName) {
  let scriptText = "Vue.component('vs-" + kebabCase(moduleName) + "', " + moduleName + ".default)"

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

function outputTemplate(mod, moduleName, targetPath) {
  let targetFilePath = path.join(
    targetPath,
    moduleSubPath(mod, moduleName),
    moduleFileName(mod, moduleName)
  )

  fs.writeFileSync(targetFilePath, generateTemplateContent(moduleName, mod))
}

function moduleSubPath(mod, moduleName) {
  if (startsWith(moduleName, "store")) {
    return "/stores"
  }

  return "/components"
}

function moduleFileName(mod, moduleName) {
  if (startsWith(moduleName, "store")) {
    moduleName = trimStart(moduleName, "store")
  }
  return kebabCase(moduleName) + ".ftl"
}

function prepTargetDir(targetPath) {
  if (fs.existsSync(targetPath)) {
    rf.sync(targetPath)
  }

  fs.mkdirSync(targetPath, { recursive: true })
  fs.mkdirSync(path.join(targetPath, "components"))
  fs.mkdirSync(path.join(targetPath, "stores"))
}

module.exports = function(manifest, a, b) {
  const modules = JSON.parse(manifest)
  const options = getOptions(this)
  const targetPath = options.targetPath || defaultTargetpath

  validateOptions(optionsSchema, options, "Generate Freemarker Template")

  prepTargetDir(targetPath)

  each(modules, partial(outputTemplate, partial.placeholder, partial.placeholder, targetPath))

  return manifest
}
