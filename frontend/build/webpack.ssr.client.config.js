const path = require('path');
const merge = require('webpack-merge');
const VueSSRClientPlugin = require('vue-server-renderer/client-plugin')

const base = require('./webpack.ssr.base.config');

const entry = {
    'main-client': path.resolve(__dirname, '../ssr/src/client-entry.js'),
}

base.entry = Object.assign({}, entry, base.entry)

const config = merge(base, {
    output: {
        path: path.resolve(__dirname, "../dist/ssr/client"),
        publicPath: '/public',
    },
    resolve: {
        alias: {
            'vue$': 'vue/dist/vue.esm.js'
        }
    },
    plugins: [
        new VueSSRClientPlugin()
    ]
});

// console.log(config)

module.exports = config