#! /usr/bin/env node
const _ = require("lodash")
const fs = require("fs")
const requestPromise = require("request-promise-native")
const Turndown = require("turndown")
const ora = require("ora")
const rm = require("rimraf")
const path = require("path")

const StyleguidistError = require("react-styleguidist/scripts/utils/error")

const defaultRemoteConfig = {
  tempPath: "./.tmp/",
}

const turndownService = _getTurndownService()

function _makeRemoteUriFromRemoteConfig(config) {
  const uri = _.get(config, "uriBase")
  let queryString = null

  if (!uri) {
    throw new StyleguidistError("Remote config is missing uriBase")
  }

  queryString = _.join(
    _.map(_.get(config, "uriParams"), (value, key) => {
      return key + "=" + value
    }),
    "&"
  )

  return uri + (queryString ? "?" + queryString : "")
}

function _applyTransform(apiResponse, transform) {
  if (_.isFunction(transform)) {
    return transform(apiResponse)
  }

  let func = _.get(transform, "func")
  let args = _.concat([apiResponse], _.get(transform, "args", []))

  if (!_.isFunction(func)) {
    return apiResponse
  }

  return func.apply(null, args)
}

function _mergeConfig(remoteConfig, srcConfig) {
  return _.assign({}, srcConfig, remoteConfig)
}

function _processSections(parentObject, tempOutputPath) {
  const sections = _.get(parentObject, "sections")

  if (!sections) {
    return parentObject
  }

  _prepOutputDir(tempOutputPath)

  parentObject.sections = _.map(sections, _.partial(_processSection, _, tempOutputPath))

  return parentObject
}

function _processSection(section, tempOutputPath) {
  // process child sections first
  section = _processSections(section, tempOutputPath)

  return _.merge({}, section, {
    content: _processSectionContent(section, tempOutputPath),
  })
}

function _processSectionContent(section, tempOutputPath) {
  const outputMarkdownPath = tempOutputPath + _makeSectionMarkdownFileName(section)
  const htmlContent = _.get(section, "content")
  const markdownContent = htmlContent ? turndownService.turndown(htmlContent) : ""

  fs.writeFileSync(outputMarkdownPath, markdownContent, "utf8")

  return outputMarkdownPath
}

function _makeSectionMarkdownFileName(section) {
  return _.kebabCase(_.get(section, "name")) + ".md"
}

function _getTurndownService() {
  const turndownService = new Turndown()

  turndownService.addRule("transform-code", {
    filter: ["code", "pre"],
    replacement(content) {
      return "```\n" + content + "\n```"
    },
  })

  return turndownService
}

function _prepOutputDir(tempOutputPath) {
  if (!fs.existsSync(tempOutputPath)) {
    fs.mkdirSync(tempOutputPath, { recursive: true })
  }
}

function _getTransforms(remoteConfig) {
  return _.castArray(_.get(remoteConfig, "transformResponse"))
}

function _applyRemoteConfigDefaults(remoteConfig) {
  return _.defaultsDeep({}, remoteConfig, defaultRemoteConfig)
}

function _addPrivateComponents(mergedConfig) {
  if (!_.isArray(mergedConfig.sections)) {
    mergedConfig.sections = []
  }

  mergedConfig.sections.push({
    name: "Private Components",
    exampleMode: "hide",
    usageMode: "hide",
    components: ["./src/**/*.vue", "./docs/components/**/*.vue"],
  })

  return mergedConfig
}

function getRemoteConfig(remoteConfig, srcConfig) {
  if (!remoteConfig) {
    return Promise.resolve(srcConfig)
  }

  remoteConfig = _applyRemoteConfigDefaults(remoteConfig)

  const uri = _makeRemoteUriFromRemoteConfig(remoteConfig)

  const spinner = ora("Getting remote config from " + uri + "...")

  const transforms = _getTransforms(remoteConfig)

  const requestOptions = {
    uri: uri,
    json: true,
  }

  spinner.start()

  return requestPromise(requestOptions)
    .then(_.partial(_.reduce, transforms, _applyTransform))
    .then(_.partial(_mergeConfig, _, srcConfig))
    .then(_.partial(_processSections, _, _.get(remoteConfig, "tempPath")))
    .then(_addPrivateComponents)
    .then(function(mergedConfig) {
      spinner.stop()

      console.log("Remote config merged!")
      console.log(mergedConfig.sections)
      return mergedConfig
    })
    .catch(function(err) {
      spinner.stop()

      console.log("Problem encountered getting remote config from " + uri)
      console.log(err)

      // return the original static config on error
      console.log("Ignoring remote config")
      return srcConfig

      // throw err
    })
}

function cleanup(remoteConfig, docsConfig) {
  const sectionMarkdownFiles = _.map(_.get(docsConfig, "sections"), _makeSectionMarkdownFileName)

  const tempPath = _.get(_applyRemoteConfigDefaults(remoteConfig), "tempPath")

  _.each(sectionMarkdownFiles, function(fileName) {
    rm(path.join(tempPath, fileName), function(err) {
      if (err) {
        console.log("Failed to remove temp markdown file", fileName, ":", err)
      }
    })
  })
}

module.exports = {
  getRemoteConfig,
  cleanup,
}
