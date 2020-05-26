const path = require('path');
const merge = require('webpack-merge');
const VueSSRClientPlugin = require('vue-server-renderer/client-plugin')
// const { endsWith } = require("lodash")

const base = require('./webpack.ssr.base.config');

const entry = {
    'main-client': path.resolve(__dirname, '../ssr/src/client-entry.js'),
}

base.entry = Object.assign({}, entry, base.entry)

module.exports = merge(base, {
    output: {
        path: path.resolve(__dirname, "../dist/ssr/client"),
    },
    optimization: {
        splitChunks: {
            chunks: "all",
            minSize: 0,
            maxInitialRequests: Infinity,
            // cacheGroups: {
            //     vueCore: {
            //         name: "vue",
            //         chunks: "all",
            //         test(moduleDefinition, chunks) {
            //             return endsWith(moduleDefinition.resource, "vue.esm.js")
            //         },
            //         filename: 'components/vendors~vue.js',
            //     },
            // },
        },
        runtimeChunk: "single",
        concatenateModules: false,
    },
    plugins: [
        new VueSSRClientPlugin()
    ]
});
