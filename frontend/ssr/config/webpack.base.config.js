const path = require('path');

const VueLoaderPlugin = require('vue-loader/lib/plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const autoprefixer = require('autoprefixer');

const srcPath = path.resolve(process.cwd(), 'ssr/src');
const isProduction = process.env.NODE_ENV === 'production';

module.exports = {
    mode: process.env.NODE_ENV,
    devtool: isProduction ? 'source-map' : 'eval-source-map',
    resolve: {
        alias: {
            'vue$': 'vue/dist/vue.esm.js'
        }
    },
    module: {
        rules: [
            {
                test: /\.vue$/,
                loader: 'vue-loader',
                include: [srcPath],
            },
            {
                test: /\.css$/,
                use: [
                    MiniCssExtractPlugin.loader,
                    { loader: 'css-loader', options: { sourceMap: !isProduction } },
                ]
            },
            {
                test: /\.scss$/,
                use: [
                    MiniCssExtractPlugin.loader,
                    'css-loader',
                    {
                        loader: 'postcss-loader',
                        options: {
                            plugins: () => [autoprefixer]
                        }
                    },
                    'sass-loader',
                ],
            },
            {
                test: /\.js$/,
                loader: 'babel-loader',
                include: [srcPath],
                exclude: /node_modules/,
            },
            {
                test: /\.(png|jpe?g|gif|svg)(\?.*)?$/,
                use: [
                    {
                        loader: 'url-loader',
                        options: {
                            limit: 10000,
                            name: '[path][name].[hash:7].[ext]',
                            context: srcPath
                        }
                    }
                ]
            },
            {
                test: /\.(woff2?|eot|ttf|otf)(\?.*)?$/,
                use: [
                    {
                        loader: 'url-loader',
                        options: {
                            limit: 10000,
                            name: '[name].[hash:7].[ext]'
                        }
                    }
                ]
            },
            {
                test: /\.html$/i,
                loader: 'html-loader',
            },
        ]
    },
    plugins: (isProduction ? 
        [
            new VueLoaderPlugin(),
            new MiniCssExtractPlugin({
                filename: '[name].[contenthash].css',
            }),
        ] : [
            new VueLoaderPlugin(),
            new MiniCssExtractPlugin({
                filename: '[name].css',
                hmr: true,
            }),
        ]
    )
};