const { extend, reduce, invoke, mapKeys, map, filter } = require("lodash")

function extractAllEntriesFromFileDescriptor(FileDescriptor) {
  return reduce(
    filter(FileDescriptor, "isInitial"),
    (acc, val) => {
      return extend({}, acc, extractEntriesFromOutputFile(val))
    },
    {}
  )
}

function extractEntriesFromOutputFile(outputFile) {
  let entries = Array.from(invoke(outputFile, "chunk._groups.values"))

  return entries.length ? mapKeys(entries, "options.name") : {}
}

/**
 * Generates manifest file for split component build
 */
module.exports = (Object, FileDescriptor) => {
  let manifest = {}

  let componentEntries = extractAllEntriesFromFileDescriptor(FileDescriptor)

  return {}
}
