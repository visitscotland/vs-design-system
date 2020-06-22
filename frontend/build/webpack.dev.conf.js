"use strict"
const utils = require("./utils")
const webpack = require("webpack")
const merge = require("webpack-merge")
const path = require("path")
const baseWebpackConfig = require("./base.webpack.conf")
const CopyWebpackPlugin = require("copy-webpack-plugin")
const HtmlWebpackPlugin = require("html-webpack-plugin")
const FriendlyErrorsPlugin = require("friendly-errors-webpack-plugin")
const portfinder = require("portfinder")

const HOST = process.env.HOST
const PORT = process.env.PORT && Number(process.env.PORT)

const devWebpackConfig = merge(baseWebpackConfig, {
  mode: "development",
  module: {
    rules: utils.styleLoaders({ sourceMap: true, usePostCSS: true }),
  },
  // cheap-module-eval-source-map is faster for development
  devtool: "cheap-module-eval-source-map",

  devServer: {
    clientLogLevel: "warning",
    historyApiFallback: {
      rewrites: [{ from: /.*/, to: path.posix.join("/", "index.html") }],
    },
    hot: true,
    contentBase: false, // since we use CopyWebpackPlugin.
    compress: true,
    host: HOST || "localhost",
    port: PORT || "7070",
    open: false,
    overlay: {
      warnings: false,
      errors: true,
    },
    publicPath: "/",
    proxy: "{}",
    quiet: true, // necessary for FriendlyErrorsPlugin
    watchOptions: {
      poll: false,
    },
  },
  plugins: [
    new webpack.HotModuleReplacementPlugin(),
    // https://github.com/ampedandwired/html-webpack-plugin
    new HtmlWebpackPlugin({
      filename: "index.html",
      template: "index.html",
      inject: true,
    }),
    // copy custom static assets
    new CopyWebpackPlugin([
      {
        from: path.resolve(__dirname, "../src/assets"),
        to: "assets",
        ignore: [".*"],
      },
    ]),
  ],
  optimization: {
    namedModules: true,
    noEmitOnErrors: true,
  },
})

module.exports = new Promise((resolve, reject) => {
  portfinder.basePort = devWebpackConfig.devServer.port
  portfinder.getPort((err, port) => {
    if (err) {
      reject(err)
    } else {
      // publish the new Port, necessary for e2e tests
      process.env.PORT = port
      // add port to devServer config
      devWebpackConfig.devServer.port = port

      // Add FriendlyErrorsPlugin
      devWebpackConfig.plugins.push(
        new FriendlyErrorsPlugin({
          compilationSuccessInfo: {
            messages: [
              `Design System Docs: http://${devWebpackConfig.devServer.host}:6060 \n`,
              `Vue.js App: http://${devWebpackConfig.devServer.host}:${port} \n`,
            ],
          },
          // should the console be cleared between each compilation?
          // default is true
          clearConsole: true,
        })
      )

      resolve(devWebpackConfig)
    }
  })
})
