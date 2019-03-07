#! /usr/bin/env node

const contentful = require("contentful")
const _ = require("lodash")
const fs = require("fs")
const documentToHtmlString = require("@contentful/rich-text-html-renderer").documentToHtmlString
const turndownService = new require("turndown")()

const client = contentful.createClient({
  // This is the space ID. A space is like a project folder in Contentful terms
  space: "xf5qyv95yk3c",
  // This is the access token for this space. Normally you get both ID and the token in the Contentful web app
  accessToken: "73c1a3252bd526262639b627c11271fc6d561848991ef76ce37453431eeda6f6",
})

addTurnDownRules(turndownService)

module.exports = function(srcConfig, tempOutputPath) {
  prepOutputDir(tempOutputPath)

  return client
    .getEntries({ content_type: "page" })
    .then(data => {
      const rawContentfulPages = _.get(data, "items", [])
      const contentfulSectionsConfig = extractSectionsConfig(rawContentfulPages, tempOutputPath)
      return mergeSectionsConfig(srcConfig, contentfulSectionsConfig)

      //vue-styleguidist build --config ./config/docs.config.js
      //vue-styleguidist server --open --config ./config/docs.config.js
    })
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

function extractSectionsConfig(rawContentfulPages, tempOutputPath) {
  return _.map(rawContentfulPages, _.partial(parseContentfulPage, tempOutputPath))
}

function parseContentfulPage(tempOutputPath, page) {
  const pageName = _.get(page, "fields.name")
  const outputPath = tempOutputPath + _.kebabCase(pageName) + ".md"
  const markDownContent = generateSectionMarkdown(page)

  fs.writeFileSync(outputPath, markDownContent, "utf8")

  return _.merge({}, _.get(page, "fields", {}), {
    name: pageName,
    content: "../" + outputPath,
  })

  // return {

  //     description: _.get(page, 'fields.description'),
  //     sectionDepth: _.get(page, 'fields.sectionDepth'),
  //     exampleMode: _.get(page, 'fields.exampleMode'),
  //     usageMode: _.get(page, 'fields.usageMode'),
  //     usageMode: _.get(page, 'fields.usageMode'),
  //     usageMode: _.get(page, 'fields.usageMode'),
  //     usageMode: _.get(page, 'fields.usageMode'),
  //     usageMode: _.get(page, 'fields.usageMode'),
  // };
}

function generateSectionMarkdown(page) {
  const htmlContent = documentToHtmlString(_.get(page, "fields.content"))

  return turndownService.turndown(htmlContent)
}

function prepOutputDir(tempOutputPath) {
  if (!fs.existsSync(tempOutputPath)) {
    fs.mkdirSync(tempOutputPath, { recursive: true })
  }
}

function mergeSectionsConfig(srcConfig, contentfulSections) {
  return _.set(srcConfig, "sections", contentfulSections)
}
