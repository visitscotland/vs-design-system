"use strict"
const webpack = require("webpack")
const merge = require("webpack-merge")
const baseWebpackConfig = require("./base.webpack.conf")
const MiniCssExtractPlugin = require("mini-css-extract-plugin")
const OptimizeCSSPlugin = require("optimize-css-assets-webpack-plugin")
const { CleanWebpackPlugin } = require('clean-webpack-plugin');
const SafeParser = require("postcss-safe-parser")
const ManifestPlugin = require("webpack-manifest-plugin")

const utils = require("./utils")
const config = require("../config")
const env = require("../config/prod.env")
const generateManifest = require("./library.generate-manifest")
const { mergeIE11Fix } = require("./webpack.ie11-fix")

baseWebpackConfig.entry = require("./library.entry.js")

// Remove the CSS extract from the base config to prevent duplicate CSS file
baseWebpackConfig.plugins = baseWebpackConfig.plugins.filter(plugin => {
  return !(plugin instanceof MiniCssExtractPlugin)
})

const webpackConfig = merge(mergeIE11Fix(baseWebpackConfig), {
  module: {
    rules: utils.styleLoaders({
      sourceMap: false,
      extract: true,
      usePostCSS: true,
    }),
  },
  devtool: false,
  output: {
    path: config.system.assetsRoot,
    filename: utils.assetsSystemPath(process.env.NODE_ENV === "development" ?
      "scripts/[name].js" :
      "scripts/[chunkhash].js",
    ),
    publicPath: config.system.assetsPublicPath,
    library: "[name]",
    libraryTarget: config.system.libraryTarget,
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
        "styles/[name].css" :
        "styles/[chunkhash].css"
      ),
    }),

    // Compress and dedupe extracted CSS
    new OptimizeCSSPlugin({
      cssProcessorOptions: { parser: SafeParser },
    }),

    // Keep module.id stable when vendor modules does not change
    new webpack.HashedModuleIdsPlugin(),

    // Generate custom manifest.json
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
