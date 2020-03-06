const CopyPlugin = require("copy-webpack-plugin")
const { CleanWebpackPlugin } = require("clean-webpack-plugin")
const path = require("path")

const DEFAULT_SOURCE_PATH = "../frontend/dist"
const DEFAULT_OUTPUT_PATH_ROOT = "../repository-data/webfiles/src/main/resources/site/"
const ASSET_OUTPUT_PATH = "design-system"
const TEMPLATE_OUTPUT_PATH = "freemarker/vs-dotcom-ds"
const FTL_IMPORTS_FILE_PATH = "../../include/imports.ftl"
const VUE_TEMPLATE_PATH = "./build/templates/vue-app-init.ftl"

module.exports = function(env, argv) {
  const srcPath = path.resolve(__dirname, argv["src-path"] || DEFAULT_SOURCE_PATH)
  const outputPath = path.resolve(__dirname, argv["output-path"] || DEFAULT_OUTPUT_PATH_ROOT)
  const assetOutputPath = path.join(outputPath, ASSET_OUTPUT_PATH)
  const templateOutputPath = path.join(outputPath, TEMPLATE_OUTPUT_PATH)

  return {
    mode: "production",
    entry: path.join(srcPath, "/manifest.json"),
    output: {
      path: assetOutputPath,
    },
    plugins: [
      new CopyPlugin([
        {
          from: srcPath,
          to: assetOutputPath,
        },
        {
          from: path.resolve(__dirname, VUE_TEMPLATE_PATH),
          to: templateOutputPath,
        },
      ]),
      new CleanWebpackPlugin(),
    ],
    module: {
      rules: [
        {
          test: /\.(\.)$/,
          use: [
            {
              loader: "file-loader",
              options: {},
            },
          ],
        },
        {
          test: /\.json$/,
          use: [
            {
              loader: path.resolve("./build/generateFreemarkerTemplate.js"),
              options: {
                targetPath: templateOutputPath,
                importsPath: FTL_IMPORTS_FILE_PATH
              },
            },
          ],
        },
      ],
    },
  }
}