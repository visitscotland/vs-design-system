const _ = require("lodash")

let projectName = "myhippoproject"

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
}

function _getFieldValue(entry, fieldPath) {
  return _.get(entry, "items." + projectName + ":" + fieldPath)
}

function _extractEntrySections(entry) {
  return _.filter(_.map(_getFieldValue(entry, "sections"), _.partial(_parseSection)))
}

function _extractSectionContent(section) {
  return _getFieldValue(section, "content.content")
}

function _parseSection(section) {
  const fields = _.mapKeys(_.get(section, "items"), (value, key) => {
    return _.last(_.split(key, ":"))
  })

  if (fields.content) {
    fields.content = _extractSectionContent(section)
  }

  if (fields.sections) {
    fields.sections = _extractEntrySections(section)
  }

  return fields
}

module.exports = {
  transformRawResponse,
}
