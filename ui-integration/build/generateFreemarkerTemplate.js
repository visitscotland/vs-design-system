const fs = require("fs")
const path = require("path")

const rf = require("rimraf")
const { 
  each,
  kebabCase,
  partial,
  reduce,
  trimStart,
  startsWith,
  isEmpty,
  includes,
  last,
} = require("lodash")
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
    },
  },
}

const mainClientModuleName = "main-client"

const coreModules = [
  "core",
  mainClientModuleName,
]

function moduleIsCore(moduleName) {
  return includes(coreModules, moduleName)
}

function moduleIsStore(moduleName) {
  return startsWith(moduleName, "store")
}

function moduleIsMainClientEntry(moduleName) {
  return moduleName === mainClientModuleName
}

function generateVueAppInitInclude() {
  return '<#include "../vue-app-init.ftl">\n'
}

function generateImportsInclude(importsPath) {
  return '<#include "' + importsPath + '">\n'
}

function generateTemplateContent(moduleName, mod, importsPath) {
  let content = ""
  const isCore = moduleIsCore(moduleName)
  const isStore = moduleIsStore(moduleName)
  const isClientEntry = moduleIsMainClientEntry(moduleName)

  if(!isCore) {
    content += generateVueAppInitInclude()
    content += generateImportsInclude(importsPath)
    content += "\n"
  }

  if(!isEmpty(mod.styles)) {
    content += generateTemplateContentStyles(mod.styles)
  }

  if(!isEmpty(mod.scripts)) {
    content += generateTemplateContentAssets(mod.scripts, isClientEntry)
  }
  
  if (isStore) {
    content += generateTemplateContentStoreScript(moduleName)
  } else if(!isCore) {
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

function generateTemplateContentAssets(scripts, isClientEntry) {
  const isClientEntryLast = isClientEntry ? last(scripts) : null

  function isAppEntry(path) {
    return isClientEntryLast && isClientEntryLast === path
  }

  return reduce(
    scripts,
    function(content, path) {
      let altHeadContributionName = isAppEntry(path) ? "htmlBodyEndAppEntry" : null

      return content + generateTemplateContentPreload(path, "script") +
          generateTemplateContentScript(path, null, altHeadContributionName)
    },
    "",
  );
}

function generateTemplateContentPreload(path, type) {
  let linkString = generateTemplateContentLinkTag("preload", path, type)

  return generateTemplateContentHeadContribution(linkString, "htmlHeadPreload")
}

function generateTemplateContentLinkTag(rel, path, type) {
  return `\t<link rel="${rel}" href="${generateTemplateContentWebfilePath(path)}" type="${type}"/>`
}

function generateTemplateContentScript(scriptPath, scriptContent, headContributionName) {
  let nodeString = '\t<script type="text/javascript"'

  if (scriptPath) {
    nodeString += ' src="' + generateTemplateContentWebfilePath(scriptPath) + '"'
  }

  nodeString += ">"

  if (scriptContent) {
    nodeString += "\n\t\t" + scriptContent + "\n\t"
  }

  nodeString += "</script>"

  if(!headContributionName) {
    headContributionName = "htmlBodyEndScripts"
  }

  return generateTemplateContentHeadContribution(nodeString, headContributionName)
}

function generateTemplateContentStyles(styles) {
  return reduce(
    styles,
    function(content, path) {
      return content +
        generateTemplateContentPreload(path, "style") +
        generateTemplateContentStyle(path)
    },
    "",
  ) + "\n"
}

function generateTemplateContentStyle(path) {
  let linkString = generateTemplateContentLinkTag("stylesheet", path, "text/css")

  return generateTemplateContentHeadContribution(linkString, "htmlHeadStyles")
}

function generateTemplateContentHeadContribution(content, category) {
  let node = ""

  node += '\n<@hst.headContribution category="' + category + '">\n'
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
  if (moduleIsStore(moduleName)) {
    return "/stores"
  } else if(moduleIsCore(moduleName)) {
    return "/"
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
 * - htmlBodyEndScripts headContributions for each script asset chunk
 * - htmlHeadStyle headContributions for each style asset chunk
 * - preload references in htmlHeadPreload headContributions for all script and style chunks
 * - includes for the vue-app-init.ftl and imports.ftl freemarker templates
 * - htmlBodyEndScripts headContributions containing a script tag with the component 
 *   registraion or global reference setup for each Vue component or Vuex store, respectively
 * - a htmlBodyEndAppEntry headContribution for the final chunk for the main client entry
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
