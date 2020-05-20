const fs = require("fs")
const path = require("path")

const rf = require("rimraf")
const { each, kebabCase, partial, reduce, trimStart, startsWith, isEmpty } = require("lodash")
const { getOptions } = require("loader-utils")
const validateOptions = require("schema-utils")

const optionsSchema = {
  type: "object",
  properties: {
    targetPath: {
      type: "string",
    },
    importsPath: {
      type: "string",
    }
  },
}

function generateTemplateContent(moduleName, mod, importsPath) {
  let content = ""

  content += '<#include "' + importsPath + '">\n' + '<#include "../vue-app-init.ftl">\n\n'

  if(!isEmpty(mod.styles)) {
    content += generateTemplateContentStyles(mod.styles)
  }

  if(!isEmpty(mod.scripts)) {
    content += generateTemplateContentScripts(mod.scripts)
  }
  
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
  let scriptText = `Vue.component('vs-${kebabCase(moduleName)}', ${moduleName}.default)`

  return generateTemplateContentScript(null, scriptText)
}

function generateTemplateContentScripts(scripts) {
  return reduce(
    scripts,
    function(content, path) {
      return content +
        generateTemplateContentPreload(path, "script") +
        generateTemplateContentScript(path)
    },
    ""
  );
}

function generateTemplateContentPreload(path, type) {
  let linkString = `\t<link rel="preload" href="${
    generateTemplateContentWebfilePath(path)
  }" type="${type}"/>`

  return generateTemplateContentHeadContribution(linkString, "htmlHeadPreload")
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

function generateTemplateContentStyles(styles) {
  return reduce(
    styles,
    function(content, path) {
      return content +
        generateTemplateContentPreload(path, "style") +
        generateTemplateContentStyle(path)
    },
    ""
  ) + "\n"
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
  return "<@hst.webfile  path='design-system/" + path + "'/>"
}

function outputTemplate(mod, moduleName, targetPath, importsPath) {
  let targetFilePath = path.join(
    targetPath,
    moduleSubPath(mod, moduleName),
    moduleFileName(mod, moduleName)
  )

  fs.writeFileSync(targetFilePath, generateTemplateContent(moduleName, mod, importsPath))
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

/**
 * Outputs a freemarker template for each module listed in the provided manifest
 * 
 * manifest is a JSON string encoding an object with module names as keys and asset
 * maps as values. Each asset map has `scripts`, `styles` and `other` keys, which are 
 * arrays of paths to the asset chunks that encode the module and all its dependencies.
 * 
 * This module generates a freemarker for each module that contains
 * 
 * - htmlBodyEnd headContributions for each script asset chunk
 * - htmlHead headContributions for each style asset chunk
 * - preload references in htmlHeadPreload headContributions for all script and style chunks
 * - includes for the vue-app-init.ftl and imports.ftl freemarker templates
 * - a htmlBodyEnd headContribution containing a script tag containing the component 
 *   registraion (for Vue components) or global reference setup (for Vuex stores)
 * 
 * @param {String} manifest
 * 
 */
module.exports = function(manifest) {
  const modules = JSON.parse(manifest)
  const options = getOptions(this)

  validateOptions(optionsSchema, options, "Generate Freemarker Template")

  prepTargetDir(options.targetPath)

  each(modules, partial(outputTemplate, partial.placeholder, partial.placeholder, options.targetPath, options.importsPath))

  return manifest
}
