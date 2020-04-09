"use strict"
const _ = require("lodash")
const utils = require("./utils")
const webpack = require("webpack")
const path = require("path")
const config = require("../config")
const merge = require("webpack-merge")
const baseWebpackConfig = require("./webpack.base.conf")
const MergeWebpackPlugin = require("webpack-merge-and-include-globally")
const MiniCssExtractPlugin = require("mini-css-extract-plugin")
const OptimizeCSSPlugin = require("optimize-css-assets-webpack-plugin")
const CopyWebpackPlugin = require("copy-webpack-plugin")
const SafeParser = require("postcss-safe-parser")

const env = require("../config/prod.env")

const ManifestPlugin = require("webpack-manifest-plugin")

const generateManifest = require("./system-components-generate-manifest")

const webpackBabelRuleIncludes = [
  resolve("node_modules/regexpu-core"),
  resolve("node_modules/unicode-match-property-ecmascript"),
  resolve("node_modules/unicode-match-property-value-ecmascript"),
  resolve("node_modules/buble/node_modules/acorn-jsx"),
  resolve("node_modules/react-dev-utils"),
  resolve("node_modules/chalk"),
  resolve("node_modules/ansi-styles"),
  resolve("node_modules/acorn-jsx"),
  resolve("node_modules/camelcase"),
  resolve("node_modules/bootstrap-vue")
]

baseWebpackConfig.entry = require("../src/utils/entry.system-components.js")

// Remove the CSS extract from the base config to prevent duplicate CSS file
baseWebpackConfig.plugins = baseWebpackConfig.plugins.filter(plugin => {
  return !(plugin instanceof MiniCssExtractPlugin)
})

const webpackBabelRuleUse = {
  loader: "babel-loader",
  options: {
      sourceType: "unambiguous",
      presets: [
          [
              "@babel/preset-env",
              {
                  useBuiltIns: "usage",
                  corejs: 3,
                  targets: {
                      ie: "11",
                  },
              },
          ],
      ],
      comments: false,
  },
}

function resolve(dir) {
  return path.join(__dirname, "..", dir)
}

function getBaseConfig() {
  const babelRule = _.find(_.get(baseWebpackConfig, "module.rules"), ["loader", "babel-loader"])

  if (babelRule) {
    // insert extra includes and settings to the base config for docs generation
    _.unset(babelRule, "loader")

    babelRule.include = _.concat(babelRule.include, webpackBabelRuleIncludes)
    babelRule.use = webpackBabelRuleUse
  }

  return baseWebpackConfig
}

const webpackConfig = merge(getBaseConfig(), {
  externals: {
    vue: "Vue",
    vuex: "Vuex",
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
    filename: utils.assetsSystemPath("components/[name].js"),
    // This will give the chunks hash names rather than meaningful names
    // Use this in real production
    // filename: utils.assetsSystemPath("components/[chunkhash].js"),
    library: "[name]",
    libraryTarget: config.system.libraryTarget,
  },
  performance: {
    hints: config.system.performanceHints,
  },
  optimization: {
    splitChunks: {
      chunks: "all",
      minSize: 0,
      maxInitialRequests: Infinity,
    },
    runtimeChunk: "single",
    concatenateModules: false,
  },
  plugins: [
    // http://vuejs.github.io/vue-loader/en/workflow/production.html
    new webpack.DefinePlugin({
      "process.env": env,
    }),
    // extract css into its own file
    new MiniCssExtractPlugin({
      filename: utils.assetsSystemPath("components/[name].css"),
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
