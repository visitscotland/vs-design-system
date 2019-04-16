const minimist = require("minimist")
const partial = require("lodash/partial")

const styleguidist = require("vue-styleguidist")
const remoteUtils = require("./utils-remote.js")

const argv = minimist(process.argv.slice(2))
const command = argv._[0]

// Set environment before loading style guide config because user’s webpack config may use it
const env = command === "build" ? "production" : "development"

process.env.NODE_ENV = process.env.NODE_ENV || env

if (!process.env.REMOTE_CONFIG_HIPPO_SPACE) {
  require("dotenv").config()
}

remoteUtils
  .getRemoteConfig(argv)
  .then(partial(run, command))
  .catch(function(err) {
    console.log("Problem getting remote config:", err)
  })

function run(command, config) {
  const styleguide = styleguidist(config)

  switch (command) {
    case "server":
      styleguide.server(serverCallback)
      break
    case "build":
    default:
      styleguide.build(buildCallback)
  }
}

function buildCallback(err, config, stats) {
  if (err) {
    console.log("Error building remote styleguide:", err)
  } else {
    console.log(config.title, "published to", config.styleguideDir)
  }

  remoteUtils.cleanup(config)
}

function serverCallback(err, config) {
  if (err) {
    console.log("Error serving remote styleguide:", err)
  } else {
    const url = `http://${config.serverHost}:${config.serverPort}`

    console.log(config.title, `running at ${url}`)
  }

  // can't cleanup until after the server has stopped
  // remoteUtils.cleanup(remoteConfig, config)
}
