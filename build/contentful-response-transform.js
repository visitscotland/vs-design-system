const documentToHtmlString = require("@contentful/rich-text-html-renderer").documentToHtmlString
const _ = require("lodash")

function getContentfulFieldValue(contentfulEntry, fieldPath) {
  return _.get(contentfulEntry, "fields." + fieldPath)
}

function extractContentfulEntrySections(contentfulEntry, relatedEntries) {
  return _.map(
    getContentfulFieldValue(contentfulEntry, "sections"),
    _.partial(parseContentfulSection, _, relatedEntries)
  )
}

function parseContentfulSection(sectionLinkObj, relatedEntries) {
  const section = _.find(
    relatedEntries,
    _.matchesProperty("sys.id", _.get(sectionLinkObj, "sys.id"))
  )

  const fields = _.get(section, "fields")

  if (fields.content) {
    fields.content = documentToHtmlString(fields.content)
  }

  return fields
}

module.exports = function transformRemoteConfig(response) {
  const instance = _.get(response, "items.0")
  const relatedEntries = _.get(response, "includes.Entry")

  return {
    title: getContentfulFieldValue(instance, "title"),
    sections: extractContentfulEntrySections(instance, relatedEntries),
  }
}
