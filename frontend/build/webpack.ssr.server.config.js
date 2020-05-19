const path = require("path");

const { get } = require("lodash");
const nodeExternals = require("webpack-node-externals");
const VueSSRServerPlugin = require("vue-server-renderer/server-plugin");
const merge = require("webpack-merge");

const base = require("./webpack.ssr.base.config");

const entry = {
    "main-server": path.resolve(__dirname, "../ssr/src/server-entry.js"),
}

const sourceImports = require("./ssr-generate-component-library-map")(base.entry, ["ItineraryMap"])

base.entry = Object.assign({}, entry, base.entry)

module.exports = merge(base, {
    target: "node",
    // This tells the server bundle to use Node-style exports
    output: {
        path: path.resolve(__dirname, "../dist/ssr/server"),
        libraryTarget: "commonjs2"
    },
    // https://webpack.js.org/configuration/externals/#function	
    // https://github.com/liady/webpack-node-externals	
    // Externalize app dependencies. This makes the server build much faster	
    // and generates a smaller bundle file.	
    externals: nodeExternals({	
        // do not externalize dependencies that need to be processed by webpack.	
        // you can add more file types here e.g. raw *.vue files	
        // you should also whitelist deps that modifies `global` (e.g. polyfills)	
        // whitelist: /[\.css]$/
    }),
    module: {
        rules: [
            {
                test: /app\.js$/,
                use: [
                    {
                        loader: path.resolve(__dirname, "./ssr-dynamic-component-loader"),
                        options: {
                            componentMap: get(sourceImports, "components")
                        }
                    }
                ]
            }
        ]
    },

    // This is the plugin that turns the entire output of the server build
    // into a single JSON file. The default file name will be
    // `vue-ssr-server-bundle.json`
    plugins: [
        new VueSSRServerPlugin(),
    ]
});
