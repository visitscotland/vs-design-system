const path = require('path');
const webpack = require('webpack');
const merge = require('webpack-merge');
const VueSSRClientPlugin = require('vue-server-renderer/client-plugin')

const base = require('./webpack.base.config');
const isProduction = process.env.NODE_ENV === 'production';
const srcPath = path.resolve(process.cwd(), 'ssr/src');

module.exports = merge(base, {
    entry: {
        main: path.join(srcPath, 'client-entry.js'),
        // DefaultButton: path.resolve(__dirname, '../src/components/Button.vue'),
    },
    output: {
        path: path.resolve(process.cwd(), 'ssr/dist'),
        publicPath: '/public',
        filename: isProduction ? '[name].[hash].js' : '[name].js',
        sourceMapFilename: isProduction ? '[name].[hash].js.map' : '[name].js.map',
    },
    resolve: {
        extensions: ['.js', '.vue'],
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
    plugins: (isProduction ? 
        [
            new VueSSRClientPlugin(),
        ] : [
            new VueSSRClientPlugin(),
            new webpack.HotModuleReplacementPlugin(),
        ]
    )
});