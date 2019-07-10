#! /usr/bin/env node
const fs = require("fs")
const rm = require("rimraf")
const path = require("path")

const _ = require("lodash")
const ora = require("ora")
const chalk = require("chalk")
const requestPromise = require("request-promise-native")
const Turndown = require("turndown")

const getConfig = require("vue-styleguidist/scripts/config")
const StyleguidistError = require("react-styleguidist/lib/scripts/utils/error")

const defaultRemoteConfig = {
  tempPath: "./.tmp/",
}

const turndownService = _getTurndownService()

const defaultRequestOptions = {
  json: true,
}

let remoteConfig = null
let spinner

function getRemoteConfig(argv) {
  const localConfig = getConfig(argv.config)

  remoteConfig = _getRemoteConfigProfile(argv)

  if (!remoteConfig) {
    return Promise.resolve(localConfig)
  }

  remoteConfig = _applyRemoteConfigDefaults(remoteConfig)

  const transforms = _getTransforms(remoteConfig)

  const requestOptions = _makeRequestOptions(remoteConfig)

  spinner = ora("Getting remote config from " + requestOptions.uri + "...")

  spinner.start()

  return requestPromise(requestOptions)
    .then(_.partial(_.reduce, transforms, _applyTransform))
    .then(_.partial(_mergeConfig, _, localConfig))
    .then(_.partial(_processSections, _, _.get(remoteConfig, "tempPath")))
    .then(_addPrivateComponents)
    .then(function(mergedConfig) {
      spinner.stop()

      console.log(chalk.cyan("Remote config merged!"))

      return mergedConfig
    })
    .catch(function(err) {
      spinner.stop()

      console.log(chalk.red("Problem encountered getting remote config from " + requestOptions.uri))
      console.log(err)

      // return the original static config on error
      console.log(chalk.cyan("Ignoring remote config"))
      return localConfig

      // throw err
    })
}

function cleanup(docsConfig) {
  const sectionMarkdownFiles = _.map(_.get(docsConfig, "sections"), _makeSectionMarkdownFileName)

  const tempPath = _.get(_applyRemoteConfigDefaults(remoteConfig), "tempPath")

  _.each(sectionMarkdownFiles, function(fileName) {
    rm(path.join(tempPath, fileName), function(err) {
      if (err) {
        console.log(chalk.red("Cleanup: failed to remove temp markdown file", fileName, ":", err))
      }
    })
  })
}

function _makeRequestOptions(remoteConfig) {
  let options = {
    uri: _makeRemoteUriFromRemoteConfig(remoteConfig),
  }

  return _.defaults(options, _.get(remoteConfig, "requestOptions"), defaultRequestOptions)
}

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

function _mergeConfig(remoteConfig, localConfig) {
  return _.assign({}, localConfig, remoteConfig)
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
  if (!mergedConfig.sections || !_.isArray(mergedConfig.sections)) {
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

function _getRemoteConfigProfile(argv) {
  const remoteConfigFile = argv["remote-config"]
  const remoteConfig = remoteConfigFile ? require(remoteConfigFile) : undefined
  const profileName = argv["remote-profile"]

  return _.get(remoteConfig, profileName) || _.get(remoteConfig, _.first(_.keys(remoteConfig)))
}

module.exports = {
  getRemoteConfig,
  cleanup,
}
