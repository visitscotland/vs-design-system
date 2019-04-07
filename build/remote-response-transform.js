const _ = require("lodash")

function transformRawResponse(raw) {
  const instance = raw

  console.log("title = " + _getFieldValue(instance, "title"))

  return {
    title: _getFieldValue(instance, "title"),
    sections: _extractEntrySections(instance),
  }
}

function _getFieldValue(entry, fieldPath) {
  return _.get(entry, "items.myhippoproject:" + fieldPath)
}

function _extractEntrySections(entry) {
  return _.filter(_.map(_getFieldValue(entry, "sections"), _.partial(_parseSection)))
}

function _parseSection(section) {
  const fields = _.mapKeys(_.get(section, "items"), (value, key) => {
    return _.last(_.split(key, ":"))
  })

  if (fields.sections) {
    fields.sections = _extractEntrySections(section)
  }
  console.log("---------")
  console.log(fields)
  console.log("---------")
  return fields
}

module.exports = {
  transformRawResponse,
}
