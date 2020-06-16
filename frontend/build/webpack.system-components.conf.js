"use strict"
const webpack = require("webpack")
const path = require("path")
const merge = require("webpack-merge")
const baseWebpackConfig = require("./webpack.base.conf")
const MergeWebpackPlugin = require("webpack-merge-and-include-globally")
const MiniCssExtractPlugin = require("mini-css-extract-plugin")
const OptimizeCSSPlugin = require("optimize-css-assets-webpack-plugin")
const CopyWebpackPlugin = require("copy-webpack-plugin")
const { CleanWebpackPlugin } = require('clean-webpack-plugin');
const SafeParser = require("postcss-safe-parser")
const ManifestPlugin = require("webpack-manifest-plugin")

const utils = require("./utils")
const config = require("../config")
const env = require("../config/prod.env")
const generateManifest = require("./system-components-generate-manifest")
const { mergeIE11Fix } = require("./webpack.ie11-fix")


baseWebpackConfig.entry = require("./entry.system-components.js")

// Remove the CSS extract from the base config to prevent duplicate CSS file
baseWebpackConfig.plugins = baseWebpackConfig.plugins.filter(plugin => {
  return !(plugin instanceof MiniCssExtractPlugin)
})

const webpackConfig = merge(mergeIE11Fix(baseWebpackConfig), {
  externals: {
    vue: "Vue",
  },
  module: {
    rules: utils.styleLoaders({
      sourceMap: config.system.productionSourceMap,
      extract: true,
      usePostCSS: true,
    }),
  },
  devtool: config.build.productionSourceMap ? config.system.devtool : false,
  output: {
    path: config.system.assetsRoot,
    filename: utils.assetsSystemPath(process.env.NODE_ENV === "development" ?
      "components/[name].js" :
      "components/[chunkhash].js",
    ),
    publicPath: config.system.assetsPublicPath,
    library: "[name]",
    libraryTarget: config.system.libraryTarget,
  },
  performance: {
    hints: config.system.performanceHints,
  },
  plugins: [
    // http://vuejs.github.io/vue-loader/en/workflow/production.html
    new webpack.DefinePlugin({
      "process.env": env,
    }),
    // extract css into its own file
    new MiniCssExtractPlugin({
      filename: utils.assetsSystemPath(process.env.NODE_ENV === "development" ?
        "components/[name].css" :
        "components/[chunkhash].css"
      ),
      // This will give the chunks hash names rather than meaningful names
      // Use this in real production
      //filename: utils.assetsSystemPath("components/[chunkhash].css"),
    }),
    // Compress extracted CSS. We are using this plugin so that possible
    // duplicated CSS from different components can be deduped.
    new OptimizeCSSPlugin({
      cssProcessorOptions: { parser: SafeParser },
    }),
    // keep module.id stable when vendor modules does not change
    new webpack.HashedModuleIdsPlugin(),
    // Copy and merge Sass tokens and system utilities as well
    new MergeWebpackPlugin({
      files: {
        [utils.assetsSystemPath("system.utils.scss")]: [
          "./src/assets/tokens/tokens.scss",
          "./src/styles/_spacing.scss",
          "./src/styles/_mixins.scss",
          "./src/styles/_functions.scss",
        ],
      },
    }),
    // copy custom static assets
    new CopyWebpackPlugin([
      {
        from: path.resolve(__dirname, "../src/assets"),
        to: config.system.assetsSubDirectory,
        ignore: [".*"],
      },
    ]),
    new ManifestPlugin({
      generate: generateManifest,
      fileName: "manifest.json",
    }),
    new CleanWebpackPlugin(),
  ],
})

if (config.system.productionGzip) {
  const CompressionWebpackPlugin = require("compression-webpack-plugin")

  webpackConfig.plugins.push(
    new CompressionWebpackPlugin({
      asset: "[path].gz[query]",
      algorithm: "gzip",
      test: new RegExp("\\.(" + config.system.productionGzipExtensions.join("|") + ")$"),
      threshold: 10240,
      minRatio: 0.8,
    })
  )
}

if (config.system.bundleAnalyzerReport) {
  const BundleAnalyzerPlugin = require("webpack-bundle-analyzer").BundleAnalyzerPlugin
  webpackConfig.plugins.push(new BundleAnalyzerPlugin())
}

module.exports = webpackConfig
