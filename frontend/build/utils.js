"use strict"
const path = require("path")
const MiniCssExtractPlugin = require("mini-css-extract-plugin")
const packageConfig = require("../package.json")

exports.cssLoaders = function(options) {
  options = options || {}

  const cssLoader = {
    loader: "css-loader",
    options: {
      sourceMap: options.sourceMap,
    },
  }

  const postcssLoader = {
    loader: "postcss-loader",
    options: {
      sourceMap: options.sourceMap,
    },
  }

  const baseSassLoaderOptions = {
    style: "compressed",
    implementation: require("sass"),
    sourceMap: options.sourceMap,
  }

  const sassResourcesLoader = {
    loader: "sass-resources-loader",
    options: {
      resources: [
        path.resolve(__dirname, "../src/assets/tokens/tokens.scss"),
        path.resolve(__dirname, "../src/assets/tokens/tokens.map.scss"),
        path.resolve(__dirname, "../src/styles/styles.scss"),
      ],
    },
  }

  // generate loader string to be used with extract text plugin
  function generateLoaders(sassLoader, extraSassLoaderOptions) {
    const loaders = []

    // Extract CSS when that option is specified
    // (which is the case during production build)
    if (options.extract) {
      loaders.push(MiniCssExtractPlugin.loader)
    } else {
      loaders.push("vue-style-loader")
    }

    loaders.push(cssLoader)

    if (options.usePostCSS) {
      loaders.push(postcssLoader)
    }

    if (sassLoader) {
      loaders.push({
        loader: "sass-loader",
        options: {
          ...baseSassLoaderOptions,
          ...extraSassLoaderOptions || {},
        }
      })

      loaders.push(sassResourcesLoader)
    }

    return loaders
  }

  // https://vue-loader.vuejs.org/guide/extract-css.html
  return {
    css: generateLoaders(),
    postcss: generateLoaders(),
    sass: generateLoaders(true, { indentedSyntax: true }),
    scss: generateLoaders("sass"),
  }
}

// Generate loaders for standalone style files (outside of .vue)
exports.styleLoaders = function(options) {
  const output = []
  const loaders = exports.cssLoaders(options)

  for (const extension in loaders) {
    const loader = loaders[extension]
    output.push({
      test: new RegExp("\\." + extension + "$"),
      use: loader,
    })
  }

  return output
}

exports.packageName = packageConfig.name
