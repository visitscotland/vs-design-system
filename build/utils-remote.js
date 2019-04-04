#! /usr/bin/env node
const _ = require("lodash")
const fs = require("fs")
const axios = require("axios")
const Turndown = require("turndown")
const ora = require("ora")
const rm = require("rimraf")
const path = require("path")

const StyleguidistError = require("react-styleguidist/scripts/utils/error")

const defaultRemoteConfig = {
  tempPath: "./.tmp/",
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
  return _.isFunction(transform) ? transform(apiResponse) : apiResponse
}

function _mergeConfig(remoteConfig, srcConfig) {
  return _.assign({}, srcConfig, remoteConfig)
}

function _processConfigSections(remoteConfig, srcConfig) {
  const sections = _.get(srcConfig, "sections")
  let turndownService = null

  if (!sections) {
    return srcConfig
  }

  turndownService = _getTurndownService()

  _prepOutputDir(remoteConfig)

  srcConfig.sections = _processSections(sections, remoteConfig, turndownService)

  return srcConfig
}

function _processSections(sections, remoteConfig, turndownService) {
  const tempRootPath = _.get(remoteConfig, "tempPath")
  return _.map(sections, _.partial(_processSection, _, tempRootPath, turndownService))
}

function _processSection(section, tempRootPath, turndownService) {
  const outputMarkdownPath = tempRootPath + _makeSectionMarkdownFileName(section)
  const markDownContent = turndownService.turndown(_.get(section, "content"))

  fs.writeFileSync(outputMarkdownPath, markDownContent, "utf8")

  return _.merge({}, section, {
    content: outputMarkdownPath,
  })
}

function _makeSectionMarkdownFileName(section) {
  return _.kebabCase(_.get(section, "name")) + ".md"
}

function _getTurndownService() {
  const turndownService = new Turndown()

  turndownService.addRule("transform-code", {
    filter: ["code"],
    replacement(content) {
      return "```\n" + content + "\n```"
    },
  })

  return turndownService
}

function _prepOutputDir(remoteConfig) {
  const tempOutputPath = _.get(remoteConfig, "tempPath")

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

function getRemoteConfig(remoteConfig, srcConfig) {
  if (!remoteConfig) {
    return Promise.resolve(srcConfig)
  }

  remoteConfig = _applyRemoteConfigDefaults(remoteConfig)

  const uri = _makeRemoteUriFromRemoteConfig(remoteConfig)

  const spinner = ora("Getting remote config from " + uri + "...")

  const transforms = _getTransforms(remoteConfig)

  spinner.start()

  return axios
    .get(uri)
    .then(_.ary(_.partial(_.get, _, "data"), 1))
    .then(_.partial(_.reduce, transforms, _applyTransform, _))
    .then(_.partial(_mergeConfig, _, srcConfig))
    .then(_.partial(_processConfigSections, remoteConfig))
    .then(function(mergedConfig) {
      spinner.stop()

      console.log("Remote config merged!")

      return mergedConfig
    })
    .catch(function(err) {
      spinner.stop()

      console.log("Problem encountered getting remote config: " + err)

      throw err
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
