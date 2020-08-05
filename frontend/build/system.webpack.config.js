const path = require("path")

const CopyWebpackPlugin = require("copy-webpack-plugin")
const HtmlWebpackPlugin = require("html-webpack-plugin")
const FriendlyErrorsPlugin = require("friendly-errors-webpack-plugin")
const merge = require("webpack-merge")

const baseWebpackConfig = require("./base.webpack.conf")
const mergeIE11Fix = require("./webpack.ie11-fix")

const utils = require("./utils")

const styleRules = utils.styleLoaders({
    sourceMap: baseWebpackConfig.mode === "development",
    extract: baseWebpackConfig.mode !== "development",
    usePostCSS: true,
})

const config = {
    output: {
        path: path.resolve(__dirname, "../dist")
    },
    resolve: {
        alias: {
            "rsg-components/ReactComponent/ReactComponentRenderer":
                "rsg-components-default/ReactComponent/ReactComponentRenderer",
            "rsg-components/Playground/PlaygroundRenderer":
                "rsg-components-default/Playground/PlaygroundRenderer",
        },
    },
    module: {
        rules: [
            ...styleRules,
        ]
    },
}

module.exports = merge(
    mergeIE11Fix(baseWebpackConfig),
    config,
)
