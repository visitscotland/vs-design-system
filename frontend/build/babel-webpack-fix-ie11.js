const path = require("path")
const _ = require("lodash")

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
    resolve("node_modules/bootstrap-vue"),
    resolve("node_modules/vuex"),
]

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



function getBaseConfig(config) {
    const babelRule = _.find(_.get(config, "module.rules"), ["loader", "babel-loader"])
  
    if (babelRule) {
      // insert extra includes and settings to the base config for docs generation
      _.unset(babelRule, "loader")
  
      babelRule.include = _.concat(babelRule.include, webpackBabelRuleIncludes)
      babelRule.use = webpackBabelRuleUse
    }
  
    return config
  }

  module.exports = {
    getBaseConfig
  }
  
  