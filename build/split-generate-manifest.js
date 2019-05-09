const _ = require("lodash")

function extractAllEntriesFromFileDescriptor(FileDescriptor) {
  return _.reduce(
    _.filter(FileDescriptor, "isInitial"),
    (acc, val) => {
      return _.extend({}, acc, extractEntriesFromOutputFile(val))
    },
    {}
  )
}

function extractEntriesFromOutputFile(outputFile) {
  let entries = Array.from(_.invoke(outputFile, "chunk._groups.values"))

  return entries.length ? _.mapKeys(entries, "options.name") : {}
}

function generateEntry(entry) {
  const files = _.flatMap(_.get(entry, "chunks"), "files")

  return {
    css: _.filter(files, _.ary(_.partial(_.endsWith, _, ".css"), 1)),
    js: _.filter(files, _.ary(_.partial(_.endsWith, _, ".js"), 1)),
  }
}

/**
 * Generates manifest file for split component build
 */
module.exports = (Object, FileDescriptor) => {
  let componentEntries = extractAllEntriesFromFileDescriptor(FileDescriptor)

  return _.mapValues(componentEntries, generateEntry)
}
