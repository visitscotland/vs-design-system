const minimist = require("minimist")
const partial = require("lodash/partial")
const chalk = require("chalk")
const ora = require("ora")

const StyleguidistError = require("react-styleguidist/scripts/utils/error")
const styleguidist = require("vue-styleguidist")

const remoteUtils = require("./utils-remote.js")

const argv = minimist(process.argv.slice(2))
const command = argv._[0]

// Set environment before loading style guide config because user’s webpack config may use it
const env = command === "build" ? "production" : "development"

const spinner = ora("Building design system...")

// Do not show nasty stack traces for Styleguidist errors
process.on("uncaughtException", err => {
  if (err.code === "EADDRINUSE") {
    console.log(
      `Another server is running at port ${
        config.serverPort
      } already. Please stop it or change the default port to continue. You can change the port using the \`serverPort\` option in the styleguide config`
    )
  } else if (err instanceof StyleguidistError) {
    console.error(chalk.red(err.message))
    logger.debug(err.stack)
  } else {
    console.error(err.toString())
    console.error(err.stack)
  }
  process.exit(1)
})

process.env.NODE_ENV = process.env.NODE_ENV || env

if (!process.env.REMOTE_CONFIG_HIPPO_SPACE) {
  require("dotenv").config()
}

remoteUtils
  .getRemoteConfig(argv)
  .then(partial(run, command))
  .catch(function(err) {
    console.log(chalk.red("Problem getting remote config:", err))
  })

function run(command, config) {
  spinner.start()

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

function callbackCommon(err, stats) {
  spinner.stop()
  if (err) throw err

  if (stats) {
    process.stdout.write(
      stats.toString({
        colors: true,
        modules: false,
        children: false,
        chunks: false,
        chunkModules: false,
      }) + "\n\n"
    )

    if (stats.hasErrors()) {
      console.log(chalk.red("  Design System build failed with errors.\n"))
      process.exit(1)
    }
  }

  console.log(chalk.cyan("Design System build complete.\n"))
}

function buildCallback(err, config, stats) {
  callbackCommon(err, stats)

  // console.log(
  //   chalk.yellow(
  //     "  Tip: You can now publish the design system as a private NPM module.\n" +
  //       "  Users can import the provided UMD module using:\n\n" +
  //       "  import DesignSystem from 'vs-design-system'\n" +
  //       "  import 'vs-design-system/dist/system/system.css'\n\n" +
  //       "  Vue.use(DesignSystem)\n"
  //   )
  // )

  remoteUtils.cleanup(config)
}

function serverCallback(err, config, stats) {
  callbackCommon(err, stats)

  const url = `http://${config.serverHost === "0.0.0.0" ? "localhost" : config.serverHost}:${
    config.serverPort
  }`

  console.log(chalk.yellow(config.title, `running at ${url}`))

  // can't cleanup until after the server has stopped
  // remoteUtils.cleanup(remoteConfig, config)
}
