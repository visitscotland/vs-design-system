"use strict"
require("./check-versions")()

process.env.NODE_ENV = "production"

const ora = require("ora")
const rm = require("rimraf")
const path = require("path")
const chalk = require("chalk")
const webpack = require("webpack")
const config = require("../config")

const spinner = ora("Building frontend components...")

const isComponentsBuild = require("./system-components")

const buildCompleteInfo =
  "  Tip: You can now publish the design system as a private NPM module.\n" +
  "  Users can import the provided UMD module using:\n\n" +
  "  import DesignSystem from 'vue-design-system'\n" +
  "  import 'vue-design-system/dist/system/system.css'\n\n" +
  "  Vue.use(DesignSystem)\n"

const buildCompleteInfoComponents =
  "  Tip: You can now use the built components in other projects by importing the dist/system folder.\n\n" +
  "  The system/manifest.json file lists which assets need to be included for each component.\n" +
  "  Once all relevent assets are referenced, add the component to the Vue app by registering it, e.g:\n\n" +
  "  Vue.component('vs-col', Col.default)\n"

const webpackConfig = require(isComponentsBuild
  ? "./webpack.system-components.conf"
  : "./webpack.system.conf")

spinner.start()

rm(path.join(config.system.assetsRoot, config.system.assetsSubDirectory), err => {
  if (err) throw err
  webpack(webpackConfig, (err, stats) => {
    spinner.stop()
    if (err) throw err
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

    console.log(chalk.cyan("  Design System build complete.\n"))
    console.log(chalk.yellow(isComponentsBuild ? buildCompleteInfoComponents : buildCompleteInfo))
  })
})
