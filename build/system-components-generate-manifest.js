const {
  extend,
  reduce,
  invoke,
  mapKeys,
  flatMap,
  mapValues,
  filter,
  get,
  uniq,
  partial,
  ary,
  endsWith,
  remove,
} = require("lodash")

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

function mapComponentEntryFiles(component) {
  let files = uniq(flatMap(get(component, "chunks"), "files"))

  return {
    scripts: remove(files, ary(partial(endsWith, partial.placeholder, ".js"), 1)),
    styles: remove(files, ary(partial(endsWith, partial.placeholder, ".css"), 1)),
    other: files,
  }
}

/**
 * Generates manifest file for system component build
 */
module.exports = (Object, FileDescriptor) => {
  let componentEntries = extractAllEntriesFromFileDescriptor(FileDescriptor)

  return mapValues(componentEntries, mapComponentEntryFiles)
}
