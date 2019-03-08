#! /usr/bin/env node

const contentful = require("contentful")
const _ = require("lodash")
const fs = require("fs")
const documentToHtmlString = require("@contentful/rich-text-html-renderer").documentToHtmlString
const turndownService = new require("turndown")()
const contentfulConfig = require("../config/contentful.config.js")

const client = contentful.createClient({
  // This is the space ID. A space is like a project folder in Contentful terms
  space: contentfulConfig.space,
  // This is the access token for this space. Normally you get both ID and the token in the Contentful web app
  accessToken: contentfulConfig.accessToken,
})

addTurnDownRules(turndownService)

module.exports = function(srcConfig, options) {
  const tempOutputPath = _.get(options, "tempOutputPath")

  if (!tempOutputPath) {
  }

  prepOutputDir(tempOutputPath)

  return client
    .getEntry(contentfulConfig.instance)
    .then(_.partial(mergeConfig, _, srcConfig, options))
    .catch(console.error)
}

function addTurnDownRules(turndownService) {
  turndownService.addRule("transform-code", {
    filter: ["code"],
    replacement: function(content) {
      return "```\n" + content + "\n```"
    },
  })
}

function mergeConfig(instance, srcConfig, options) {
  const contentfulInstance = _.get(instance, "fields.sections", [])
  const contentfulConfig = extractContentfulConfig(instance, _.get(options, "tempOutputPath"))
  console.log(instance)
  console.log(contentfulConfig)

  return mergeContentfulConfig(srcConfig, contentfulConfig)
}

function extractContentfulConfig(contentfulInstance, tempOutputPath) {
  return {
    title: getContentfulFieldValue(contentfulInstance, "title"),
    sections: extractContentfulInstanceSections(contentfulInstance, tempOutputPath),
  }
}

function getContentfulFieldValue(contentfulEntry, fieldPath) {
  return _.get(contentfulEntry, "fields." + fieldPath)
}

function extractContentfulInstanceSections(contentfulInstance, tempOutputPath) {
  let sections = _.map(
    getContentfulFieldValue(contentfulInstance, "sections"),
    _.partial(parseContentfulSection, tempOutputPath)
  )

  sections.push({
    name: "Private Components",
    exampleMode: "hide",
    usageMode: "hide",
    components: "../src/**/[_]*.vue",
  })

  return sections
}

function parseContentfulSection(tempOutputPath, section) {
  const outputPath = tempOutputPath + _.kebabCase(getContentfulFieldValue(section, "name")) + ".md"
  const markDownContent = generateSectionMarkdown(section)

  fs.writeFileSync(outputPath, markDownContent, "utf8")

  return _.merge({}, _.get(section, "fields", {}), {
    content: "../" + outputPath,
  })
}

function generateSectionMarkdown(section) {
  const htmlContent = documentToHtmlString(_.get(section, "fields.content"))

  return turndownService.turndown(htmlContent)
}

function prepOutputDir(tempOutputPath) {
  if (!fs.existsSync(tempOutputPath)) {
    fs.mkdirSync(tempOutputPath, { recursive: true })
  }
}

function mergeContentfulConfig(srcConfig, contentfulConfig) {
  return _.assign({}, srcConfig, contentfulConfig)
}
