const CopyPlugin = require("copy-webpack-plugin")
const { CleanWebpackPlugin } = require("clean-webpack-plugin")
const glob = require("glob")
const path = require("path")

const DEFAULT_SOURCE_PATH = "./node_modules/vs-dotcom-ds/dist/system-components"
const DEFAULT_OUTPUT_PATH_ROOT = "../repository-data/webfiles/src/main/resources/site/"
const ASSET_OUTPUT_PATH = "design-system"
const TEMPLATE_OUTPUT_PATH = "freemarker/include/vs-dotcom-ds"

module.exports = function(env, argv) {
  srcPath = path.resolve(argv["src-path"] || DEFAULT_SOURCE_PATH)
  outputPath = path.resolve(__dirname, argv["output-path"] || DEFAULT_OUTPUT_PATH_ROOT)
  assetOutputPath = path.join(outputPath, ASSET_OUTPUT_PATH)

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
                targetPath: path.join(outputPath, TEMPLATE_OUTPUT_PATH),
              },
            },
          ],
        },
      ],
    },
  }
}
