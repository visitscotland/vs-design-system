const fs = require("fs")
const path = require("path")
const findup = require("findup")
const isString = require("lodash/isString")
const isPlainObject = require("lodash/isPlainObject")
const StyleguidistError = require("react-styleguidist/scripts/utils/error")
const sanitizeConfig = require("react-styleguidist/scripts/utils/sanitizeConfig")
const schema = require("vue-styleguidist/scripts/schemas/config")

const CONFIG_FILENAME = "styleguide.config.js"

const mergeContentfulConfig = require("./merge-contentful-config.js")

const tempOutputPath = "temp/"

/**
 * Read, parse and validate config file or passed config.
 *
 * @param {object|string} [config] All config options or config file name or nothing.
 * @param {function} [update] Change config object before running validation on it.
 * @returns {object}
 */
function getConfig(config, update) {
  let configFilepath
  if (isString(config)) {
    // Load config from a given file
    configFilepath = path.resolve(process.cwd(), config)
    if (!fs.existsSync(configFilepath)) {
      throw new StyleguidistError("Styleguidist config not found: " + configFilepath + ".")
    }
    config = {}
  } else if (!isPlainObject(config)) {
    // Try to read config options from a file
    configFilepath = findConfigFile()
    config = {}
  }

  if (configFilepath) {
    config = require(configFilepath)
  }

  if (update) {
    config = update(config)
  }

  const configDir = configFilepath ? path.dirname(configFilepath) : process.cwd()
  console.log("STATIC--------")
  console.log(config.sections)
  return mergeContentfulConfig(config, tempOutputPath)
    .then(function(mergedConfig) {
      console.log("MERGED--------")
      console.log(mergedConfig.sections)
      return sanitizeConfig(mergedConfig, schema, configDir)
    })
    .catch(function(err) {
      /* eslint-disable */
      console.log(exception instanceof StyleguidistError, exception.constructor.name)
      throw exception.message
    })
}

/**
 * Try to find config file up the file tree.
 *
 * @return {string|boolean} Config absolute file path.
 */
function findConfigFile() {
  let configDir
  try {
    configDir = findup.sync(process.cwd(), CONFIG_FILENAME)
  } catch (exception) {
    return false
  }

  return path.join(configDir, CONFIG_FILENAME)
}

module.exports = getConfig
