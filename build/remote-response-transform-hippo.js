const _ = require("lodash")

let projectName = "myhippoproject"
let sectionsFieldTitle = "Sections"
let sectionsContentFieldTitle = "content"

function transformRawResponse(raw, args) {
  const instance = raw

  _setup(args)

  return {
    title: _getFieldValue(instance, "title"),
    sections: _extractEntrySections(instance),
  }
}

function _setup(args) {
  projectName = _.get(args, "projectName", projectName)
  sectionsFieldTitle = _.get(args, "sectionsFieldTitle", sectionsFieldTitle)
  sectionsContentFieldTitle = _.get(args, "sectionsContentFieldTitle", sectionsContentFieldTitle)
}

function _getFieldValue(entry, fieldPath) {
  return _.get(entry, "items." + projectName + ":" + fieldPath)
}

function _extractEntrySections(entry) {
  return _.filter(_.map(_getFieldValue(entry, sectionsFieldTitle), _.partial(_parseSection)))
}

function _extractSectionContent(section) {
  let contentObject = _getFieldValue(section, sectionsContentFieldTitle)
  let raw = _.get(contentObject, "content")
  let cons = false

  if ((section.name = "Grid")) {
    cons = true
  }

  return raw.replace(
    /data-hippo-link="([^"]*)"/gm,
    _.partial(_replaceHippoLink, _, _, _.get(contentObject, "links"), cons)
  )
}

function _replaceHippoLink(match, linkId, contentLinks, cons) {
  let srcAttribute = 'src="'

  /**
   * NOTE: the replace here rmeoves the port number from the URLs. This solution
   * needs to be replaced with something more stable via Hippo configuration.
   */
  srcAttribute += _.get(contentLinks, [linkId, "url"], "").replace(/:[0-9]{4}\//, "/")

  srcAttribute += '"'

  return srcAttribute
}

function _parseSection(section) {
  const fields = _.mapKeys(_.get(section, "items"), (value, key) => {
    return _.last(_.split(key, ":"))
  })

  if (fields[sectionsContentFieldTitle]) {
    fields.content = _extractSectionContent(section)
  }

  if (fields[sectionsFieldTitle]) {
    fields.sections = _extractEntrySections(section)
  }

  return fields
}

module.exports = {
  transformRawResponse,
}
